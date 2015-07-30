package com.yongjia.controller.web;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.dao.MemberCarMapper;
import com.yongjia.dao.MemberMapper;
import com.yongjia.dao.PointPoolConfigMapper;
import com.yongjia.dao.PointPoolMapper;
import com.yongjia.dao.PointPoolRecordMapper;
import com.yongjia.dao.SignPointConfigMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.dao.WxUserMapper;
import com.yongjia.model.Member;
import com.yongjia.model.MemberCar;
import com.yongjia.model.PointPool;
import com.yongjia.model.PointPoolConfig;
import com.yongjia.model.PointPoolRecord;
import com.yongjia.model.SignPointConfig;
import com.yongjia.model.User;
import com.yongjia.model.WxUser;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.DateUtil;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/pool")
public class PointPoolController extends BaseController {

    private static Logger log = Logger.getLogger(PointPoolController.class);

    @Autowired
    private PointPoolMapper pointPoolMapper;

    @Autowired
    private PointPoolRecordMapper pointPoolRecordMapper;
    @Autowired
    private SignPointConfigMapper signPointConfigMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {

        Long count = pointPoolMapper.countAll();
        if (count > 0) {
            List<PointPool> pointPoolList = pointPoolMapper.selectAll(getPageMap(pageNo, pageSize));
            for (int i = 0; i < pointPoolList.size(); i++) {
                Long now = System.currentTimeMillis();
                if (pointPoolList.get(i).getEndAt() < now) {
                    pointPoolList.get(i).setStatus(PointPool.StatusOverdue);
                } else if (pointPoolList.get(i).getStartAt() <= now && pointPoolList.get(i).getEndAt() >= now) {
                    pointPoolList.get(i).setStatus(PointPool.StatusActive);
                } else if (pointPoolList.get(i).getStartAt() > now) {
                    pointPoolList.get(i).setStatus(PointPool.StatusNoActive);
                }
            }
            return ToJsonUtil
                    .toPagetMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), count, pointPoolList);
        } else {
            return ToJsonUtil.toPagetMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), count, null);
        }
    }

    @RequestMapping("/recordlist")
    @ResponseBody
    public Map recordlist(Long pointPoolId, Integer pageNo, Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {
        Long count = pointPoolRecordMapper.countByPointPoolId(pointPoolId);
        List<PointPoolRecord> pointPoolRecordList = null;
        if (count > 0) {
            pointPoolRecordList = pointPoolRecordMapper.selectByPointPoolId(pointPoolId, getPageMap(pageNo, pageSize));
        }
        return ToJsonUtil.toPagetMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), count,
                pointPoolRecordList);
    }

    @RequestMapping("/add")
    @ResponseBody
    public Map add(PointPool pointPool, HttpServletRequest request, HttpServletResponse response) {
        if (CookieUtil.getRoleID(request) != null && CookieUtil.getRoleID(request) <= 2) {
            Long maxEndAt = pointPoolMapper.selectMaxEndAt();
            if (pointPool.getStartAt() < maxEndAt) {
                return ToJsonUtil.toEntityMap(201, "新增积分池有效时间不能重叠", null);
            } else if (pointPool.getStartAt() > pointPool.getEndAt()) {
                return ToJsonUtil.toEntityMap(201, "积分池结束时间必须大于起始时间", null);
            }
            Long userId = CookieUtil.getUserID(request);
            pointPool.setCreateBy(userId);
            pointPool.setCreateAt(System.currentTimeMillis());
            pointPool.setUpdateBy(userId);
            pointPool.setUpdateAt(System.currentTimeMillis());
            if (pointPoolMapper.insertSelective(pointPool) > 0) {
                return ToJsonUtil.toEntityMap(200, "success", null);
            } else {
                return ToJsonUtil.toEntityMap(400, "error", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(403, "权限不够", null);
        }
    }

    @RequestMapping("/update")
    @ResponseBody
    public Map update(PointPool pointPool, HttpServletRequest request, HttpServletResponse response) {
        if (CookieUtil.getRoleID(request) != null && CookieUtil.getRoleID(request) <= 2) {
            if (pointPool.getStartAt() >= System.currentTimeMillis()) {
                // TODO 获取前一个积分池和后一个积分池
                PointPool beforePointPool = pointPoolMapper.selectByPrimaryKey(pointPool.getId() - 1);
                PointPool afterPointPool = pointPoolMapper.selectByPrimaryKey(pointPool.getId() + 1);

                if ((beforePointPool != null && pointPool.getStartAt() < beforePointPool.getEndAt())
                        || (afterPointPool != null && pointPool.getEndAt() > afterPointPool.getStartAt())) {
                    return ToJsonUtil.toEntityMap(201, "新增积分池有效时间不能重叠", null);
                } else if (pointPool.getStartAt() > pointPool.getEndAt()) {
                    return ToJsonUtil.toEntityMap(201, "积分池结束时间必须大于起始时间", null);
                }
                Long userId = CookieUtil.getUserID(request);
                pointPool.setUpdateBy(userId);
                pointPool.setUpdateAt(System.currentTimeMillis());
                if (pointPoolMapper.updateByPrimaryKeySelective(pointPool) > 0) {
                    return ToJsonUtil.toEntityMap(200, "success", null);
                } else {
                    return ToJsonUtil.toEntityMap(400, "error", null);
                }
            } else {
                return ToJsonUtil.toEntityMap(400, "该积分池不可修改", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(403, "权限不够", null);
        }
    }

    @RequestMapping("/charge")
    @ResponseBody
    @Transactional
    public Map charge(Long id, Integer point, HttpServletRequest request, HttpServletResponse response) {
        if (CookieUtil.getRoleID(request) != null && CookieUtil.getRoleID(request) <= 2) {
            Long now = System.currentTimeMillis();

            Long userId = CookieUtil.getUserID(request);
            PointPool pointPool = pointPoolMapper.selectByPrimaryKey(id);

            if (pointPool.getEndAt() >= System.currentTimeMillis()) {
                pointPool.setTotalPoint(pointPool.getTotalPoint() + point);
                pointPool.setUpdateBy(userId);
                pointPool.setUpdateAt(System.currentTimeMillis());
                if (pointPoolMapper.updateByPrimaryKeySelective(pointPool) > 0) {
                    PointPoolRecord pointPoolRecord = new PointPoolRecord();
                    pointPoolRecord.setAction("充值");
                    pointPoolRecord.setOperatorName(CookieUtil.getUserName(request));
                    pointPoolRecord.setOperatorRole("主管");
                    pointPoolRecord.setPoint(point);
                    pointPoolRecord.setPointPoolId(id);
                    pointPoolRecord.setTotalPointAfter(pointPool.getTotalPoint());
                    pointPoolRecord.setTotalPointBefore(pointPool.getTotalPoint() - point);

                    if (pointPoolRecordMapper.insertSelective(pointPoolRecord) > 0) {
                        return ToJsonUtil.toEntityMap(200, "success", pointPool);
                    } else {
                        return ToJsonUtil.toEntityMap(400, "error", null);
                    }
                } else {
                    return ToJsonUtil.toEntityMap(400, "error", null);
                }
            } else {
                return ToJsonUtil.toEntityMap(400, "该积分池不在使用中", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(403, "权限不够", null);
        }
    }

    @RequestMapping("/delete")
    @ResponseBody
    public Map delete(Long id, HttpServletRequest request, HttpServletResponse response) {
        if (CookieUtil.getRoleID(request) != null && CookieUtil.getRoleID(request) <= 2) {
            PointPool pointPool = pointPoolMapper.selectByPrimaryKey(id);
            if (pointPool.getStartAt() >= System.currentTimeMillis()) {
                if (pointPoolMapper.deleteByPrimaryKey(id) > 0) {
                    return ToJsonUtil.toEntityMap(200, "success", null);
                } else {
                    return ToJsonUtil.toEntityMap(400, "error", null);
                }
            } else {
                return ToJsonUtil.toEntityMap(400, "该积分池不可删除", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(403, "权限不够", null);
        }
    }

    @RequestMapping("/config")
    @ResponseBody
    public Map config(Long id, Integer registerPoint, Integer carOwnerPoint, Integer toBuyCarPoint,
            HttpServletRequest request, HttpServletResponse response) {
        if (CookieUtil.getRoleID(request) != null && CookieUtil.getRoleID(request) <= 2) {

            Long userId = CookieUtil.getUserID(request);
            PointPool pointPool = pointPoolMapper.selectByPrimaryKey(id);

            if (pointPool.getEndAt() >= System.currentTimeMillis()) {
                pointPool.setUpdateBy(userId);
                pointPool.setUpdateAt(System.currentTimeMillis());
                if (registerPoint > 0) {
                    pointPool.setRegisterPoint(registerPoint);
                }
                if (carOwnerPoint > 0) {
                    pointPool.setCarOwnerPoint(carOwnerPoint);
                }
                if (toBuyCarPoint > 0) {
                    pointPool.setToBuyCarPoint(toBuyCarPoint);
                }
                pointPoolMapper.updateByPrimaryKey(pointPool);

                return ToJsonUtil.toEntityMap(200, "success", null);
            } else {
                return ToJsonUtil.toEntityMap(400, "该积分池不可配置积分项", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(403, "权限不够", null);
        }
    }

    @RequestMapping("/getSignConfig")
    @ResponseBody
    public Map getSignConfig(String month, HttpServletRequest request, HttpServletResponse response) {

        List<SignPointConfig> signPointConfigListFromDb = signPointConfigMapper.selectByMonth(month);
        Integer monthDayCount = DateUtil.getDayByMonth(month);
        List<SignPointConfig> signPointConfigList = new ArrayList<SignPointConfig>();
        for (int i = 0; i < monthDayCount; i++) {
            SignPointConfig signPointConfig = new SignPointConfig();
            signPointConfig.setMonth(month);
            signPointConfig.setTimes(i+1);
            signPointConfig.setPoint(0);

            for (SignPointConfig sp : signPointConfigListFromDb) {
                if (sp.getTimes().equals(i+1)) {
                    signPointConfig.setPoint(sp.getPoint());
                    break;
                }
            }

            signPointConfigList.add(signPointConfig);
        }

        return ToJsonUtil.toListMap(200, "success", signPointConfigList);
    }

    @RequestMapping("/setSignConfig")
    @ResponseBody
    public Map setSignConfig(String month, Integer times, Integer point, HttpServletRequest request,
            HttpServletResponse response) {
        if (CookieUtil.getRoleID(request) != null && CookieUtil.getRoleID(request) <= 2) {
            Long userId = CookieUtil.getUserID(request);
            Long now = System.currentTimeMillis();
            SignPointConfig signPointConfig = signPointConfigMapper.selectByMonthAndTimes(month, times);
            if (signPointConfig != null) {
                signPointConfig.setPoint(point);
                signPointConfig.setUpdateAt(now);
                signPointConfig.setUpdateBy(userId);
                if (signPointConfigMapper.updateByPrimaryKeySelective(signPointConfig) > 0) {
                    return ToJsonUtil.toEntityMap(200, "success", null);
                } else {
                    return ToJsonUtil.toEntityMap(400, "error", null);
                }
            } else {
                signPointConfig = new SignPointConfig();
                signPointConfig.setMonth(month);
                signPointConfig.setTimes(times);
                signPointConfig.setPoint(point);
                signPointConfig.setCreateAt(now);
                signPointConfig.setCreateBy(userId);
                signPointConfig.setUpdateAt(now);
                signPointConfig.setUpdateBy(userId);
                if (signPointConfigMapper.insertSelective(signPointConfig) > 0) {
                    return ToJsonUtil.toEntityMap(200, "success", null);
                } else {
                    return ToJsonUtil.toEntityMap(400, "error", null);
                }
            }
        } else {
            return ToJsonUtil.toEntityMap(403, "权限不够", null);
        }
    }

}
package com.yongjia.controller.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.dao.MemberPointMapper;
import com.yongjia.dao.MemberPointRecordMapper;
import com.yongjia.dao.PointPoolMapper;
import com.yongjia.dao.WxUser2memberMapper;
import com.yongjia.model.MemberPoint;
import com.yongjia.model.MemberPointRecord;
import com.yongjia.model.PointPool;
import com.yongjia.model.WxUser2member;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.ToJsonUtil;
import com.yongjia.wxkit.template.uitls.TplMessageUtil;

@Controller
@RequestMapping("/web/point")
public class PointController extends WebBaseController {

    private static Logger log = Logger.getLogger(PointController.class);

    @Autowired
    private MemberPointRecordMapper memberPointRecordMapper;

    @Autowired
    private MemberPointMapper memberPointMapper;

    @Autowired
    private PointPoolMapper pointPoolMapper;

    @Autowired
    private WxUser2memberMapper wxUser2memberMapper;

    /**
     * 注入线程池
     */
    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;

    @RequestMapping("/listExchange")
    @ResponseBody
    public Map listExchange(Long pointPoolId, Integer pageNo, Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {

        Long totalCount = memberPointRecordMapper.countByPoolIdAndType(pointPoolId, MemberPointRecord.TypeUse);
        List<MemberPointRecord> memberPointRecordList = null;
        if (totalCount > 0) {
            memberPointRecordList = memberPointRecordMapper.selectByPoolIdAndType(pointPoolId,
                    MemberPointRecord.TypeUse, getPageMap(pageNo, pageSize));
        }
        return ToJsonUtil.toPageMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount,
                memberPointRecordList);
    }

    @RequestMapping("/listPoint")
    @ResponseBody
    public Map listPoint(Long pointPoolId, Integer pageNo, Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {

        Long totalCount = memberPointRecordMapper.countByPoolIdAndType(pointPoolId, MemberPointRecord.TypeGet);
        List<MemberPointRecord> memberPointRecordList = null;
        if (totalCount > 0) {
            memberPointRecordList = memberPointRecordMapper.selectByPoolIdAndType(pointPoolId,
                    MemberPointRecord.TypeGet, getPageMap(pageNo, pageSize));
        }
        return ToJsonUtil.toPageMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount,
                memberPointRecordList);
    }

    @RequestMapping("/addExchange")
    @ResponseBody
    public Map addExchange(MemberPointRecord memberPointRecord, HttpServletRequest request, HttpServletResponse response) {
        if (memberPointRecord.getMemberId() == null) {
            return ToJsonUtil.toEntityMap(400, "memberId 不能为空", null);
        }

        PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
        if (pointPool == null) {
            return ToJsonUtil.toEntityMap(400, "没有激活的积分池", null);
        }
        memberPointRecord.setPointPoolId(pointPool.getId());
        Long userId = CookieUtil.getUserID(request);
        memberPointRecord.setType(MemberPointRecord.TypeUse);
        memberPointRecord.setCreateBy(userId);
        memberPointRecord.setCreateAt(System.currentTimeMillis());

        MemberPoint memberPoint = memberPointMapper.selectByMemberIdAndPoolId(memberPointRecord.getMemberId(),
                pointPool.getId());
        if (memberPoint == null || memberPoint.getPoint() < memberPointRecord.getPoint()) {
            return ToJsonUtil.toEntityMap(400, "积分不足", null);
        } else {
            memberPoint.setPoint(memberPoint.getPoint() - memberPointRecord.getPoint());
            if (memberPointMapper.updateByPrimaryKeySelective(memberPoint) > 0) {
                if (memberPointRecordMapper.insertSelective(memberPointRecord) > 0) {
                    /**
                     * 发送积分变动通知
                     */
                    taskExecutor.execute(new SendPointChangeTplTask(memberPointRecord.getMemberId(), memberPointRecord,
                            memberPoint.getPoint()));
                    return ToJsonUtil.toEntityMap(200, "success", null);
                } else {
                    return ToJsonUtil.toEntityMap(400, "error", null);
                }
            } else {
                return ToJsonUtil.toEntityMap(400, "error", null);
            }
        }

    }

    @RequestMapping("/addPoint")
    @ResponseBody
    @Transactional
    public Map addPoint(MemberPointRecord memberPointRecord, HttpServletRequest request, HttpServletResponse response) {
        if (memberPointRecord.getMemberId() == null) {
            return ToJsonUtil.toEntityMap(400, "memberId 不能为空", null);
        }

        PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
        if (pointPool == null) {
            return ToJsonUtil.toEntityMap(400, "没有激活的积分池", null);
        }
        memberPointRecord.setPointPoolId(pointPool.getId());
        Long userId = CookieUtil.getUserID(request);
        memberPointRecord.setType(MemberPointRecord.TypeGet);
        memberPointRecord.setCreateBy(userId);
        memberPointRecord.setCreateAt(System.currentTimeMillis());
        if (memberPointRecordMapper.insertSelective(memberPointRecord) > 0) {
            MemberPoint memberPoint = memberPointMapper.selectByMemberIdAndPoolId(memberPointRecord.getMemberId(),
                    pointPool.getId());
            if (memberPoint == null) {
                memberPoint = new MemberPoint();
                memberPoint.setMemberId(memberPointRecord.getMemberId());
                memberPoint.setPointPoolId(pointPool.getId());
                memberPoint.setPoint(memberPointRecord.getPoint());
                if (memberPointMapper.insertSelective(memberPoint) > 0) {
                    /**
                     * 发送积分变动通知
                     */
                    taskExecutor.execute(new SendPointChangeTplTask(memberPointRecord.getMemberId(), memberPointRecord,
                            memberPoint.getPoint()));
                    return ToJsonUtil.toEntityMap(200, "success", null);
                } else {
                    return ToJsonUtil.toEntityMap(400, "error", null);
                }
            } else {
                memberPoint.setPoint(memberPoint.getPoint() + memberPointRecord.getPoint());
                if (memberPointMapper.updateByPrimaryKeySelective(memberPoint) > 0) {
                    /**
                     * 发送积分变动通知
                     */
                    taskExecutor.execute(new SendPointChangeTplTask(memberPointRecord.getMemberId(), memberPointRecord,
                            memberPoint.getPoint()));
                    return ToJsonUtil.toEntityMap(200, "success", null);
                } else {
                    return ToJsonUtil.toEntityMap(400, "error", null);
                }
            }

        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    private class SendPointChangeTplTask implements Runnable {

        private Long memberId;
        private MemberPointRecord memberPointRecord;
        private Integer leftPoint;

        public SendPointChangeTplTask(Long memberId, MemberPointRecord memberPointRecord, Integer leftPoint) {
            this.memberId = memberId;
            this.memberPointRecord = memberPointRecord;
            this.leftPoint = leftPoint;
        }

        public void run() {
            WxUser2member wxUser2member = wxUser2memberMapper.selectByMemberId(memberId);
            TplMessageUtil.sendPointChangeTpl(wxUser2member.getOpenid(), memberPointRecord, leftPoint);
        }

    }
}
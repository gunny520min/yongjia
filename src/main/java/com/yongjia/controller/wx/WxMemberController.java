package com.yongjia.controller.wx;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.controller.web.WebBaseController;
import com.yongjia.dao.MemberCarMapper;
import com.yongjia.dao.MemberMapper;
import com.yongjia.dao.MemberPointMapper;
import com.yongjia.dao.MemberPointRecordMapper;
import com.yongjia.dao.MemberSignMapper;
import com.yongjia.dao.MemberSignRecordMapper;
import com.yongjia.dao.PointPoolMapper;
import com.yongjia.dao.SignPointConfigMapper;
import com.yongjia.dao.SmsSendRecordMapper;
import com.yongjia.dao.WxUser2memberMapper;
import com.yongjia.dao.WxUserAndMemberMapper;
import com.yongjia.model.Member;
import com.yongjia.model.MemberCar;
import com.yongjia.model.MemberPoint;
import com.yongjia.model.MemberPointRecord;
import com.yongjia.model.MemberSign;
import com.yongjia.model.MemberSignRecord;
import com.yongjia.model.PointPool;
import com.yongjia.model.SignPointConfig;
import com.yongjia.model.SmsSendRecord;
import com.yongjia.model.WxUser2member;
import com.yongjia.model.WxUserAndMember;
import com.yongjia.sms.utils.SmsUtil;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.DateUtil;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/wx/member")
public class WxMemberController extends WebBaseController {

    private static Logger log = Logger.getLogger(WxMemberController.class);

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberCarMapper memberCarMapper;

    @Autowired
    private WxUser2memberMapper wxUser2memberMapper;

    @Autowired
    private WxUserAndMemberMapper wxUserAndMemberMapper;

    @Autowired
    private SmsSendRecordMapper smsSendRecordMapper;

    @Autowired
    private PointPoolMapper pointPoolMapper;

    @Autowired
    private MemberSignMapper memberSignMapper;

    @Autowired
    private SignPointConfigMapper signPointConfigMapper;

    @Autowired
    private MemberPointMapper memberPointMapper;

    @Autowired
    private MemberPointRecordMapper memberPointRecordMapper;

    @Autowired
    private MemberSignRecordMapper memberSignRecordMapper;

    @RequestMapping("/edit")
    @ResponseBody
    public Map edit(String name, Integer sex, String mobile, String valiCode, HttpServletRequest request, HttpServletResponse response) {

        String openid = CookieUtil.getOpenid(request);
        log.info("openid = " + openid);
        log.info("mobile = " + mobile);
        log.info("valiCode = " + valiCode);
        /**
         * 判断手机号是否合法
         */
        if (mobile == null || mobile.length() != 11) {
            return ToJsonUtil.toEntityMap(400, "手机号有误", null);
        }
        /**
         * 判断是否注册过
         */
        PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
        Long pointPoolId = 0L;
        if (pointPool != null) {
            pointPoolId = pointPool.getId();
        }
        Long now = System.currentTimeMillis();
        WxUserAndMember wxUserAndMember = wxUserAndMemberMapper.selectByOpenid(openid, pointPoolId);
        if (wxUserAndMember.getId()!=null && wxUserAndMember.getId() > 0) {
            if (!wxUserAndMember.getMobile().equals(mobile)) {
                /**
                 * 判断验证是否正确
                 */
                SmsSendRecord smsSendRecord = smsSendRecordMapper.selectByMobileAndTpl(mobile,
                        SmsUtil.SmsTpl[SmsUtil.TypeRegister]);
                
                if (smsSendRecord == null || now - smsSendRecord.getCreateAt() > SmsUtil.OverDueTime) {
                    return ToJsonUtil.toEntityMap(400, "请发送验证码", null);
                }
                if (!smsSendRecord.getCode().equals(valiCode)) {
                    return ToJsonUtil.toEntityMap(400, "验证码错误", null);
                }
            }
            Member member = new Member();
            member.setId(wxUserAndMember.getId());
            member.setMobile(mobile);
            member.setName(name);
            member.setSex(sex);
            member.setUpdateAt(now);
            memberMapper.updateByPrimaryKeySelective(member);
            
            return ToJsonUtil.toEntityMap(200, "success", null);
        }
        /**
         * 判断验证是否正确
         */
        SmsSendRecord smsSendRecord = smsSendRecordMapper.selectByMobileAndTpl(mobile,
                SmsUtil.SmsTpl[SmsUtil.TypeRegister]);
        
        if (smsSendRecord == null || now - smsSendRecord.getCreateAt() > SmsUtil.OverDueTime) {
            return ToJsonUtil.toEntityMap(400, "请发送验证码", null);
        }
        if (!smsSendRecord.getCode().equals(valiCode)) {
            return ToJsonUtil.toEntityMap(400, "验证码错误", null);
        }
        /**
         * 插入member及wxuser2member表
         */
        Member member = new Member();
        member.setMobile(mobile);
        member.setHeadImgurl(wxUserAndMember.getHeadimgurl());
        member.setName(name);
        member.setSex(sex);
        member.setStatus(Member.StatusMember);
        member.setValiFlag(Member.NoVali);
        member.setCreateAt(now);
        member.setUpdateAt(now);
        try {
            if (memberMapper.insertSelective(member) > 0) {
                Long memberId = member.getId();
                WxUser2member wxUser2member = new WxUser2member();
                wxUser2member.setMemberId(memberId);
                wxUser2member.setOpenid(openid);
                if (wxUser2memberMapper.insert(wxUser2member) > 0) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(CookieUtil.OPEN_ID, openid);
                    params.put(CookieUtil.MEMBER_ID, memberId + "");
                    
                    CookieUtil.setIdentity(request, response, params, 0);
                    return ToJsonUtil.toEntityMap(200, "success", null);
                } else {
                    return ToJsonUtil.toEntityMap(400, "insert wxuser2member fail", null);
                }
            } else {
                return ToJsonUtil.toEntityMap(400, "insert member fail", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ToJsonUtil.toEntityMap(400, "sql or database error", null);
        }
    }

    @RequestMapping("/valiCarOwn")
    @ResponseBody
    public Map valiCarOwn(String name, Integer sex, MemberCar memberCar, HttpServletRequest request,
            HttpServletResponse response) {

        Long now = System.currentTimeMillis();
        Long memberId = CookieUtil.getMemberId(request);
        if (memberId == null || memberId <= 0) {
            return ToJsonUtil.toEntityMap(400, "您还不是会员，请注册", null);
        }
        Member member = memberMapper.selectByPrimaryKey(memberId);
        member.setName(name);
        member.setSex(sex);
        member.setValiFlag(Member.ToVali);
        member.setUpdateAt(now);
        if (memberMapper.updateByPrimaryKeySelective(member) > 0) {
            memberCar.setMemberId(memberId);
            memberCar.setStatus(MemberCar.StatusToVali);
            memberCar.setCreateAt(now);
            memberCar.setUpdateAt(now);

            if (memberCarMapper.insertSelective(memberCar) > 0) {

                return ToJsonUtil.toEntityMap(200, "success", null);
            } else {
                return ToJsonUtil.toEntityMap(400, "insert memberCar error", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(400, "update member error", null);
        }

    }

    @RequestMapping("/addCar")
    @ResponseBody
    public Map addCar(MemberCar memberCar, HttpServletRequest request, HttpServletResponse response) {

        Long memberId = CookieUtil.getMemberId(request);
        Long now = System.currentTimeMillis();
        memberCar.setMemberId(memberId);
        memberCar.setStatus(MemberCar.StatusToVali);
        memberCar.setCreateAt(now);
        memberCar.setUpdateAt(now);

        if (memberCarMapper.insertSelective(memberCar) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "insert memberCar error", null);
        }
    }

    @RequestMapping("/sign")
    @ResponseBody
    @Transactional
    public Map sign(HttpServletRequest request, HttpServletResponse response) {
        try {
            Long memberId = CookieUtil.getMemberId(request);
            if (memberId == null) {
                return ToJsonUtil.toEntityMap(400, "必须是会员", null);
            }
            Long now = System.currentTimeMillis();
            String month = DateUtil.formatDatetime(new Date(now), "yyyy-M");
            MemberSign memberSign = memberSignMapper.selectByMemberIdAndMonth(memberId, month);
            if (memberSign == null) {
                memberSign = new MemberSign();
                memberSign.setMemberId(memberId);
                memberSign.setMonth(month);
                memberSign.setTimes(0);
                memberSignMapper.insertSelective(memberSign);
            }

            PointPool pointPool = pointPoolMapper.selectActivePool(System.currentTimeMillis());
            Long pointPoolId = 0L;
            if (pointPool != null) {
                pointPoolId = pointPool.getId();
            }

            SignPointConfig signPointConfig = signPointConfigMapper.selectByMonthAndTimes(month,
                    memberSign.getTimes()+1);
            if (signPointConfig==null) {
                signPointConfig = new SignPointConfig();
                signPointConfig.setMonth(memberSign.getMonth());
                signPointConfig.setPoint(0);
                signPointConfig.setTimes(memberSign.getTimes()+1);
            }
            /**
             * 新增积分
             */
            MemberPoint memberPoint = memberPointMapper.selectByMemberIdAndPoolId(memberId, pointPoolId);
            if (memberPoint == null) {
                memberPoint = new MemberPoint();
                memberPoint.setMemberId(memberId);
                memberPoint.setPointPoolId(pointPoolId);
                memberPoint.setPoint(signPointConfig.getPoint());
                memberPointMapper.insertSelective(memberPoint);
            } else {
                memberPoint.setPoint(memberPoint.getPoint() + signPointConfig.getPoint());
                memberPointMapper.updateByPrimaryKeySelective(memberPoint);
            }
            /**
             * 新增积分记录
             */
            MemberPointRecord memberPointRecord = new MemberPointRecord();
            memberPointRecord.setMemberId(memberId);
            memberPointRecord.setPointPoolId(pointPoolId);
            memberPointRecord.setPoint(signPointConfig.getPoint());
            memberPointRecord.setType(MemberPointRecord.TypeGet);
            memberPointRecord.setAction("签到");
            memberPointRecord.setActionContent("签到");
            memberPointRecord.setCreateAt(now);
            memberPointRecordMapper.insertSelective(memberPointRecord);
            /**
             * 新增签到记录
             */
            MemberSignRecord memberSignRecord = new MemberSignRecord();
            memberSignRecord.setMemberId(memberId);
            memberSignRecord.setSignDate(DateUtil.formatDate(new Date(now)));
            memberSignRecordMapper.insertSelective(memberSignRecord);
            /**
             * 修改用户签到次数
             */
            memberSign.setTimes(memberSign.getTimes() + 1);
            memberSignMapper.updateByPrimaryKeySelective(memberSign);

            return ToJsonUtil.toEntityMap(200, "success", null);
        } catch (Exception e) {
            e.printStackTrace();
            return ToJsonUtil.toEntityMap(500, "server error", null);
        }
    }

}
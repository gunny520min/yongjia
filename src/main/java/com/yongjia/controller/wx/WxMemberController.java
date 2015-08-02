package com.yongjia.controller.wx;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.controller.web.BaseController;
import com.yongjia.dao.MemberCarMapper;
import com.yongjia.dao.MemberMapper;
import com.yongjia.dao.SmsSendRecordMapper;
import com.yongjia.dao.WxUser2memberMapper;
import com.yongjia.dao.WxUserAndMemberMapper;
import com.yongjia.model.Member;
import com.yongjia.model.MemberCar;
import com.yongjia.model.SmsSendRecord;
import com.yongjia.model.WxUser2member;
import com.yongjia.model.WxUserAndMember;
import com.yongjia.sms.utils.SmsUtil;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/wx/member")
public class WxMemberController extends BaseController {

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

    @RequestMapping("/regsiter")
    @ResponseBody
    public Map regsiter(String mobile, String viliCode, HttpServletRequest request, HttpServletResponse response) {

        String openid = CookieUtil.getOpenid(request);
        log.info("openid = " + openid);
        log.info("mobile = " + mobile);
        log.info("viliCode = " + viliCode);
        /**
         * 判断手机号是否合法
         */
        if (mobile == null || mobile.length() != 11) {
            return ToJsonUtil.toEntityMap(400, "手机号有误", null);
        }
        /**
         * 判断是否注册过
         */
        WxUserAndMember wxUserAndMember = wxUserAndMemberMapper.selectByOpenid(openid);
        if (wxUserAndMember.getId() > 0) {
            return ToJsonUtil.toEntityMap(400, "您已经注册过了", null);
        }
        /**
         * 判断验证是否正确
         */
        SmsSendRecord smsSendRecord = smsSendRecordMapper.selectByMobileAndTpl(mobile,
                SmsUtil.SmsTpl[SmsUtil.TypeRegister]);
        Long now = System.currentTimeMillis();
        if (smsSendRecord == null || now - smsSendRecord.getCreateAt() > SmsUtil.OverDueTime) {
            return ToJsonUtil.toEntityMap(400, "请发送验证码", null);
        }
        if (!smsSendRecord.getCode().equals(viliCode)) {
            return ToJsonUtil.toEntityMap(400, "验证码错误", null);
        }
        /**
         * 插入member及wxuser2member表
         */
        Member member = new Member();
        member.setMobile(mobile);
        member.setHeadImgurl(wxUserAndMember.getHeadimgurl());
        member.setName(wxUserAndMember.getNickname());
        member.setSex(wxUserAndMember.getSex());
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
                    params.put(CookieUtil.MEMBER_ID, wxUserAndMember.getId() + "");
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

}
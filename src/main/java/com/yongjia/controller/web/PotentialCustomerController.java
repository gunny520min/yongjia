package com.yongjia.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.controller.BaseController;
import com.yongjia.dao.PotentialCustomerAndMemberMapper;
import com.yongjia.model.PotentialCustomerAndMember;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/customer")
public class PotentialCustomerController extends BaseController {

    private static Logger log = Logger.getLogger(PotentialCustomerController.class);

    @Autowired
    private PotentialCustomerAndMemberMapper pCustomermMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map list(String name, String phone, Integer pageNo, Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {

        Long totalCount = pCustomermMapper.countByNameAndPhone(name, phone);
        if (totalCount != null && totalCount > 0) {
            List<PotentialCustomerAndMember> customerList = pCustomermMapper.selectByNameAndPhone(name, phone,
                    getPageMap(pageNo, pageSize));
            return ToJsonUtil.toPagetMap(200, "success", pageNo, pageSize, totalCount, customerList);
        } else {
            return ToJsonUtil.toEntityMap(200, "success", null);
        }
    }

}
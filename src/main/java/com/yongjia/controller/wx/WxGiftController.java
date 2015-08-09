package com.yongjia.controller.wx;

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
import com.yongjia.dao.CarHallMapper;
import com.yongjia.dao.GiftMapper;
import com.yongjia.model.CarHall;
import com.yongjia.model.Gift;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/wx/gift")
public class WxGiftController extends BaseController {

    private static Logger log = Logger.getLogger(WxGiftController.class);

    @Autowired
    private GiftMapper giftMapper;

    @RequestMapping("/list")
    @ResponseBody
    public Map hallList(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        try {
            Long totalCount = giftMapper.countByNameAndStatus(null, Gift.StatusPubnish);
            List<Gift> giftList = null;
            if (totalCount > 0) {
                giftList = giftMapper.selectByNameAndStatus(null, Gift.StatusPubnish, getPageMap(pageNo, pageSize));
            }

            return ToJsonUtil
                    .toPagetMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount, giftList);
        } catch (Exception e) {
            return ToJsonUtil.toEntityMap(500, "server error", null);
        }
    }
}
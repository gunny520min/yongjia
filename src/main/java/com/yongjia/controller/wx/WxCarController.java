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
import com.yongjia.model.CarHall;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/wx/car")
public class WxCarController extends BaseController {

    private static Logger log = Logger.getLogger(WxCarController.class);

    @Autowired
    private CarHallMapper carHallMapper;

    @RequestMapping("/hallList")
    @ResponseBody
    public Map hallList(Integer pageNo, Integer pageSize, HttpServletRequest request, HttpServletResponse response) {
        Long totalCount = carHallMapper.countByCondition(null, null, CarHall.StatusActive);
        List<CarHall> carHallList = null;
        if (totalCount > 0) {
            carHallList = carHallMapper.selectByCondition(null, null, CarHall.StatusActive,
                    getPageMap(pageNo, pageSize));
        }

        return ToJsonUtil.toPageMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount, carHallList);
    }
    
    
    
}
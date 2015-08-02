package com.yongjia.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yongjia.dao.CarModelMapper;
import com.yongjia.dao.CarModelParamMapper;
import com.yongjia.dao.CarTypeMapper;
import com.yongjia.model.CarModel;
import com.yongjia.model.CarModelParam;
import com.yongjia.model.CarType;
import com.yongjia.utils.ToJsonUtil;
@Controller
@RequestMapping("/common")
public class BaseCommonController {
    

    @Autowired
    private CarTypeMapper carTypeMapper;
    @Autowired
    private CarModelMapper carModelMapper;
    @Autowired
    private CarModelParamMapper carModelParamMapper;
    
    
    @RequestMapping("/getCarType")
    @ResponseBody
    public Map getCarType(Integer importFlag, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CarType> carTypeList = carTypeMapper.selectByImportFlag(importFlag);
            return ToJsonUtil.toListMap(200, "success", carTypeList);
        } catch (Exception e) {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/getCarModel")
    @ResponseBody
    public Map getCarModel(Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CarModel> carModelList = carModelMapper.selectByTypeId(id);
            return ToJsonUtil.toListMap(200, "success", carModelList);
        } catch (Exception e) {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }


    @RequestMapping("/getCarParam")
    @ResponseBody
    public Map getCarParam(Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CarModelParam> carParamList = carModelParamMapper.selectByModelId(id);
            return ToJsonUtil.toListMap(200, "success", carParamList);
        } catch (Exception e) {
            return ToJsonUtil.toListMap(400, "error", null);
        }
    }
    
}
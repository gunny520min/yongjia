package com.yongjia.controller.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
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
import com.yongjia.dao.CarModelMapper;
import com.yongjia.dao.CarModelParamMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.model.CarHall;
import com.yongjia.model.CarModel;
import com.yongjia.model.CarModelParam;
import com.yongjia.model.User;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.PasswordUtils;
import com.yongjia.utils.ToJsonUtil;

@Controller
@RequestMapping("/web/car")
public class CarController extends BaseController {

    private static Logger log = Logger.getLogger(CarController.class);

    @Autowired
    private CarModelMapper carModelMapper;
    @Autowired
    private CarModelParamMapper carModelParamMapper;
    @Autowired
    private CarHallMapper carHallMapper;

    @RequestMapping("/carlist")
    @ResponseBody
    public Map carlist(String typeName, String modelName, HttpServletRequest request, HttpServletResponse response) {

//        List<CarModel> carList = carModelMapper.selectByName(typeName, modelName);
        List<CarModel> carList = new ArrayList<CarModel>();
        CarModel carModel = new CarModel();
        carModel.setCarModelName("奥迪A6");
        carModel.setId(1L);
        carModel.setStatus(CarModel.StatusActive);
        carModel.setTypeId(1L);
        carModel.setTypeName("奥迪");
        carList.add(carModel);
        
        carModel = new CarModel();
        carModel.setCarModelName("奥迪A8");
        carModel.setId(2L);
        carModel.setStatus(CarModel.StatusActive);
        carModel.setTypeId(1L);
        carModel.setTypeName("奥迪");
        carList.add(carModel);
        return ToJsonUtil.toListMap(200, "success", carList);
    }

    @RequestMapping("/importCarType")
    @ResponseBody
    public Map addCarType(String typeName, Integer importFlag, File file, HttpServletRequest request,
            HttpServletResponse response) {

        return ToJsonUtil.toEntityMap(200, "success", null);
    }

    @RequestMapping("/updateCarModel")
    @ResponseBody
    public Map updateCarModel(CarModel carModel, CarModelParam carModelParam, HttpServletRequest request,
            HttpServletResponse response) {

        if (carModelParamMapper.updateByPrimaryKeySelective(carModelParam) > 0
                && carModelMapper.updateByPrimaryKey(carModel) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/toggleCarModel")
    @ResponseBody
    public Map toggleCarModel(Integer carModelId, HttpServletRequest request, HttpServletResponse response) {

        CarModel carModel = carModelMapper.selectByPrimaryKey(carModelId);
        if (carModel.getStatus().equals(CarModel.StatusActive)) {
            carModel.setStatus(CarModel.StatusStop);
        } else {
            carModel.setStatus(CarModel.StatusActive);
        }

        if (carModelMapper.updateByPrimaryKeySelective(carModel) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/carHalllist")
    @ResponseBody
    public Map carHalllist(String typeName, Integer importFlag, HttpServletRequest request, HttpServletResponse response) {

//        List<CarHall> carHallList = carHallMapper.selectByName(typeName, importFlag);
        List<CarHall> carHallList = new ArrayList<CarHall>();
        CarHall carHall = new CarHall();
        carHall.setId(1L);
        carHall.setTypeId(1L);
        carHall.setTypeName("奥迪");
        carHall.setImg("");
        carHall.setImportFlag(CarHall.ImportChina);
        carHall.setPriceLowest("20万");
        carHall.setPriceMost("50万");
        carHall.setStatus(CarHall.StatusActive);
        carHallList.add(carHall);
        
        carHall = new CarHall();
        carHall.setId(2L);
        carHall.setTypeId(1L);
        carHall.setTypeName("奥迪");
        carHall.setImg("");
        carHall.setImportFlag(CarHall.ImportChina);
        carHall.setPriceLowest("20万");
        carHall.setPriceMost("50万");
        carHall.setStatus(CarHall.StatusActive);
        carHallList.add(carHall);
        return ToJsonUtil.toListMap(200, "success", carHallList);
    }


    @RequestMapping("/addCarHall")
    @ResponseBody
    public Map addCarHall(CarHall carHall,List<String> carHallPics, List<Integer> carModelIds, HttpServletRequest request,
            HttpServletResponse response) {

        return ToJsonUtil.toEntityMap(200, "success", null);
    }


    @RequestMapping("/updateCarHall")
    @ResponseBody
    public Map updateCarHall(CarHall carHall,List<String> carHallPics, List<Integer> carModelIds, HttpServletRequest request,
            HttpServletResponse response) {

        return ToJsonUtil.toEntityMap(200, "success", null);
    }
    


    @RequestMapping("/toggleCarHall")
    @ResponseBody
    public Map toggleCarHall(Integer carHallId, HttpServletRequest request, HttpServletResponse response) {

        CarHall carHall = carHallMapper.selectByPrimaryKey(carHallId);
        if (carHall.getStatus().equals(CarModel.StatusActive)) {
            carHall.setStatus(CarModel.StatusStop);
        } else {
            carHall.setStatus(CarModel.StatusActive);
        }

        if (carHallMapper.updateByPrimaryKeySelective(carHall) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }
}
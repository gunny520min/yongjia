package com.yongjia.controller.web;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.yongjia.dao.CarHallMapper;
import com.yongjia.dao.CarHallModelMapper;
import com.yongjia.dao.CarHallPicMapper;
import com.yongjia.dao.CarModelMapper;
import com.yongjia.dao.CarModelParamMapper;
import com.yongjia.dao.CarTypeMapper;
import com.yongjia.dao.UserMapper;
import com.yongjia.model.CarHall;
import com.yongjia.model.CarHallModel;
import com.yongjia.model.CarHallPic;
import com.yongjia.model.CarModel;
import com.yongjia.model.CarModelParam;
import com.yongjia.model.CarType;
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
    @Autowired
    private CarHallPicMapper carHallPicMapper;
    @Autowired
    private CarHallModelMapper carHallModelMapper;
    @Autowired
    private CarTypeMapper carTypeMapper;

    @RequestMapping("/carlist")
    @ResponseBody
    public Map carlist(String typeName, String modelName, Integer pageNo, Integer pageSize, HttpServletRequest request,
            HttpServletResponse response) {

        Long totalCount = carModelMapper.countByName(typeName, modelName);
        List<CarModel> carList = null;
        if (totalCount > 0) {
            carList = carModelMapper.selectByName(typeName, modelName, getPageMap(pageNo, pageSize));
        }

        return ToJsonUtil.toPagetMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount, carList);
    }

    @RequestMapping("/getCarParam")
    @ResponseBody
    public Map getCarParam(Long carModelId, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CarModelParam> carParamList = carModelParamMapper.selectByModelId(carModelId);
            return ToJsonUtil.toListMap(200, "success", carParamList);
        } catch (Exception e) {
            return ToJsonUtil.toListMap(400, "error", null);
        }
    }

    @RequestMapping("/importCarType")
    @ResponseBody
    public Map importCarType(String typeName, Integer importFlag, File file, HttpServletRequest request,
            HttpServletResponse response) {
        // TODO
        return ToJsonUtil.toEntityMap(200, "success", null);
    }

    @RequestMapping("/getCarType")
    @ResponseBody
    public Map getCarType(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CarType> carTypeList = carTypeMapper.selectAll();
            return ToJsonUtil.toListMap(200, "success", carTypeList);
        } catch (Exception e) {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/getCarModel")
    @ResponseBody
    public Map getCarModel(Long typeId, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<CarModel> carModelList = carModelMapper.selectByTypeId(typeId);
            return ToJsonUtil.toListMap(200, "success", carModelList);
        } catch (Exception e) {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/updateCarModel")
    @ResponseBody
    @Transactional
    public Map updateCarModel(CarModel carModel, String carModelParamList, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            List<CarModelParam> carModelParams = (List<CarModelParam>) JSONArray.parse(carModelParamList);
            if (carModelMapper.updateByPrimaryKeySelective(carModel) > 0) {
                for (CarModelParam carModelParam : carModelParams) {
                    carModelParamMapper.updateByPrimaryKeySelective(carModelParam);
                }
                return ToJsonUtil.toEntityMap(200, "success", null);
            } else {
                return ToJsonUtil.toEntityMap(400, "error", null);
            }
        } catch (JSONException je) {
            return ToJsonUtil.toEntityMap(400, "json error", null);
        } catch (Exception e) {
            return ToJsonUtil.toEntityMap(400, "sql error", null);
        }
    }

    @RequestMapping("/toggleCarModel")
    @ResponseBody
    public Map toggleCarModel(Long id, Integer status, HttpServletRequest request, HttpServletResponse response) {

        CarModel carModel = carModelMapper.selectByPrimaryKey(id);
        carModel.setStatus(status);

        if (carModelMapper.updateByPrimaryKeySelective(carModel) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }

    @RequestMapping("/carHalllist")
    @ResponseBody
    public Map carHalllist(String typeName, Integer importFlag, Integer pageNo, Integer pageSize,
            HttpServletRequest request, HttpServletResponse response) {

        Long totalCount = carHallMapper.countByCondition(typeName, importFlag);
        List<CarHall> carHallList = null;
        if (totalCount > 0) {
            carHallList = carHallMapper.selectByCondition(typeName, importFlag, getPageMap(pageNo, pageSize));
        }

        return ToJsonUtil.toPagetMap(200, "success", getPageNo(pageNo), getPageSize(pageSize), totalCount, carHallList);
    }

//    @RequestMapping("/getCarHallPics")
//    @ResponseBody
//    public Map getCarHallPics(Long carHallId, HttpServletRequest request, HttpServletResponse response) {
//        try {
//            List<CarHallPic> carHallPics = carHallPicMapper.selectByHallId(carHallId);
//
//            return ToJsonUtil.toListMap(200, "success", carHallPics);
//        } catch (Exception e) {
//            return ToJsonUtil.toListMap(400, "error", null);
//        }
//    }

    @RequestMapping("/getCarHallDetail")
    @ResponseBody
    public Map getCarHallDetail(Long carHallId, HttpServletRequest request, HttpServletResponse response) {
        try {
            CarHall carHall = carHallMapper.selectByPrimaryKey(carHallId);
            List<CarHallPic> carHallPics = carHallPicMapper.selectByHallId(carHallId);
            List<String> carHallPicStr = new ArrayList<String>();
            for(int i=0;i<carHallPics.size();i++){
                carHallPicStr.add(carHallPics.get(i).getImg());
            }
            carHall.setCarHallPics(carHallPicStr);
            
            List<CarHallModel> carHallModels = carHallModelMapper.selectByHallId(carHallId);
            List<Long> carHallModelStr = new ArrayList<Long>();
            for(int i=0;i<carHallModels.size();i++){
                carHallModelStr.add(carHallModels.get(i).getCarModelId());
            }
            carHall.setCarModelIds(carHallModelStr);
            
            return ToJsonUtil.toEntityMap(200, "success", carHall);
        } catch (Exception e) {
            return ToJsonUtil.toListMap(400, "error", null);
        }
    }

    @RequestMapping("/addCarHall")
    @ResponseBody
    @Transactional
    public Map addCarHall(CarHall carHall, HttpServletRequest request,
            HttpServletResponse response) {
        Long userId = CookieUtil.getUserID(request);
        Long now = System.currentTimeMillis();
        carHall.setCreateAt(now);
        carHall.setCreateBy(userId);
        carHall.setUpdateAt(now);
        carHall.setUpdateBy(userId);
        carHall.setStatus(CarHall.StatusActive);
        carHallMapper.insertSelective(carHall);
        Long carHallId = carHall.getId();
        try {
            if (carHall.getCarHallPics() != null) {
                for (String img : carHall.getCarHallPics()) {
                    CarHallPic carHallPic = new CarHallPic();
                    carHallPic.setCarHallId(carHallId);
                    carHallPic.setImg(img);
                    carHallPicMapper.insertSelective(carHallPic);
                }
            }
            if (carHall.getCarModelIds() != null) {
                for (Long carModelId : carHall.getCarModelIds()) {
                    CarHallModel carHallModel = new CarHallModel();
                    carHallModel.setCarHallId(carHallId);
                    carHallModel.setCarModelId(carModelId);
                    carHallModelMapper.insertSelective(carHallModel);

                }
            }
        } catch (JSONException je) {
            return ToJsonUtil.toEntityMap(400, "json error", null);
        }
        return ToJsonUtil.toEntityMap(200, "success", null);
    }

    @RequestMapping("/updateCarHall")
    @ResponseBody
    @Transactional
    public Map updateCarHall(CarHall carHall, HttpServletRequest request,
            HttpServletResponse response) {
        carHallMapper.updateByPrimaryKeySelective(carHall);
        Long carHallId = carHall.getId();
        try {
            if (carHall.getCarHallPics() != null && carHall.getCarHallPics().size() > 0) {
                carHallPicMapper.deleteByCarHallId(carHallId);
                for (String img : carHall.getCarHallPics()) {
                    CarHallPic carHallPic = new CarHallPic();
                    carHallPic.setCarHallId(carHallId);
                    carHallPic.setImg(img);
                    carHallPicMapper.insertSelective(carHallPic);
                }
            }

            if (carHall.getCarModelIds() != null && carHall.getCarModelIds().size() > 0) {
                carHallModelMapper.deleteByCarHallId(carHallId);
                for (Long carModelId : carHall.getCarModelIds()) {
                    CarHallModel carHallModel = new CarHallModel();
                    carHallModel.setCarHallId(carHallId);
                    carHallModel.setCarModelId(carModelId);
                    carHallModelMapper.insertSelective(carHallModel);

                }
            }
        } catch (JSONException je) {
            return ToJsonUtil.toEntityMap(400, "json error", null);
        }
        return ToJsonUtil.toEntityMap(200, "success", null);
    }

    @RequestMapping("/toggleCarHall")
    @ResponseBody
    public Map toggleCarHall(Long id, Integer status, HttpServletRequest request, HttpServletResponse response) {

        CarHall carHall = carHallMapper.selectByPrimaryKey(id);
        carHall.setStatus(status);

        if (carHallMapper.updateByPrimaryKeySelective(carHall) > 0) {
            return ToJsonUtil.toEntityMap(200, "success", null);
        } else {
            return ToJsonUtil.toEntityMap(400, "error", null);
        }
    }
}
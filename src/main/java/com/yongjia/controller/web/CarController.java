package com.yongjia.controller.web;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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
import com.yongjia.model.CarModelItemParam;
import com.yongjia.model.CarModelParam;
import com.yongjia.model.CarType;
import com.yongjia.model.User;
import com.yongjia.utils.CookieUtil;
import com.yongjia.utils.ExcelUtil;
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

    @RequestMapping("/addCarType")
    @ResponseBody
    public Map addCarType(CarType carType, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (carTypeMapper.insertSelective(carType) > 0) {
                return ToJsonUtil.toEntityMap(200, "success", null);
            } else {
                return ToJsonUtil.toEntityMap(400, "insert error", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ToJsonUtil.toEntityMap(500, "server error", null);
        }

    }

    @RequestMapping("/importCarModel")
    @ResponseBody
    @Transactional
    public Map importCarModel(Long typeId, String typeName, String paramsStr, HttpServletRequest request,
            HttpServletResponse response) {

        try {
            List<CarModel> carModels = JSONArray.parseArray(paramsStr, CarModel.class);
            for (CarModel carModel : carModels) {
                carModel.setTypeId(typeId);
                carModel.setTypeName(typeName);
                carModelMapper.insertSelective(carModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ToJsonUtil.toEntityMap(400, "sql error maybe", null);
        }
        return ToJsonUtil.toEntityMap(200, "success", null);
    }

    @RequestMapping("/importCarModelExcel")
    @ResponseBody
    public Map importCarModelExcel(@RequestParam(value = "file", required = true) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response) {

        List<CarModel> allParams = new ArrayList<CarModel>();
        log.info("file name is " + file.getName());
        log.info("file original name is " + file.getOriginalFilename());
        /**
         * excel 2003-2007
         */
        if (file.getOriginalFilename().endsWith(".xls")) {
            try {
                POIFSFileSystem fs = new POIFSFileSystem(file.getInputStream());
                HSSFWorkbook wb = new HSSFWorkbook(fs);
                for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                    HSSFSheet sheet = wb.getSheetAt(i);
                    List<CarModelParam> params = new ArrayList<CarModelParam>();
                    String paramName = null;
                    CarModelParam param = new CarModelParam();
                    List<CarModelItemParam> childParams = new ArrayList<CarModelItemParam>();
                    for (int j = 0; j < sheet.getLastRowNum(); j++) {

                        HSSFRow row = sheet.getRow(j);
                        HSSFCell cell1 = row.getCell(0);
                        if (paramName == null || !ExcelUtil.getCellStringValue(cell1).equals(paramName)) {
                            if (paramName != null) {
                                param.setValue(childParams);
                                params.add(param);
                            }
                            param = new CarModelParam();
                            paramName = ExcelUtil.getCellStringValue(cell1);
                            param.setName(paramName);
                            childParams = new ArrayList<CarModelItemParam>();
                        }
                        CarModelItemParam carModelItemParam = new CarModelItemParam();
                        HSSFCell cell2 = row.getCell(1);
                        carModelItemParam.setName(ExcelUtil.getCellStringValue(cell2));
                        HSSFCell cell3 = row.getCell(2);
                        carModelItemParam.setValue(ExcelUtil.getCellStringValue(cell3));
                        childParams.add(carModelItemParam);

                    }
                    param.setValue(childParams);
                    params.add(param);

                    CarModel carModel = new CarModel();
                    String carModelName = sheet.getSheetName();
                    carModel.setCarModelName(carModelName);
                    carModel.setStatus(CarModel.StatusActive);
                    carModel.setParams(JSONArray.toJSONString(params));
                    allParams.add(carModel);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return ToJsonUtil.toEntityMap(400, "io error", null);
            } catch (Exception e) {
                e.printStackTrace();
                return ToJsonUtil.toEntityMap(400, "sql error maybe", null);
            }
        }
        /**
         * excel 2010-2013
         */
        else if (file.getOriginalFilename().endsWith(".xlsx")) {
            try {
                XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
                for (int i = 0; i < wb.getNumberOfSheets(); i++) {
                    XSSFSheet sheet = wb.getSheetAt(i);
                    List<CarModelParam> params = new ArrayList<CarModelParam>();
                    String paramName = null;
                    CarModelParam param = new CarModelParam();
                    List<CarModelItemParam> childParams = new ArrayList<CarModelItemParam>();
                    for (int j = 0; j < sheet.getLastRowNum(); j++) {

                        XSSFRow row = sheet.getRow(j);
                        XSSFCell cell1 = row.getCell(0);
                        if (paramName == null || !ExcelUtil.getCellStringValue(cell1).equals(paramName)) {
                            if (paramName != null) {
                                param.setValue(childParams);
                                params.add(param);
                            }
                            param = new CarModelParam();
                            paramName = ExcelUtil.getCellStringValue(cell1);
                            param.setName(paramName);
                            childParams = new ArrayList<CarModelItemParam>();
                        }
                        CarModelItemParam carModelItemParam = new CarModelItemParam();
                        XSSFCell cell2 = row.getCell(1);
                        carModelItemParam.setName(ExcelUtil.getCellStringValue(cell2));
                        XSSFCell cell3 = row.getCell(2);
                        carModelItemParam.setValue(ExcelUtil.getCellStringValue(cell3));
                        childParams.add(carModelItemParam);

                    }
                    param.setValue(childParams);
                    params.add(param);
                    
                    CarModel carModel = new CarModel();
                    String carModelName = sheet.getSheetName();
                    carModel.setCarModelName(carModelName);
                    carModel.setStatus(CarModel.StatusActive);
                    carModel.setParams(JSONArray.toJSONString(params));
                    allParams.add(carModel);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return ToJsonUtil.toEntityMap(400, "io error", null);
            } catch (Exception e) {
                e.printStackTrace();
                return ToJsonUtil.toEntityMap(400, "sql error maybe", null);
            }
        } else {
            return ToJsonUtil.toEntityMap(400, "只支持xls及xlsx文件", null);
        }

        return ToJsonUtil.toListMap(200, "success", allParams);
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

    // @RequestMapping("/getCarHallPics")
    // @ResponseBody
    // public Map getCarHallPics(Long carHallId, HttpServletRequest request, HttpServletResponse response) {
    // try {
    // List<CarHallPic> carHallPics = carHallPicMapper.selectByHallId(carHallId);
    //
    // return ToJsonUtil.toListMap(200, "success", carHallPics);
    // } catch (Exception e) {
    // return ToJsonUtil.toListMap(400, "error", null);
    // }
    // }

    @RequestMapping("/getCarHallDetail")
    @ResponseBody
    public Map getCarHallDetail(Long id, HttpServletRequest request, HttpServletResponse response) {
        try {
            CarHall carHall = carHallMapper.selectByPrimaryKey(id);
            List<CarHallPic> carHallPics = carHallPicMapper.selectByHallId(id);
            List<String> carHallPicStr = new ArrayList<String>();
            for (int i = 0; i < carHallPics.size(); i++) {
                carHallPicStr.add(carHallPics.get(i).getImg());
            }
            carHall.setCarHallPics(carHallPicStr);

            List<CarHallModel> carHallModels = carHallModelMapper.selectByHallId(id);
            List<Long> carHallModelStr = new ArrayList<Long>();
            for (int i = 0; i < carHallModels.size(); i++) {
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
    public Map addCarHall(CarHall carHall, HttpServletRequest request, HttpServletResponse response) {
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
    public Map updateCarHall(CarHall carHall, HttpServletRequest request, HttpServletResponse response) {
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
package com.yongjia.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;

public class ExcelUtil {

    public static String getCellStringValue(XSSFCell cell) {
        String cellValue = "";
        switch (cell.getCellType()) {
        case XSSFCell.CELL_TYPE_STRING:// 字符串类型
            cellValue = cell.getStringCellValue();
            if (cellValue.trim().equals("") || cellValue.trim().length() <= 0)
                cellValue = " ";
            break;
        case XSSFCell.CELL_TYPE_NUMERIC: // 数值类型
            cellValue = String.valueOf(cell.getNumericCellValue());
            break;
        case XSSFCell.CELL_TYPE_FORMULA: // 公式
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cellValue = String.valueOf(cell.getNumericCellValue());
            break;
        case XSSFCell.CELL_TYPE_BLANK:
            cellValue = " ";
            break;
        case XSSFCell.CELL_TYPE_BOOLEAN:
            break;
        case XSSFCell.CELL_TYPE_ERROR:
            break;
        default:
            break;
        }
        return cellValue;
    }
    
    public static String getCellStringValue(HSSFCell cell) {
        String cellValue = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:// 字符串类型
            cellValue = cell.getStringCellValue();
            if (cellValue.trim().equals("") || cellValue.trim().length() <= 0)
                cellValue = " ";
            break;
        case HSSFCell.CELL_TYPE_NUMERIC: // 数值类型
            cellValue = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_FORMULA: // 公式
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cellValue = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            cellValue = " ";
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            break;
        case HSSFCell.CELL_TYPE_ERROR:
            break;
        default:
            break;
        }
        return cellValue;
    }
}

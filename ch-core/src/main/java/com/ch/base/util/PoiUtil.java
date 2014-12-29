package com.ch.base.util;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

/**
 * POI工具类
 * 
 * 
 */
public class PoiUtil {

	/**
	 * 由于Excel当中的单元格Cell存在类型,若获取类型错误就会产生异常, 所以通过此方法将Cell内容全部转换为String类型
	 * 
	 * @param cell
	 * @return
	 */
	public static String getCellValue(Cell cell) {
		String str = null;
		if (cell != null) {
			switch (cell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				str = "";
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				str = String.valueOf(cell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				str = String.valueOf(cell.getCellFormula());
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
					str = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(cell.getDateCellValue());
				} else {
					str = String.valueOf((long) cell.getNumericCellValue());
				}
				break;
			case Cell.CELL_TYPE_STRING:
				str = String.valueOf(cell.getStringCellValue());
				break;
			default:
				str = null;
				break;
			}
		}
		return StringUtils.trim(str);
	}

}

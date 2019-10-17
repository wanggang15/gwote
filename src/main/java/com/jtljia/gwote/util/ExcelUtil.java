package com.jtljia.gwote.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormatter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <Class Description>
 * <p>
 * <p>
 * <Change History>
 * -----------------------------------------------------------------------------
 * Author Date Version Comments
 * -----------------------------------------------------------------------------
 * Jason Fang 2015-04-24 1.0 Create
 * -----------------------------------------------------------------------------
 */
public class ExcelUtil {

    private static Logger log = LoggerFactory.getLogger(ExcelUtil.class);

    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static ArrayList<ArrayList<String>> readExcel2(InputStream stream, int index, int startRow, int endCol) {
        try {
            ArrayList<ArrayList<String>> dataList = new ArrayList<>();

            Workbook wb = WorkbookFactory.create(stream);
            Sheet sheet = wb.getSheetAt(index);
            for (int i = startRow; i <= sheet.getPhysicalNumberOfRows(); i++) {
                Row sourceRow = sheet.getRow(i);
                if (sourceRow == null) {
                    continue;
                }

                ArrayList<String> itemList = new ArrayList<>();
                for (int j = 0; j <= endCol; j++) {
                    String value = getCellValueAsStr(sourceRow.getCell(j));
                    itemList.add(j, value.trim());
                }
                dataList.add(itemList);
            }
            return dataList;
        } catch (Exception e) {
            log.error("readExcel2异常", e);
            return null;
        }
    }

    private static String getCellValueAsStr(Cell cell) {
        HSSFDataFormatter format = new HSSFDataFormatter();
        return format.formatCellValue(cell).trim();
    }

    public static LinkedList<LinkedList<String>> readExcel(InputStream stream, int index, int startRow, int endCol) {
        LinkedList<LinkedList<String>> dataList = new LinkedList<>();
        try {
            Workbook wb = WorkbookFactory.create(stream);
            Sheet sheet = wb.getSheetAt(index);
            for (Row row : sheet) {
                LinkedList<String> itemList = new LinkedList<>();
                for (int j = 0; j < endCol; j++) {
                    Cell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String value = cell.getStringCellValue();
                    itemList.add(j, value == null ? "" : value.trim());
                }
                dataList.add(itemList);
            }
        } catch (Exception e) {
            log.error("readExcel异常", e);
        }
        return dataList;
    }

    public static List<String[]> readDataFromExcel(InputStream stream, int index, int startRow, int endCol) {
        List<String[]> dataList = new LinkedList<>();
        try {
            Workbook wb = WorkbookFactory.create(stream);
            Sheet sheet = wb.getSheetAt(index);
            for (int i = startRow; i < sheet.getLastRowNum(); i++) {
                String[] item = new String[endCol + 1];
                for (int j = 0; j < endCol; j++) {
                    Cell cell = sheet.getRow(i).getCell(j, Row.CREATE_NULL_AS_BLANK);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    String value = cell.getStringCellValue();
                    item[j] = value == null ? "" : value.trim();
                }
                dataList.add(item);
            }
        } catch (Exception e) {
            log.error("readDataFromExcel 异常", e);
        }
        return dataList;
    }

    public static Map<String, LinkedList<LinkedList<String>>> readMultiExcel(InputStream stream, int startRow, int endCol) {
        try {
            Map<String, LinkedList<LinkedList<String>>> resultMap = new HashMap<>();
            LinkedList<LinkedList<String>> dataList = new LinkedList<LinkedList<String>>();

            Workbook wb = WorkbookFactory.create(stream);

            for (int i = 0; i <= wb.getNumberOfSheets(); i++) {
                Sheet sheet = wb.getSheetAt(i);
                for (Row row : sheet) {
                    LinkedList<String> itemList = new LinkedList<String>();
                    for (int j = 0; j < endCol; j++) {
                        Cell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
                        cell.setCellType(Cell.CELL_TYPE_STRING);

                        String value = cell.getStringCellValue();
                        itemList.add(j, value == null ? "" : value.trim());
                    }
                    dataList.add(itemList);
                }
                resultMap.put(sheet.getSheetName(), dataList);
            }
            return resultMap;
        } catch (Exception e) {
            return null;
        }
    }

    public static void writeExcel(InputStream stream, int index, List<String> result, int startRow, int colindex, String newFilePath) {
        try {
            Workbook wb = WorkbookFactory.create(stream);
            Sheet sheet = wb.getSheetAt(index);
            int rows = sheet.getLastRowNum();

            CellStyle cellStyle = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.RED.index);
            cellStyle.setFont(font);

            for (int i = 0; i < result.size(); i++) {
                if (StringUtils.isNotBlank(result.get(i))) {
                    Cell cell = sheet.getRow(i + startRow).getCell(colindex, Row.CREATE_NULL_AS_BLANK);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(result.get(i));
                }
            }
            FileOutputStream os = new FileOutputStream(newFilePath);
            wb.write(os);
            os.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }


    public static LinkedList<LinkedList<String>> readExcel(Workbook wb, int index, int startRow, int endCol) {
        try {
            LinkedList<LinkedList<String>> dataList = new LinkedList<LinkedList<String>>();

            Sheet sheet = wb.getSheetAt(index);

            for (Row row : sheet) {
                LinkedList<String> itemList = new LinkedList<String>();
                for (int j = 0; j < endCol; j++) {
                    Cell cell = row.getCell(j, Row.CREATE_NULL_AS_BLANK);
                    cell.setCellType(Cell.CELL_TYPE_STRING);

                    String value = cell.getStringCellValue();
                    itemList.add(j, value == null ? "" : value.trim());
                }
                dataList.add(itemList);
            }

            return dataList;
        } catch (Exception e) {
            return null;
        }
    }

    public static Workbook writeExcel(Workbook wb, int index, List<String> result, int startRow, int colindex) {
        try {
            Sheet sheet = wb.getSheetAt(index);
            int rows = sheet.getLastRowNum();

            CellStyle cellStyle = wb.createCellStyle();
            Font font = wb.createFont();
            font.setBoldweight(Font.BOLDWEIGHT_BOLD);
            font.setColor(HSSFColor.RED.index);
            cellStyle.setFont(font);

            for (int i = 0; i < result.size(); i++) {
                if (StringUtils.isNotBlank(result.get(i))) {
                    Cell cell = sheet.getRow(i + startRow).getCell(colindex, Row.CREATE_NULL_AS_BLANK);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(result.get(i));
                }
            }
            return wb;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    /**
     * 创建excel文档，
     *
     * @param list        数据
     * @param keys        list中map的key数组集合
     * @param columnNames excel的列名
     */
    public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String[] columnNames) {
        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }

        //设置列名
        Row row = sheet.createRow((short) 0);
        CellStyle titleStyle = createCellStyle(wb);
        titleStyle.setFont(createTitleFont(wb));
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(titleStyle);
        }
        //设置每行每列的值
        CellStyle contentStyle = createCellStyle(wb);
        contentStyle.setFont(createStringCellFont(wb));
        for (int i = 1; i < list.size(); i++) {
            // Row 行,Cell 方格 , Row 和 Cell 都是从0开始计数的
            // 创建一行，在页sheet上
            Row row1 = sheet.createRow(i);
            // 在row行上创建一个方格
            for (int j = 0; j < keys.length; j++) {
                Cell cell = row1.createCell(j);
                cell.setCellValue(list.get(i).get(keys[j]) == null ? " " : list.get(i).get(keys[j]).toString());
                cell.setCellStyle(contentStyle);
            }
        }
        return wb;
    }

    private static Font createTitleFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        return font;
    }

    private static Font createStringCellFont(Workbook workbook) {
        Font font = workbook.createFont();
        font.setFontHeightInPoints((short) 10);
        font.setColor(IndexedColors.BLACK.getIndex());
        return font;
    }

    // 获取用于列名的样式
    private static CellStyle createCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);
        cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        return cellStyle;
    }

    /**
     * 创建excel文档
     *
     * @param list        数据
     * @param keys        list中map的key数组集合
     * @param columnNames excel的列名
     * @param formats     数据类型
     */
   /* public static Workbook createWorkBook(List<Map<String, Object>> list, String[] keys, String[] columnNames, ExportQueryTo.FormatEnum[] formats) {
        // 创建excel工作簿
        Workbook wb = new XSSFWorkbook();
        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(list.get(0).get("sheetName").toString());
        // 手动设置列宽。第一个参数表示要为第几列设；，第二个参数表示列的宽度，n为列高的像素数。
        for (int i = 0; i < keys.length; i++) {
            sheet.setColumnWidth((short) i, (short) (35.7 * 150));
        }
        // 创建第一行
        Row row = sheet.createRow((short) 0);
        // 设置列名
        CellStyle titleStyle = createCellStyle(wb);
        titleStyle.setFont(createTitleFont(wb));
        for (int i = 0; i < columnNames.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(columnNames[i]);
            cell.setCellStyle(titleStyle);
        }
        if (formats == null) {
            //设置每行每列的值
            CellStyle contentStyle = createCellStyle(wb);
            contentStyle.setFont(createStringCellFont(wb));
            for (int i = 1; i < list.size(); i++) {
                Row row1 = sheet.createRow(i);
                for (int j = 0; j < keys.length; j++) {
                    Cell cell = row1.createCell(j);
                    cell.setCellValue(list.get(i).computeIfAbsent(keys[j], v -> " ").toString());
                    cell.setCellStyle(contentStyle);
                }
            }
        } else {
            CellStyle defaultStyle = createCellStyle(wb);
            defaultStyle.setFont(createStringCellFont(wb));
            Map<ExportQueryTo.FormatEnum, CellStyle> contentStyles = getContentStyles(formats, wb);
            //设置每行每列的值
            for (int i = 1; i < list.size(); i++) {
                Row row1 = sheet.createRow(i);
                for (int j = 0; j < keys.length; j++) {
                    Cell cell = row1.createCell(j);
                    if (j < formats.length && formats[j] != null) {
                        setCellValue(cell, list.get(i).get(keys[j]), formats[j]);
                        cell.setCellStyle(contentStyles.computeIfAbsent(formats[j], v -> defaultStyle));
                    } else {
                        cell.setCellValue(list.get(i).computeIfAbsent(keys[j], v -> " ").toString());
                        cell.setCellStyle(defaultStyle);
                    }
                }
            }
        }
        return wb;
    }

    private static void setCellValue(Cell cell, Object value, ExportQueryTo.FormatEnum format) {
        if (value == null) {
            cell.setCellValue(" ");
            return;
        }

        switch (format) {
            case DATE:
                cell.setCellValue((Date) value);
                break;
            case NUMBER_SCALE_2:
                cell.setCellValue(((BigDecimal) value).doubleValue());
                break;
            case NUMBER_SCALE_4:
                cell.setCellValue(value.toString());
                break;
            case STRING:
            default:
                cell.setCellValue(value.toString());
                break;
        }
    }

    // 获取formats所有样式集合
    private static Map<ExportQueryTo.FormatEnum, CellStyle> getContentStyles(ExportQueryTo.FormatEnum[] formats, Workbook workbook) {
        Map<ExportQueryTo.FormatEnum, CellStyle> result = new HashMap<>();
        for (ExportQueryTo.FormatEnum format : formats) {
            if (result.containsKey(format))
                continue;

            CellStyle cellStyle;
            CreationHelper createHelper = workbook.getCreationHelper();
            switch (format) {
                case DATE:
                    cellStyle = createCellStyle(workbook);
                    cellStyle.setFont(createStringCellFont(workbook));
                    cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("yyyy/mm/dd"));
                    break;
                case NUMBER_SCALE_2:
                    cellStyle = createCellStyle(workbook);
                    cellStyle.setFont(createStringCellFont(workbook));
                    cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("0.00"));
                    break;
                case NUMBER_SCALE_4:
                case STRING:
                default:
                    cellStyle = createCellStyle(workbook);
                    cellStyle.setFont(createStringCellFont(workbook));
                    break;
            }
            result.put(format, cellStyle);
        }
        return result;
    }

    *//**
     * 对象集合转换成map集合(不改变数据的类型)
     *//*
    public static <T> List<Map<String, Object>> transferListWithoutChangeDataType(List<T> list) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (T t : list) {
            Map<String, Object> map = obj2Map(t, false);
            result.add(map);
        }
        return result;
    }
    public static <T> List<Map<String, Object>> transferList(List<T> list) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (T t : list) {
            Map<String, Object> map = obj2Map(t, true);
            result.add(map);
        }
        return result;
    }

    *//**
     * 对象转换成map
     *//*
    public static <T> Map<String, Object> obj2Map(T obj, boolean changeDataType) {
        Map<String, Object> map = new HashMap<>();
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(obj.getClass());
        } catch (IntrospectionException ignored) {
        }
        if (beanInfo == null) return map;
        if (obj instanceof Map) return (Map<String, Object>) obj;
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
        for (PropertyDescriptor property : propertyDescriptors) {
            String key = property.getName();
            if (key.compareToIgnoreCase("class") == 0) {
                continue;
            }
            Method getter = property.getReadMethod();
            Object tmp = null;
            try {
                String typeName = property.getPropertyType().getSimpleName();
                Object o = getter != null ? getter.invoke(obj) : null;
                if (changeDataType) {
                    if (o != null && typeName.equalsIgnoreCase("Date")) {
                        Date d = (Date) o;
                        tmp = DateUtil.dateToString(d, "yyyy-MM-dd HH:mm:ss").substring(0, 19);
                    } else if (o != null && typeName.equalsIgnoreCase("BigDecimal")) {
                        BigDecimal d = (BigDecimal) o;
                        tmp = d.setScale(2, RoundingMode.HALF_UP).doubleValue();
                    } else {
                        tmp = o;
                    }
                } else {
                    tmp = o;
                }
            } catch (Exception ignored) {
            }
            map.put(key, tmp);
        }
        return map;
    }

    *//**
     * 设置sheet页标题头和列头
     *
     * @param sheet 页
     * @param title 标题头
     * @param data  列头
     *//*
    public static void setHeaderRow(Sheet sheet, String title, String[] data) {
        setHeaderRow(sheet, 0, title, data);
    }
    public static void setHeaderRow(Sheet sheet, int startRow, String title, String[] data) {
        if (StringUtils.isNotEmpty(title)) {
            Cell headCell = sheet.createRow(startRow).createCell(0);
            headCell.setCellValue(title);
            CellStyle style = sheet.getWorkbook().createCellStyle();
            style.setAlignment(XSSFCellStyle.ALIGN_CENTER);
            if (data.length > 0) {
                sheet.addMergedRegion(new CellRangeAddress(startRow, startRow, 0, (short) (data.length - 1)));
            }
            headCell.setCellStyle(style);
        }
        if (data.length == 0) return;
        Row row = sheet.createRow(startRow + 1);
        for (int i = 0; i < data.length; i++) {
            sheet.setColumnWidth(i, 4000);
            row.createCell(i).setCellValue(data[i]);
        }
    }

    public static <T> void setDataRow(Sheet sheet, int startRow, List<T> list, String[] propertyNames){
        List<Map<String, Object>> dataList = transferListWithoutChangeDataType(list);
        for (int i = startRow; i < dataList.size(); i++) {
            Row row = sheet.createRow(i);
            Map<String, Object> data = dataList.get(i);
            for (int j = 0; j < propertyNames.length; j++) {
                Object obj = data.get(propertyNames[j]);
                if(obj == null) continue;
                String typeName = obj.getClass().getName();
                if(typeName.equalsIgnoreCase("java.math.BigDecimal")){
                    BigDecimal t = (BigDecimal) obj;
                    row.getCell(j).setCellValue(t.setScale(2, RoundingMode.HALF_UP).doubleValue());
                }else if(typeName.equalsIgnoreCase("java.util.Date")){
                    Date t = (Date) obj;
                    row.getCell(j).setCellValue(t);
                }else{
                    row.getCell(j).setCellValue(obj.toString());
                }
            }
        }
    }*/
}

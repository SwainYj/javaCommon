package com.swain.common.utils.utils;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.util.List;

public class ExcelUtils {
    // 默认字体
    private static final String DEFAULT_FONT = "微软雅黑";

    // 默认字体高度
    private static final short DEFAULT_FONT_HEIGHT = 10;

    // 默认列宽：25个字符宽度
    private static final int DEFAULT_COLUMN_WIDTH = 25 * 256;

    // 默认Sheet名称
    private static final String DEFAULT_SHEET_NAME = "Sheet0";

    // 标题行ID
    private static final int HEADER_ROW_ID = 0;

    // 标题样式的下标
    private static final int HEADER_STYLE_INDEX = 1;

    // 数据样式的下标
    private static final int DATA_STYLE_INDEX = 2;

    /**
     * 创建一个空的工作簿
     *
     * @return 空的工作簿
     * @author d00420944 2019/9/17
     */
    public static SXSSFWorkbook createWorkbook() {
        SXSSFWorkbook workbook = new SXSSFWorkbook();

        workbook.createSheet(DEFAULT_SHEET_NAME);

        return workbook;
    }

    /**
     * 创建一个工作簿，写入指定内容
     *
     * @param headers 写入工作簿的内容
     * @return 写入指定内容的工作簿
     * @author d00420944 2019/9/17
     */
    public static SXSSFWorkbook createWorkbookTemplate(List<String> headers) {
        SXSSFWorkbook workbook = createWorkbook();

        if (CollectionUtils.isEmpty(headers)) {
            return workbook;
        }

        Font commonFont = workbook.createFont();
        CellStyle headerStyle = workbook.createCellStyle();
        CellStyle dataStyle = workbook.createCellStyle();
        setFormatExcel(commonFont, headerStyle, dataStyle);

        Sheet sheet = workbook.getSheet(DEFAULT_SHEET_NAME);

        Row row = sheet.createRow(HEADER_ROW_ID);

        for (int cellId = 0; cellId < headers.size(); cellId++) {
            Cell cell = row.createCell(cellId);
            cell.setCellValue(headers.get(cellId));

            cell.setCellStyle(headerStyle);

            // 设置默认列宽
            sheet.setColumnWidth(cellId, DEFAULT_COLUMN_WIDTH);
        }

        return workbook;
    }

    /**
     * 创建一个工作簿，写入指定内容
     *
     * @param rowContents 写入工作簿的内容
     * @return 写入指定内容的工作簿
     * @author d00420944 2019/9/17
     */
    public static void appendDataToWorkbook(SXSSFWorkbook workbook, List<List<String>> rowContents) {
        if (null == workbook) {
            workbook = createWorkbookTemplate(null);
        }

        CellStyle dataStyle = workbook.getCellStyleAt(DATA_STYLE_INDEX);

        Sheet sheet = workbook.getSheet(DEFAULT_SHEET_NAME);

        if (CollectionUtils.isEmpty(rowContents)) {
            return;
        }

        int startRowIndex = sheet.getLastRowNum() + 1;

        List<String> rowContent;
        for (int rowOffset = 0; rowOffset < rowContents.size(); rowOffset++) {
            rowContent = rowContents.get(rowOffset);

            if (CollectionUtils.isEmpty(rowContent)) {
                continue;
            }

            Row row = sheet.createRow(startRowIndex + rowOffset);

            for (int cellId = 0; cellId < rowContent.size(); cellId++) {
                Cell cell = row.createCell(cellId);
                cell.setCellValue(rowContent.get(cellId));

                if (null != dataStyle) {
                    cell.setCellStyle(dataStyle);
                }
            }
        }
    }

    /**
     * 设置文件格式
     */
    private static void setFormatExcel(Font commonFont, CellStyle headerStyle, CellStyle dataStyle) {
        // 文件整体的字体样式
        commonFont.setFontName(DEFAULT_FONT);
        commonFont.setFontHeightInPoints(DEFAULT_FONT_HEIGHT);

        headerStyle.setFont(commonFont);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);

        dataStyle.setFont(commonFont);
        dataStyle.setBorderTop(BorderStyle.THIN);
        dataStyle.setBorderBottom(BorderStyle.THIN);
        dataStyle.setBorderLeft(BorderStyle.THIN);
        dataStyle.setBorderRight(BorderStyle.THIN);
        dataStyle.setAlignment(HorizontalAlignment.CENTER);
    }
}

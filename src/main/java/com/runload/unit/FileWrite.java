package com.runload.unit;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;

public class FileWrite {
    public static void main(String[] args) throws IOException {
        file();
    }

    public static void file() throws IOException {

        XSSFWorkbook workbook = new XSSFWorkbook("D:/数据.xlsx");
        Sheet sheet = workbook.getSheetAt(0);
        int rownum = sheet.getLastRowNum();
        int cellNum = sheet.getRow(0).getLastCellNum();

        for (int i = 1; i < rownum; i++) {
            for (int k = 0; k < cellNum; k++) {
                System.out.println(sheet.getRow(i).getCell(k));
            }
        }
    }
}

package com.simar.reportdpc;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class DAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HashMap<String, Object> getReport() {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("GET_REPORT_BULANAN_RAYHAN");

        Map<String, Object> inParamMap = new HashMap<String, Object>();
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        HashMap<String,Object> Path = (HashMap<String,Object>) simpleJdbcCall.execute(in);
        System.out.println(Path);
        JSONObject test = new JSONObject(Path);
        JSONArray array = test.getJSONArray("CURSORPARAM");

        List<String> temp = new ArrayList<>();
            try{
                Workbook workbook = new XSSFWorkbook();
                Sheet sh = workbook.createSheet("Report");
                String[] columHeadings = {"No. Box", "Kode Cabang","RCC", "Status"};
                Font headerFont = workbook.createFont();
                headerFont.setBold(true);
                headerFont.setFontHeightInPoints((short)12);
                headerFont.setColor(IndexedColors.BLACK.index);

                CellStyle headerStyle = workbook.createCellStyle();
                headerStyle.setFont(headerFont);
                headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                headerStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.index);
                Row headerRow = sh.createRow(0);

                for(int i=0; i<columHeadings.length; i++){
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(columHeadings[i]);
                    cell.setCellStyle(headerStyle);
                }

                CreationHelper creationHelper = workbook.getCreationHelper();
                CellStyle dateStyle = workbook.createCellStyle();
                dateStyle.setDataFormat(creationHelper.createDataFormat().getFormat("MM/dd/yyyy"));
                int rownum =1;
                for(int i = 0; i<array.length(); i++){


                    String str = array.getJSONObject(i).getString("COLUMN_VALUE");
                    JSONObject object = new JSONObject(str);

                    Row row = sh.createRow(rownum++);
                    row.createCell(0).setCellValue(object.getString("BOX_NUMBER"));
                    row.createCell(1).setCellValue(object.get("KODE_CABANG").toString());
                    row.createCell(2).setCellValue(object.get("RCC").toString());
                    row.createCell(3).setCellValue(object.getString("STATUS"));
                }

                for(int i=0; i<columHeadings.length;i++){
                    sh.autoSizeColumn(i);
                }
                Sheet sh2 = workbook.createSheet("Second");

                File currDir = new File(".");
                String path = currDir.getAbsolutePath();
                String fileLocation = path.substring(0, path.length() - 1) + "report-bulanan.xlsx";
                FileOutputStream fileOut = new FileOutputStream(fileLocation);
                workbook.write(fileOut);
                fileOut.close();
                workbook.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        return Path;
    }
}

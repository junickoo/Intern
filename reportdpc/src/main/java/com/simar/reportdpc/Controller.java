package com.simar.reportdpc;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/simar")
public class Controller {
    @Autowired
    DAO DAO;

    @GetMapping(path = "/report-dpc")
    public String getReport(){

        JSONObject temp1 = new JSONObject(DAO.getReport());
        JSONArray tempArray = temp1.getJSONArray("CURSORPARAM");
        System.out.println(tempArray);


        return "Test";
    }
}

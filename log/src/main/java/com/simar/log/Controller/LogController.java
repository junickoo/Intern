package com.simar.log.Controller;

import com.simar.log.payload.Log;
import com.simar.log.logDAO.logDAO;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController

@RequestMapping(value = "/simar")
public class LogController {
    @Autowired
    logDAO logDAO;

    @GetMapping(path = "/getLog")
    public Map<String, Object> getLog(@RequestBody Log log){
        List<Object> outputSchema = new ArrayList<>();
        Map<String, Object> outputApi = new HashMap<>();
        List<Object> data = new ArrayList<>();
        JSONObject temp1 = new JSONObject(logDAO.getLog(log.getObjectId()));
        JSONArray tempArray = temp1.getJSONArray("CURSORPARAM");

        for(int j=0; j < tempArray.length(); j++) {
            Map<String, String> tempOutput = new HashMap<>();
            String str = tempArray.getJSONObject(j).getString("COLUMN_VALUE");
            JSONObject object = new JSONObject(str);
            tempOutput.put("USER_ID_APPROVER", object.getString("USER_ID_APPROVER"));
            tempOutput.put("USER_ID_PENGAJU", object.getString("USER_ID_PENGAJU"));
            tempOutput.put("WAKTU_PENGAJUAN", object.getString("WAKTU_PENGAJUAN"));
            tempOutput.put("WAKTU_APPROVAL", object.getString("WAKTU_APPROVAL"));
            tempOutput.put("AKTIVITAS", object.getString("AKTIVITAS"));
            outputSchema.add(tempOutput);
        }
        outputApi.put("output_schema", outputSchema);
        outputApi.put("error_schema", null);
        return outputApi;
    }


}

package com.simar.transaction_type.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.simar.transaction_type.DAO.TransactionTypeDAO;
import com.simar.transaction_type.payload.getUsername;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "simar")
public class TransactionTypeController {
    @Autowired
    TransactionTypeDAO transactionTypeDAO;
    @GetMapping(value = "/getTransaction")
    public Map<String, Object> getTransaction(@RequestBody getUsername getUsername){
        List<Object> outputSchema = new ArrayList<>();
        Map<String, Object> outputApi = new HashMap<>();
        JSONObject temp1 = new JSONObject(transactionTypeDAO.getTransactionType(getUsername.getUsername()));
        JSONArray tempArray = temp1.getJSONArray("CURSORPARAM");

        for(int j=0; j < tempArray.length(); j++) {
            Map<String, Object> tempOutput = new HashMap<>();
            String str = tempArray.getJSONObject(j).getString("JSON_ARRAYAGG(JSON_OBJECT(TRANSACTION_ID,TRANSACTION_TYPE_NAME))");
            String jenisKlasifikasi = tempArray.getJSONObject(j).getString("CLASSIFICATION_NAME");
            JSONArray array = new JSONArray(str);
            List<Object> data = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                Map<String, String> tempTransaksi = new HashMap<>();
                JSONObject object = array.getJSONObject(i);
                tempTransaksi.put("TRANSACTION_ID", object.getString("transaction_id"));
                tempTransaksi.put("TRANSACTION_TYPE_NAME", object.getString("transaction_type_name"));
                data.add(tempTransaksi);
            }
            tempOutput.put("data", data);
            tempOutput.put("jenis_klasifikasi", jenisKlasifikasi);
            outputSchema.add(tempOutput);
        }
        outputApi.put("output_schema", outputSchema);
        outputApi.put("error_schema", null);
        return outputApi;
    }
}
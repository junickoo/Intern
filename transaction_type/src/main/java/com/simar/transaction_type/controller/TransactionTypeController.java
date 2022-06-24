package com.simar.transaction_type.controller;

import com.simar.transaction_type.DAO.TransactionTypeDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.simar.transaction_type.payload.getUsername;

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
        String formatSchema = transactionTypeDAO.getTransactionType(getUsername.getUsername()).get("OUT_TRANSACTION_BY_CLASSIFICATION").toString();
        Map<String, String> tempJenisKlasifikasi = new HashMap<>();
        List<Object> outputSchema = new ArrayList<>();

        Map<String, Object> outputApi = new HashMap<>();

        String[] arrOfStr = formatSchema.split("#");
        for(int j = 1; j< arrOfStr.length; j++) {
            String[] tempTransByClassification = arrOfStr[j].split("~id~");
            List<Map<String, String>> data = new ArrayList<>();
            Map<String, Object> outputJenisklasifikasi = new HashMap<>();
            for (int i = 0; i < tempTransByClassification.length; i++) {
                if (i != 0) {

                    Map<String, String> tempMap = new HashMap<>();
                    String[] tempTransaction = tempTransByClassification[i].split("~name~");
                    tempMap.put("TRANSACTION_ID", tempTransaction[0]);
                    tempMap.put("TRANSACTION_NAME", tempTransaction[1]);
                    data.add(tempMap);
                } else if (i == 0) {
                    outputJenisklasifikasi.put("jenis_klasifikasi", tempTransByClassification[0]);
                }
            }
            outputJenisklasifikasi.put("data", data);
            outputSchema.add(outputJenisklasifikasi);
        }
        outputApi.put("output_schema", outputSchema);
        outputApi.put("error_schema", null);


//        transactionFormat.formatByClassification(formatSchema);

        return outputApi;
    }
}
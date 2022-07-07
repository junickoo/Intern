package com.simar.peminjaman.Controller;

import com.simar.peminjaman.DAO.PeminjamanDAO;
import com.simar.peminjaman.Model.Peminjaman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/borrow-arsip")
public class PeminjamanController {

    @Autowired
    private PeminjamanDAO penyimpananDAO;

    HashMap<String, Object> response = new HashMap<>();
    HashMap<String, Object> errorSchema = new HashMap<>();

    @PostMapping
    public HashMap<String, Object> borrowArsip(@Valid @RequestBody Peminjaman penyimpanan, Errors errors){
        List<String> msg = new ArrayList<>();
        if(errors.hasErrors()){
            for(ObjectError error : errors.getAllErrors()){
                msg.add(error.getDefaultMessage());
            }
            String msgId = "wajib (tidak boleh kosong) : " + msg;
            String msgEn = "required (not null) : " + msg;
            HashMap<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("indonesia", msgId);
            errorMessage.put("english", msgEn);
            errorSchema.put("message", errorMessage);
            errorSchema.put("status", 404);
            response.put("output_schema", null);
            response.put("error_schema", errorSchema);
            return response;
        }
        Integer status = null;
        boolean errorStatus = false;

        HashMap<String, Object> penyimpananOutput = penyimpananDAO.postBorrow(penyimpanan.getArchiveId(), penyimpanan.getLoginName(), penyimpanan.getAddress(), penyimpanan.getServiceType(), penyimpanan.getCreatorPhone(), penyimpanan.getReason());

        if(penyimpananOutput.get("STATUS") != null){
            errorStatus = penyimpananOutput.get("STATUS").toString().equals("404");
        }
        Object errorCode = penyimpananOutput.get("ERRCODE");
        Object messageId = penyimpananOutput.get("ERRMSGID");
        Object messageEng = penyimpananOutput.get("ERRMSGEN");


        if(errorStatus) {
            HashMap<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("english", messageEng);
            errorMessage.put("indonesia", messageId);
            errorSchema.put("error_code", errorCode);
            errorSchema.put("error_message", errorMessage);
            response.put("output_schema", null);
            response.put("error_schema", errorSchema);
            return response;
        }
        else {
            HashMap<String, Object> outputSchema = new HashMap<>();
            outputSchema.put("appraisal_status", "PENDING_APPROVAL_KABAG");
            outputSchema.put("status", "BERHASIL_INPUT");
            response.put("output_schema", outputSchema);
            response.put("error_schema", null);
        }

        return  response;
    }
}

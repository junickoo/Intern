package com.simar.peminjaman.Controller;

import com.simar.peminjaman.DAO.PeminjamanDAO;
import com.simar.peminjaman.Model.Peminjaman;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/borrow-arsip")
public class PeminjamanController {

    @Autowired
    private PeminjamanDAO penyimpananDAO;

    HashMap<String, Object> response = new HashMap<>();
    @PostMapping
    public HashMap<String, Object> borrowArsip(@RequestBody Peminjaman penyimpanan){
        String reason = penyimpanan.getReason();
        Integer status = null;
        boolean errorStatus = false;
        if(reason.equalsIgnoreCase("lain-lain")){
            reason = "kebutuhan internal XYZ";
            System.out.print(reason);
        }

        HashMap<String, Object> penyimpananOutput = penyimpananDAO.postBorrow(penyimpanan.getArchiveId(), penyimpanan.getLoginName(), penyimpanan.getAddress(), penyimpanan.getServiceType(), penyimpanan.getCreatorPhone(), reason);

        System.out.println(penyimpananOutput.get("STATUS"));
        if(penyimpananOutput.get("STATUS") != null){
            System.out.print("BUKAN NULL");
            errorStatus = penyimpananOutput.get("STATUS").toString().equals("404");
            System.out.println(errorStatus);
        }
        else{
            System.out.print("NULL");
        }
        Object errorCode = penyimpananOutput.get("ERRCODE");
        Object messageId = penyimpananOutput.get("ERRMSGID");
        Object messageEng = penyimpananOutput.get("ERRMSGEN");


        if(errorStatus) {
            HashMap<String, Object> errorMessage = new HashMap<>();
            errorMessage.put("english", messageEng);
            errorMessage.put("indonesia", messageId);
            HashMap<String, Object> errorSchema = new HashMap<>();
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
            if(penyimpanan.getServiceType().equalsIgnoreCase("urgent")){
                System.out.println("Sent Email");
            }
        }

        return  response;
    }
}

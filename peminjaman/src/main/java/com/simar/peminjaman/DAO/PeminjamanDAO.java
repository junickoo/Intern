package com.simar.peminjaman.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PeminjamanDAO {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HashMap<String, Object> postBorrow(String archiveId, String loginName, String address, String serviceType, String creatorPhone, String reason) {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("POST_BORROW_ARCHIVE");

        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("p_ARCHIVE_ID", archiveId);
        inParamMap.put("p_USER_LOGIN_NAME", loginName);
        inParamMap.put("p_PICKUP_ADDRESS", address);
        inParamMap.put("p_SERVICE_TYPE", serviceType);
        inParamMap.put("p_CREATOR_PHONE_NUMBER", creatorPhone);
        inParamMap.put("p_REASON", reason);
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        HashMap<String,Object> Path = (HashMap<String,Object>) simpleJdbcCall.execute(in);
        //System.out.println(Path);
        return Path;
    }

}

package com.simar.permanentOut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HashMap<String, Object> putPermanentOut(String boxNum) {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("PUT_PERMANENT_OUT_RAYHAN");

        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("BOXNUMBER", boxNum);
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        HashMap<String,Object> Path = (HashMap<String,Object>) simpleJdbcCall.execute(in);
        //System.out.println(Path);
        return Path;
    }
}

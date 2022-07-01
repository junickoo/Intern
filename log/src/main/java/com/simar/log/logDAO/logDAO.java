package com.simar.log.logDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class logDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HashMap<String, Object> getLog(String objectId) {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("GET_AUDIT_LOG_RAYHAN");

        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_object_id", objectId);
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        HashMap<String,Object> Path = (HashMap<String,Object>) simpleJdbcCall.execute(in);
        //System.out.println(Path);
        return Path;
    }
}

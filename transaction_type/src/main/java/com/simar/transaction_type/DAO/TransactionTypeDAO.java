package com.simar.transaction_type.DAO;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TransactionTypeDAO {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public HashMap<String, Object> getTransactionType(String username) {

        SimpleJdbcCall simpleJdbcCall = new SimpleJdbcCall(jdbcTemplate).withProcedureName("GET_ARC_TRANSACTION_TYPE_RAYHAN");

        Map<String, Object> inParamMap = new HashMap<String, Object>();
        inParamMap.put("in_username", username);
        SqlParameterSource in = new MapSqlParameterSource(inParamMap);

        HashMap<String,Object> Path = (HashMap<String,Object>) simpleJdbcCall.execute(in);
		//System.out.println(Path);
        return Path;
    }
}

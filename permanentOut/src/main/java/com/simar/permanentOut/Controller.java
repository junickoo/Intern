package com.simar.permanentOut;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping(value = "/simar")
public class Controller {
    @Autowired
    DAO permOutDAO;

    @PutMapping(path = "/permOut")
    public HashMap<String, Object> permOut(@RequestBody PermOut permOut){
        HashMap<String, Object> output = permOutDAO.putPermanentOut(permOut.getBoxNum());

        return output;
    }
}

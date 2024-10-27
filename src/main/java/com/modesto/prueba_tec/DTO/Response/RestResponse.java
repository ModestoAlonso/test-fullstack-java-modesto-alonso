package com.modesto.prueba_tec.DTO.Response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class RestResponse {

        public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("mensaje", message);
            map.put("status", status.value());

            if(responseObj != null) {
                map.put("data", responseObj);
            }

            return new ResponseEntity<Object>(map, status);
    }
}

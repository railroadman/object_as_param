package com.example.objects.demo.controller;

import com.example.objects.demo.filter.Filter;
import com.example.objects.demo.service.Fservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
public class IndexController {

    @Autowired
    private Fservice fservice;
    @GetMapping()
    ResponseEntity<String> paramWithObject(Filter filter){
        boolean result = fservice.getSomethingWithFilter(filter);
        if (result){
            return new ResponseEntity<>("ok", HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("fail", HttpStatus.OK);
        }

    }

}

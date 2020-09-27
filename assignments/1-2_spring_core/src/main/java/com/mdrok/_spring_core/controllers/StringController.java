package com.mdrok._spring_core.controllers;

import com.mdrok._spring_core.services.StringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

@Controller
public class StringController {

    private StringService stringService;

    @Autowired
    public StringController(StringService stringService){
        this.stringService = stringService;
    }

    @Profile("regular")
    public String invert(String text){
        return stringService.invert(text);
    }

    @Profile("caps")
    public String capitalise(String text){
        return stringService.capitalise(text);
    }
}

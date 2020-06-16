package com.boot.controller;

import com.boot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TicketController {

    @Autowired
    private UserService userServiceImpl;


    @RequestMapping("/ticket")
    @ResponseBody
    public String ticket(){
        this.userServiceImpl.hello();

        return "OK";
    }

}

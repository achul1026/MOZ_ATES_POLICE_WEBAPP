package com.moz.ates.traffic.policewebapp.search.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/search")
public class SearchController {
    /**
     * @Method Name : searchDriver
     * @Date : 2024. 1. 15.
     * @Author : IK.MOON
     * @Method Brief : 운전자 조회 페이지 이동
     * @param model
     * @return
     */
    @GetMapping("/driver")
    public String searchDriver(Model model) {

        return "views/main/searchDriver";
    }

    /**
     * @Method Name : searchVehicle
     * @Date : 2024. 1. 15.
     * @Author : IK.MOON
     * @Method Brief : 차량 조회 페이지 이동
     * @param model
     * @return
     */
    @GetMapping("/vehicle")
    public String searchVehicle(Model model) {

        return "views/main/searchVehicle";
    }

    
    
    
    @GetMapping("/acdntEnfHistory")
    public String acdntEnfHistory(Model model) {

        return "views/main/acdntEnfHistory";
    }

    @GetMapping("/acdntEnfceDetail")
    public String acdntEnfceDetail(Model model) {

        return "views/main/acdntEnfceDetail";
    }
}

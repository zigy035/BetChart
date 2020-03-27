package com.betchart.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/betchart/api")
public class BetChartController {

    @RequestMapping("/estimations")
    public String getEstimations(@RequestParam("startDate") String startDate) {

        return "";
    }
}

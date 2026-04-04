package com.zorvyn.finance.controller;

import com.zorvyn.finance.service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService service;

    public DashboardController(DashboardService service) {
        this.service = service;
    }

    @GetMapping("/week")
    public ResponseEntity<?> getDashboardDataWeekly() {
        service.getDashboardDataWeekly();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/monthly")
    public ResponseEntity<?> getDashboardDataMonthly() {
        service.getDashboardDataMonthly();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

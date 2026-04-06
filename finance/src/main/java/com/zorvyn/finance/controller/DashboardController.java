package com.zorvyn.finance.controller;

import com.zorvyn.finance.service.DashboardService;
import org.springframework.http.HttpStatus;
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

    @GetMapping("/summary")
    public ResponseEntity<?> getSummary() {
        return new ResponseEntity<>(service.getSummary(), HttpStatus.OK);
    }

    @GetMapping("/week")
    public ResponseEntity<?> getDashboardDataWeekly() {
        return new ResponseEntity<>(service.getDashboardDataWeekly(), HttpStatus.OK);
    }

    @GetMapping("/month")
    public ResponseEntity<?> getDashboardDataMonthly() {
        return new ResponseEntity<>(service.getDashboardDataMonthly(), HttpStatus.OK);
    }
}

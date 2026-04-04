package com.zorvyn.finance.controller;

import com.zorvyn.finance.dto.request.CreateRecordRequest;
import com.zorvyn.finance.dto.request.UpdateRecordRequest;
import com.zorvyn.finance.service.RecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/record")
public class RecordController {

    private final RecordService service;

    public RecordController(RecordService recordService) {
        this.service = recordService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRecord(@RequestBody CreateRecordRequest recordRequest) {
        service.createRecord(recordRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable("id") Long recordId) {
        service.deleteRecord(recordId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateRecord(@RequestBody UpdateRecordRequest updateRequest, @PathVariable("id") Long recordId) {
        service.updateRecord(updateRequest,recordId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecord(@PathVariable("id") Long recordId) {
        service.getRecord(recordId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

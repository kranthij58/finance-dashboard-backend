package com.zorvyn.finance.controller;

import com.zorvyn.finance.dto.request.CreateRecordRequest;
import com.zorvyn.finance.dto.request.UpdateRecordRequest;
import com.zorvyn.finance.exception.ErrorResponse;
import com.zorvyn.finance.service.RecordService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/record")
public class RecordController {

    private final RecordService service;

    public RecordController(RecordService recordService) {
        this.service = recordService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRecord(@Valid @RequestBody CreateRecordRequest recordRequest) {
        return new ResponseEntity<>(service.createRecord(recordRequest), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRecord(@PathVariable("id") Long recordId) {
        service.deleteRecord(recordId);
        return new ResponseEntity<>(new ErrorResponse(200, "Record deleted successfully", LocalDateTime.now()), HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<?> updateRecord(@Valid @RequestBody UpdateRecordRequest updateRequest, @PathVariable("id") Long recordId) {
        return new ResponseEntity<>(service.updateRecord(updateRequest,recordId), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecord(@PathVariable("id") Long recordId) {
            return new ResponseEntity<>( service.getRecord(recordId), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getAllRecords() {
        return new ResponseEntity<>(service.getAllRecords(), HttpStatus.OK);
    }

    @GetMapping("/filter/type")
    public ResponseEntity<?> filterByType(@RequestParam String type) {
        return new ResponseEntity<>(service.filterByType(type), HttpStatus.OK);
    }

    @GetMapping("/filter/category")
    public ResponseEntity<?> filterByCategory(@RequestParam String category) {
        return new ResponseEntity<>(service.filterByCategory(category), HttpStatus.OK);
    }

    @GetMapping("/filter/date")
    public ResponseEntity<?> filterByDate(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return new ResponseEntity<>(service.filterByDate(date), HttpStatus.OK);
    }
}

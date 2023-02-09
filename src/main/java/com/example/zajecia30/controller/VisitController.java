package com.example.zajecia30.controller;

import com.example.zajecia30.command.VisitCommand;
import com.example.zajecia30.domain.Visit;
import com.example.zajecia30.dto.VisitDto;
import com.example.zajecia30.service.VisitService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/visit")
public class VisitController {

    private final VisitService visitService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<VisitDto> addVisit(@Valid @RequestBody VisitCommand command) {
        final Visit visit = modelMapper.map(command, Visit.class);
        return new ResponseEntity<>(modelMapper.map(visitService.add(visit), VisitDto.class), HttpStatus.CREATED);
    }

}

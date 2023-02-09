package com.example.zajecia30.controller;

import com.example.zajecia30.command.DoctorCommand;
import com.example.zajecia30.domain.Doctor;
import com.example.zajecia30.dto.DoctorDto;
import com.example.zajecia30.dto.VisitDto;
import com.example.zajecia30.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@RequestMapping("/doctor")
public class DoctorController {

    private final DoctorService doctorService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<DoctorDto> addDoctor(@Valid @RequestBody DoctorCommand command) {
        final Doctor doctor = modelMapper.map(command, Doctor.class);
        return new ResponseEntity<>(modelMapper.map(doctorService.save(doctor), DoctorDto.class), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<CollectionModel<DoctorDto>> getAll() {
        final List<DoctorDto> doctors = doctorService.findAll().stream().map(doctor -> modelMapper.map(doctor, DoctorDto.class)).collect(Collectors.toList());

        for (DoctorDto d : doctors) {
            d.add(linkTo(DoctorController.class).slash(d.getId()).withSelfRel());
        }

        Link link = linkTo(methodOn(DoctorController.class)
                .getAll()).withSelfRel();
        CollectionModel<DoctorDto> result = CollectionModel.of(doctors, link);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable("id") Long id) {
        DoctorDto doctorDto = modelMapper.map(doctorService.findById(id), DoctorDto.class);
        doctorDto.add(linkTo(DoctorController.class).slash(id).withSelfRel());
        doctorDto.add(linkTo(methodOn(DoctorController.class).getVisitsByDoctorId(id)).withRel("Doctor Visits"));
        return new ResponseEntity<>(doctorDto, HttpStatus.OK);
    }


    @GetMapping("/{id}/visit")
    public ResponseEntity<Set<VisitDto>> getVisitsByDoctorId(@PathVariable("id") Long id) {
        return new ResponseEntity<>(doctorService.findVisitsByDoctorId(id).stream().map(visit -> modelMapper.map(visit, VisitDto.class)).collect(Collectors.toSet()), HttpStatus.OK);
    }
}

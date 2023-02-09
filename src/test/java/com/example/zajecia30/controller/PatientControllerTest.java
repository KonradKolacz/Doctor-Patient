package com.example.zajecia30.controller;

import com.example.zajecia30.command.PatientCommand;
import com.example.zajecia30.domain.Patient;
import com.example.zajecia30.dto.PatientDto;
import com.example.zajecia30.exception.ObjectNotFoundException;
import com.example.zajecia30.repository.PatientRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("integration-tests")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PatientRepository patientRepository;

    @Test
    public void shouldAddPatient() throws Exception {
        PatientCommand patientCommand = new PatientCommand("Weronika", "Nowak", "987654321");

        this.mockMvc.perform(post("/patient")
                .content(objectMapper.writeValueAsString(patientCommand))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.surname").value("Nowak"));
    }

    @Test
    public void shouldGetZeroPatients() throws Exception {
        this.mockMvc.perform(get("/patient"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldThrowExceptionWhenPatientNotExist() throws Exception {

        this.mockMvc.perform(get("/patient/{id}", 12)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof ObjectNotFoundException));
    }

    @Test
    public void shouldGetSinglePatient() throws Exception {
        Patient patient = patientRepository.save(new Patient(2L, "Kasia", "Kozlowska", "1234567", null));

        MvcResult mvcResult = this.mockMvc.perform(get("/patient/" + patient.getId()))
                .andExpect(status().isOk())
                .andReturn();

        PatientDto patientDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PatientDto.class);
        assertThat(patientDto.getId()).isEqualTo(patient.getId());
        assertThat(patientDto.getName()).isEqualTo("Kasia");
    }
}

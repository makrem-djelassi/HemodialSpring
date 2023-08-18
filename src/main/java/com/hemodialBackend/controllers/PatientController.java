package com.hemodialBackend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.hemodialBackend.models.Patient;

import com.hemodialBackend.services.PatientService;
import lombok.RequiredArgsConstructor;
@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient/")

public class PatientController {
	private final PatientService patientService;
	@GetMapping("")
	public ResponseEntity getAll() {
		List<Patient> list = patientService.getAllPatient();
		if (list != null) {
            return new ResponseEntity(list, HttpStatus.OK);
        }else{
            return new ResponseEntity("No patient",HttpStatus.OK);
        }
	}
    @GetMapping("{id}")
    public Optional<Patient> getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    @GetMapping("clinique/{id}")

    public List<Patient> getPatientByCliniqueId(@PathVariable Long id) {
        return patientService.getPatientByClinique(id);
    }
}

package com.hemodialBackend.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.hemodialBackend.models.Role;
import com.hemodialBackend.models.User;
import com.hemodialBackend.models.Clinique;
import com.hemodialBackend.repositories.PatientRepository;
import com.hemodialBackend.services.CliniqueService;
import com.hemodialBackend.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hemodialBackend.models.Patient;

import com.hemodialBackend.services.PatientService;
import lombok.RequiredArgsConstructor;

import javax.swing.text.html.Option;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/patient/")

public class PatientController {
    private final PatientService patientService;
    private final UserService userService;
    private final CliniqueService cliniqueService;
    private final PatientRepository patientRepository;

    @GetMapping("")
    public ResponseEntity getAll(@RequestHeader("Authorization") String RequestHeader) {
        User user = userService.getUserAuthority(RequestHeader);
        List<Patient> list = new ArrayList();
        ;
        if (user.getRole() == Role.ADMIN) {
            list = patientService.getAllPatient();
        } else {
            List<Clinique> clinique = cliniqueService.getCliniqueByGerant(user.getId());
            for (Clinique it : clinique) {
                list = patientService.getPatientByClinique(it.getId());
            }
        }
        if (list != null) {
            return new ResponseEntity(list, HttpStatus.OK);
        } else {
            return new ResponseEntity("No patient", HttpStatus.OK);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity getPatientById(@PathVariable Long id, @RequestHeader("Authorization") String RequestHeader) {
        boolean var = false;
        Patient list = null;
        User user = userService.getUserAuthority(RequestHeader);
        Optional<Patient> result = patientService.getPatientById(id);
        if (result.isPresent()) {
            if (user.getRole() == Role.ADMIN) {
                var = true;
            }
            else {
                list = result.get();
                if (Objects.equals(user.getId(), list.getClinique().getGerant().getId())) {
                    var = true;
                }
            }
        }

        if (result.isPresent() && var) {
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity("No patient with id: " + id, HttpStatus.OK);
        }

    }

    @PutMapping("{id}")
    public ResponseEntity updatePatient(@PathVariable Long id, @RequestBody Patient updatedPatient) {
        Patient existingPatient = patientRepository.findById(id).orElse(null);
        if (existingPatient != null) {
            updatedPatient.setId(id);
            Patient result = patientRepository.save(updatedPatient);
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity("No patient with id: " + existingPatient, HttpStatus.OK);
        }
    }

    @PostMapping("")
    public ResponseEntity AddPatient(@RequestBody Patient newPatient) {
        Patient result = patientService.save(newPatient);
        if (result != null)
            return new ResponseEntity(result, HttpStatus.OK);
        return new ResponseEntity(null, HttpStatus.OK);

    }

}

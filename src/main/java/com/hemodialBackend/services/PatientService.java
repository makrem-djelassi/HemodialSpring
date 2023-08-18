package com.hemodialBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hemodialBackend.models.Clinique;
import com.hemodialBackend.models.Patient;
import com.hemodialBackend.repositories.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {
	public final PatientRepository patientRepository;

	public List<Patient> getAllPatient() {
		return patientRepository.findAll();
	}

	public Optional<Patient> getPatientById(Long id) {
		return patientRepository.findById(id);
	}
	
	public List<Patient> getPatientByClinique(Long cliniqueId){

		return patientRepository.findPatientsByClinique_Id(cliniqueId);
	}

	public Patient save(Patient newPatient) {
		Patient result = patientRepository.save(newPatient);
		if (result != null)
			return result;
		return null;
	}
}

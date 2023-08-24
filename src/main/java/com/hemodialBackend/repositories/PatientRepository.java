package com.hemodialBackend.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hemodialBackend.models.Patient;
@Repository

public interface PatientRepository extends JpaRepository<Patient, Long> {

	public List<Patient> findPatientsByClinique_Id(Long Id);
}

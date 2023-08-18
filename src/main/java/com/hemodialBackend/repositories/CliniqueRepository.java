package com.hemodialBackend.repositories;

import com.hemodialBackend.models.Patient;
import com.hemodialBackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hemodialBackend.models.Clinique;

import java.util.List;

@Repository

public interface CliniqueRepository extends JpaRepository<Clinique,Long> {

    public Clinique  findCliniqueByGerant(User gerant);
}

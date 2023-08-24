package com.hemodialBackend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hemodialBackend.models.Clinique;

import java.util.List;
import java.util.Optional;

@Repository

public interface CliniqueRepository extends JpaRepository<Clinique,Long> {

   public List<Clinique> findCliniqueByGerant_Id(Long id) ;
}

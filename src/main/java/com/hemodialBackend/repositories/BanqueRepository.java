package com.hemodialBackend.repositories;

import com.hemodialBackend.models.Banque;
import com.hemodialBackend.models.Clinique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BanqueRepository extends JpaRepository<Banque, Long> {
    public List<Banque> findBanqueByClinique_Id(Long id) ;

}
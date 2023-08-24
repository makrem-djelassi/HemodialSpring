package com.hemodialBackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hemodialBackend.models.Clinique;
import com.hemodialBackend.repositories.CliniqueRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CliniqueService {
	public final CliniqueRepository cliniqueRepository;
	
    public List<Clinique> getAllClinique(){
        return cliniqueRepository.findAll();
    }


	public List<Clinique> getCliniqueByGerant(Long id){

		return cliniqueRepository.findCliniqueByGerant_Id(id);
	}


	public Clinique save(Clinique newClinique) {
		Clinique result = cliniqueRepository.save(newClinique);
		if (result != null)
			return result;
		return null;
	}

}

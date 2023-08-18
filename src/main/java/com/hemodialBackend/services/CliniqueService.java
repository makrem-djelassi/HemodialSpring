package com.hemodialBackend.services;

import java.util.List;

import com.hemodialBackend.models.Patient;
import com.hemodialBackend.models.User;
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

	public Clinique getCliniqueByGerant(User gerant){

		return cliniqueRepository.findCliniqueByGerant(gerant);
	}

	public Clinique save(Clinique newClinique) {
		Clinique result = cliniqueRepository.save(newClinique);
		if (result != null)
			return result;
		return null;
	}
}

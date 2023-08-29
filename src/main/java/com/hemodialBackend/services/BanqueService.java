package com.hemodialBackend.services;

import com.hemodialBackend.models.Banque;
import com.hemodialBackend.repositories.BanqueRepository;
import com.hemodialBackend.repositories.CliniqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BanqueService {
	public final BanqueRepository banqueRepository;
	public final CliniqueRepository cliniqueRepository;
	
    public List<Banque> getAllBanque(){
        return banqueRepository.findAll();
    }

	public List<Banque> getBanqueByClinique(Long id){
		return banqueRepository.findBanqueByClinique_Id(id);
	}

	public Banque save(Banque newBanque) {
		Banque result = banqueRepository.save(newBanque);
		if (result != null)
			return result;
		return null;
	}

}

package com.hemodialBackend.services;

import com.hemodialBackend.models.Caisse;
import com.hemodialBackend.repositories.CaisseRepository;
import com.hemodialBackend.repositories.CliniqueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CaisseService {
    public final CaisseRepository caisseRepository;

    public List<Caisse> getAllCaisse() {
        return caisseRepository.findAll();
    }

    public Caisse getById(Long id) {
        return caisseRepository.findById(id).orElse(null);
    }

    public Caisse save(Caisse newCaisse) {
        Caisse result = caisseRepository.save(newCaisse);
        if (result != null)
            return result;
        return null;
    }

}

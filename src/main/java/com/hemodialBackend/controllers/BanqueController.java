package com.hemodialBackend.controllers;

import java.util.List;

import com.hemodialBackend.models.*;
import com.hemodialBackend.repositories.BanqueRepository;
import com.hemodialBackend.repositories.CliniqueRepository;
import com.hemodialBackend.repositories.PatientRepository;
import com.hemodialBackend.repositories.UserRepository;
import com.hemodialBackend.config.JwtService;
import com.hemodialBackend.services.CliniqueService;
import com.hemodialBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.hemodialBackend.services.BanqueService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/banque/")


public class BanqueController {

    @Autowired
    private UserService userService;
    private final BanqueService banqueService;
    private final CliniqueService cliniqueService;
    private final BanqueRepository banqueRepository;
    private final PatientRepository patientRepository;

    @GetMapping("")
    public ResponseEntity getAll(@RequestHeader("Authorization") String RequestHeader) {

        User user=userService.getUserAuthority(RequestHeader);
        if (user.getRole() == Role.Admin){
            List<Banque> list;
            list = banqueService.getAllBanque();
            return new ResponseEntity(list, HttpStatus.OK);
        }else{
            List<Clinique> CliniqueList;
            List<Banque> BanqueList = null;

            CliniqueList = cliniqueService.getCliniqueByGerant (user.getId());

            if (CliniqueList != null) {

                for(Clinique cl: CliniqueList) {
                    BanqueList =banqueService.getBanqueByClinique(cl.getId());
                }
                return new ResponseEntity(BanqueList, HttpStatus.OK);
            } else {
                return new ResponseEntity("No Clinique", HttpStatus.OK);
            }
        }
    }

    	@PostMapping("")
	public ResponseEntity AddBanque(@RequestBody Banque newbanque) {
		Banque result = banqueService.save(newbanque);
		if (result != null)
			return new ResponseEntity(result, HttpStatus.OK);
		return new ResponseEntity(null, HttpStatus.OK);

	}

    @PutMapping("{id}")
    public ResponseEntity updateBanque(@PathVariable Long id, @RequestBody Banque updatedBanque) {
        Banque existingBanque = banqueRepository.findById(id).orElse(null);
        if (existingBanque != null) {
            updatedBanque.setId(id);
            Banque result = banqueService.save(updatedBanque);
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity("No patient with id: " + existingBanque, HttpStatus.OK);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteById(@PathVariable("id") long id) {
        Banque existingBanque = banqueRepository.findById(id).orElse(null);
if (existingBanque != null) {
    banqueRepository.deleteById(id);
    return new ResponseEntity("banque efface avec succes",HttpStatus.OK);
}
else {
        return new ResponseEntity("banque n'existe pas",HttpStatus.OK);
        }
    }

}


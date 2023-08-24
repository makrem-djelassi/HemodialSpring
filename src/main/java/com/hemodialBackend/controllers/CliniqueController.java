package com.hemodialBackend.controllers;

import java.util.List;

import com.hemodialBackend.models.Role;
import com.hemodialBackend.models.User;
import com.hemodialBackend.repositories.CliniqueRepository;
import com.hemodialBackend.repositories.UserRepository;
import com.hemodialBackend.config.JwtService;
import com.hemodialBackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.hemodialBackend.models.Clinique;

import com.hemodialBackend.services.CliniqueService;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/clinique/")


public class CliniqueController {

	String zz;
	private final CliniqueService cliniqueService;
	@Autowired
	private UserService userService;
	private final CliniqueRepository cliniqueRepository;

	@GetMapping("")
	public ResponseEntity getAll(@RequestHeader("Authorization") String RequestHeader) {

		User user=userService.getUserAuthority(RequestHeader);
		List<Clinique> list;
		if (user.getRole() == Role.ADMIN){
			list = cliniqueService.getAllClinique();
		}else{
			list = cliniqueService.getCliniqueByGerant(user.getId());
		}

		if (list != null) {
			return new ResponseEntity(list, HttpStatus.OK);
		} else {
			return new ResponseEntity("No clinique", HttpStatus.OK);
		}
	}

	@PostMapping("")
	public ResponseEntity AddClinique(@RequestBody Clinique newClinique) {
		Clinique result = cliniqueService.save(newClinique);
		if (result != null)
			return new ResponseEntity(result, HttpStatus.OK);
		return new ResponseEntity(null, HttpStatus.OK);

	}

	@PutMapping("{id}")
	public ResponseEntity updateClinique(@PathVariable Long id, @RequestBody Clinique updatedClinique) {
		Clinique existingClinique = cliniqueRepository.findById(id).orElse(null);
		if (existingClinique != null) {
			/*
			existingClinique.setCodeClinique(updatedClinique.getCodeClinique());
			existingClinique.setAdresse(updatedClinique.getAdresse());
			existingClinique.setEtat(updatedClinique.getEtat());
			existingClinique.setEmail(updatedClinique.getEmail());
			existingClinique.setAffiliationCaisse(updatedClinique.getAffiliationCaisse());
			existingClinique.setBureauCaisse(updatedClinique.getBureauCaisse());
			existingClinique.setCodePrestation(updatedClinique.getCodePrestation());
			existingClinique.setLabel(updatedClinique.getLabel());
			existingClinique.setFax(updatedClinique.getFax());
			existingClinique.setMatriculeTva(updatedClinique.getMatriculeTva());
			existingClinique.setMedcein(updatedClinique.getMedcein());
			existingClinique.setRegistreCommerce(updatedClinique.getRegistreCommerce());
			existingClinique.setTel(updatedClinique.getTel());


			Clinique result= cliniqueRepository.save(existingClinique);

			 */
			updatedClinique.setId(id);

			Clinique result= cliniqueRepository.save(updatedClinique);

			return new ResponseEntity(result, HttpStatus.OK);

		} else {
			return new ResponseEntity(null, HttpStatus.OK);
		}
	}


}


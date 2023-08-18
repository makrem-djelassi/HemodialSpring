package com.hemodialBackend.controllers;

import java.util.List;

import com.hemodialBackend.models.User;
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
	private final CliniqueService cliniqueService;

	@GetMapping("")
	public ResponseEntity getAll() {
		List<Clinique> list = cliniqueService.getAllClinique();
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

	@GetMapping("{gerant}")
	public Clinique findCliniqueByGerant(@PathVariable User gerant) {
		return cliniqueService.getCliniqueByGerant(gerant);
	}
}


package com.hemodialBackend.controllers;

import com.hemodialBackend.models.Caisse;
import com.hemodialBackend.services.CaisseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/caisse/")


public class CaisseController {

    @Autowired
    private final CaisseService caisseService;

    @GetMapping("")
    public ResponseEntity getAll() {
         List<Caisse> list = caisseService.getAllCaisse();
        if (list != null) {
            return new ResponseEntity(list, HttpStatus.OK);
         } else {
                return new ResponseEntity("No caisse", HttpStatus.OK);
            }
    }

    	@PostMapping("")
	public ResponseEntity AddCaisse(@RequestBody Caisse newCaisse) {
		Caisse result = caisseService.save(newCaisse);
		if (result != null)
			return new ResponseEntity(result, HttpStatus.OK);
		return new ResponseEntity(null, HttpStatus.OK);

	}

    @PutMapping("{id}")
    public ResponseEntity updateCaisse(@PathVariable Long id, @RequestBody Caisse updatedCaisse) {
        Caisse existingCaisse = caisseService.getById(id);
        if (existingCaisse != null) {
            updatedCaisse.setId(id);
            Caisse result = caisseService.save(updatedCaisse);
            return new ResponseEntity(result, HttpStatus.OK);
        } else {
            return new ResponseEntity("No caisse with id: " + id, HttpStatus.OK);
        }
    }
}


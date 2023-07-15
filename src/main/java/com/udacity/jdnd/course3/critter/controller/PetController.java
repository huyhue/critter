package com.udacity.jdnd.course3.critter.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.PetDTO;
import com.udacity.jdnd.course3.critter.service.PetService;

@RestController
@RequestMapping("/pet")
public class PetController {

    @Autowired
    PetService petService;
    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO dto) {
        Pet p = new Pet();
        p.setType(dto.getType());
        p.setName(dto.getName());
        PetDTO obj = null;
        try {
        	obj = Convert.convertPet(petService.save(p, dto.getCusId(), dto.getScheId()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test error save", e);
        }
        return obj;
    }
    
    @GetMapping("/{id}")
    public PetDTO getPet(@PathVariable long id) {
        Pet p = null;
        try {
            p = petService.findById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test not exist", e);
        }
        return Convert.convertPet(p);
    }
    
    @GetMapping
    public List<PetDTO> listPet() {
        List<Pet> list = petService.findAll();
        return list.stream().map(Convert::convertPet).collect(Collectors.toList());
    }
    
    @GetMapping("/customer/{id}")
    public List<PetDTO> getPetOfCustomer(@PathVariable long id) {
        List<Pet> list = null;
        try {
        	list = petService.findForCustomer(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test not exist", e);
        }
        return list.stream().map(Convert::convertPet).collect(Collectors.toList());
    }
}

package com.udacity.jdnd.course3.critter.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

@Service
@Transactional
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private CustomerRepository customerRepository;
    
    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet findById(long petId) {
        return petRepository.getOne(petId);
    }

    public List<Pet> findForCustomer(long customerId) {
        return petRepository.findByCustomerId(customerId);
    }

    public Pet save(Pet pet, long cusId, long scheId) {
        Customer customer = customerRepository.getOne(cusId);
        pet.setCustomer(customer);
        Schedule sch = scheduleRepository.getOne(scheId);
        pet.setSchedule(sch);
        pet = petRepository.save(pet);
        List<Pet> pets = new ArrayList<>();
        pets.add(pet);
        customer.setPets(pets);
        customerRepository.save(customer);
        return pet;
    }
}

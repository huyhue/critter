package com.udacity.jdnd.course3.critter.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;

@Service
@Transactional
public class ScheduleService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public List<Schedule> findByPetId(long id) {
        Pet p = petRepository.getOne(id);
        return scheduleRepository.findByPets(p);
    }

    public List<Schedule> findByEmployeeId(long id) {
        Employee e = employeeRepository.getOne(id);
        return scheduleRepository.findByEmployees(e);
    }

    public List<Schedule> findByCustomerId(long id) {
        Customer c = customerRepository.getOne(id);
        return scheduleRepository.findByPetsIn(c.getPets());
    }

    public Schedule save(Schedule sche, List<Long> employeeIds, List<Long> petIds) {
        List<Employee> elist = employeeRepository.findAllById(employeeIds);
        List<Pet> plist = petRepository.findAllById(petIds);
        sche.setEmployees(elist);
        sche.setPets(plist);
        return scheduleRepository.save(sche);
    }
}

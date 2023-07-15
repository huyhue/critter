package com.udacity.jdnd.course3.critter.controller;

import java.util.List;
import java.util.stream.Collectors;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeDTO;
import com.udacity.jdnd.course3.critter.entity.Pet;
import com.udacity.jdnd.course3.critter.entity.PetDTO;
import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.entity.ScheduleDTO;

public class Convert {
    public static CustomerDTO convertCustomer(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setNotes(customer.getNotes());
        List<Long> petIds = customer.getPets().stream().map(Pet::getId).collect(Collectors.toList());
        customerDTO.setPetIds(petIds);
        return customerDTO;
    }

    public static EmployeeDTO toEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getId());
        employeeDTO.setName(employee.getName());
        employeeDTO.setSkills(employee.getSkills());
        employeeDTO.setDays(employee.getDays());
        return employeeDTO;
    }

    public static ScheduleDTO convertSchedule(Schedule sche) {
        ScheduleDTO dto = new ScheduleDTO();
        dto.setId(sche.getId());
        dto.setEmployeeIds(sche.getEmployees().stream().map(Employee::getId).collect(Collectors.toList()));
        dto.setPetIds(sche.getPets().stream().map(Pet::getId).collect(Collectors.toList()));
        dto.setDate(sche.getDate());
        dto.setActivities(sche.getActivities());
        return dto;
    }

    public static PetDTO convertPet(Pet p) {
        PetDTO dto = new PetDTO();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setType(p.getType());
        dto.setCusId(p.getCustomer().getId());
        return dto;
    }
}

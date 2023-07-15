package com.udacity.jdnd.course3.critter.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.udacity.jdnd.course3.critter.entity.Customer;
import com.udacity.jdnd.course3.critter.entity.CustomerDTO;
import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeDTO;
import com.udacity.jdnd.course3.critter.service.CustomerService;
import com.udacity.jdnd.course3.critter.service.EmployeeService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/customer")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO dto) {
        Customer c = new Customer();
        c.setName(dto.getName());
        c.setPhone(dto.getPhone());
        c.setNotes(dto.getNotes());
        List<Long> petIds = dto.getPetIds();
        CustomerDTO obj = null;
        try {
        	obj = Convert.convertCustomer(customerService.save(c, petIds));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test not save", e);
        }
        return obj;
    }

    @GetMapping("/customer")
    public List<CustomerDTO> listCustomer() {
        List<Customer> list = customerService.findAll();
        return list.stream().map(Convert::convertCustomer).collect(Collectors.toList());
    }

    @GetMapping("/customer/pet/{id}")
    public CustomerDTO getOwnerByPet(@PathVariable long id) {
        Customer c = null;
        try {
            c = customerService.findByPetId(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test not exist", e);
        }
        return Convert.convertCustomer(c);
    }

    @PostMapping("/employee")
    public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO dto) {
        Employee e = new Employee();
        e.setName(dto.getName());
        e.setSkills(dto.getSkills());
        e.setDays(dto.getDays());
        EmployeeDTO obj = null;
        try {
        	obj = Convert.convertEmployee(employeeService.save(e));
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test not save", ex);
        }
        return obj;
    }

    @GetMapping("/employee/{id}")
    public EmployeeDTO getEmployee(@PathVariable long id) {
        Employee e = null;
        try {
            e = employeeService.findById(id);
        } catch (Exception ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test not exist", ex);
        }
        return Convert.convertEmployee(e);
    }

    @PutMapping("/employee/{id}")
    public void setDaysEmployee(@RequestBody EmployeeDTO dto, @PathVariable long id) {
        try {
            employeeService.setDaysEmployee(dto.getDays(), id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "error not exist", e);
        }
    }

    @GetMapping("/employee/availability")
    public List<EmployeeDTO> findEmployeesForService(@RequestBody EmployeeDTO dto) {
        List<Employee> employees = employeeService.findByService(dto.getDays(), dto.getSkills());
        return employees.stream().map(Convert::convertEmployee).collect(Collectors.toList());
    }
}

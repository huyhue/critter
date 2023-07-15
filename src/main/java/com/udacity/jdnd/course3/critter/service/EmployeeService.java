package com.udacity.jdnd.course3.critter.service;

import java.time.DayOfWeek;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jdnd.course3.critter.entity.Employee;
import com.udacity.jdnd.course3.critter.entity.EmployeeSkill;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee findById(long employeeId) {
        return employeeRepository.getOne(employeeId);
    }

    public List<Employee> findByService(DayOfWeek days, EmployeeSkill skills) {
        List<Employee> list = employeeRepository
                .findByDays(days).stream()
                .filter(e -> e.getSkills().equals(skills))
                .collect(Collectors.toList());
        return list;
    }

    public void setDaysEmployee(DayOfWeek days, long id) {
        Employee e = employeeRepository.getOne(id);
        e.setDays(days);
        employeeRepository.save(e);
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}

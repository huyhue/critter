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

import com.udacity.jdnd.course3.critter.entity.Schedule;
import com.udacity.jdnd.course3.critter.entity.ScheduleDTO;
import com.udacity.jdnd.course3.critter.service.ScheduleService;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping
    public ScheduleDTO newSchedule(@RequestBody ScheduleDTO dto) {
        Schedule sch = new Schedule();
        sch.setDate(dto.getDate());
        sch.setActivities(dto.getActivities());
        ScheduleDTO obj = null;
        try {
        	obj = Convert.convertSchedule(scheduleService.save(sch, dto.getEmployeeIds(), dto.getPetIds()));
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test not save", e);
        }
        return obj;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {
        List<Schedule> list = scheduleService.findAll();
        return list.stream().map(Convert::convertSchedule).collect(Collectors.toList());
    }

    @GetMapping("/pet/{id}")
    public List<ScheduleDTO> scheduleOfPet(@PathVariable long id) {
        List<Schedule> list = null;
        try {
        	list = scheduleService.findByPetId(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test not exist", e);
        }
        return list.stream().map(Convert::convertSchedule).collect(Collectors.toList());
    }

    @GetMapping("/employee/{id}")
    public List<ScheduleDTO> scheduleOfEmployee(@PathVariable long id) {
        List<Schedule> list = null;
        try {
        	list = scheduleService.findByEmployeeId(id);
        } catch (Exception e) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test not exist", e);
        }
        return list.stream().map(Convert::convertSchedule).collect(Collectors.toList());
    }

    @GetMapping("/customer/{id}")
    public List<ScheduleDTO> scheduleOfCustomer(@PathVariable long id) {
        List<Schedule> list = null;
        try {
        	list = scheduleService.findByCustomerId(id);
        } catch (Exception e) {
        	throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "test not exist", e);
        }
        return list.stream().map(Convert::convertSchedule).collect(Collectors.toList());
    }
}

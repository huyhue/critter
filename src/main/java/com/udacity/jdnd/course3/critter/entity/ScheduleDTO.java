package com.udacity.jdnd.course3.critter.entity;

import java.time.LocalDate;
import java.util.List;

public class ScheduleDTO {
	private long id;
	private List<Long> employeeIds;
	private List<Long> petIds;
	private LocalDate date;
	private EmployeeSkill activities;

	public ScheduleDTO() {
	}

	public ScheduleDTO(List<Long> employeeIds, List<Long> petIds, LocalDate date, EmployeeSkill activities) {
		super();
		this.employeeIds = employeeIds;
		this.petIds = petIds;
		this.date = date;
		this.activities = activities;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Long> getEmployeeIds() {
		return employeeIds;
	}

	public void setEmployeeIds(List<Long> employeeIds) {
		this.employeeIds = employeeIds;
	}

	public List<Long> getPetIds() {
		return petIds;
	}

	public void setPetIds(List<Long> petIds) {
		this.petIds = petIds;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public EmployeeSkill getActivities() {
		return activities;
	}

	public void setActivities(EmployeeSkill activities) {
		this.activities = activities;
	}

}

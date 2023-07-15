package com.udacity.jdnd.course3.critter.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table
@Entity
public class Schedule implements Serializable {
	@Id
	@GeneratedValue
	private long id;

	@ManyToMany(mappedBy = "scheduleEmployee")
	private List<Employee> employees;
	
	@OneToMany(mappedBy="schedule")
    private List<Pet> pets;

	private LocalDate date;

	@Enumerated(EnumType.STRING)
	private EmployeeSkill activities;

	public Schedule() {
	}

	public Schedule(long id, List<Employee> employees, List<Pet> pets, LocalDate date, EmployeeSkill activities) {
		super();
		this.id = id;
		this.employees = employees;
		this.pets = pets;
		this.date = date;
		this.activities = activities;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<Pet> getPets() {
		return pets;
	}



	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}



	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
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

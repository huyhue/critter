package com.udacity.jdnd.course3.critter.entity;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Table
@Entity
public class Employee implements Serializable {
	@Id
	@GeneratedValue
	private long id;

	private String name;

	@Enumerated(EnumType.STRING)
	private EmployeeSkill skills;

	@Enumerated(EnumType.STRING)
	private DayOfWeek days;

	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "employee_schedule", joinColumns = @JoinColumn(name = "employee_id"), inverseJoinColumns = @JoinColumn(name = "schedule_id"))
	private List<Schedule> scheduleEmployee;

	public Employee() {
	}

	public Employee(long id, String name, EmployeeSkill skills, DayOfWeek days,
			List<Schedule> scheduleEmployee) {
		super();
		this.id = id;
		this.name = name;
		this.skills = skills;
		this.days = days;
		this.scheduleEmployee = scheduleEmployee;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public EmployeeSkill getSkills() {
		return skills;
	}

	public void setSkills(EmployeeSkill skills) {
		this.skills = skills;
	}

	public DayOfWeek getDays() {
		return days;
	}

	public void setDays(DayOfWeek days) {
		this.days = days;
	}

	public List<Schedule> getScheduleEmployee() {
		return scheduleEmployee;
	}

	public void setScheduleEmployee(List<Schedule> scheduleEmployee) {
		this.scheduleEmployee = scheduleEmployee;
	}

}

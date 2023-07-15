package com.udacity.jdnd.course3.critter.entity;

import java.time.DayOfWeek;

public class EmployeeDTO {
    private long id;
    private String name;
    private EmployeeSkill skills;
    private DayOfWeek days;

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

    
}

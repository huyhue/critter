package com.udacity.jdnd.course3.critter.entity;

public class PetDTO {
    private long id;
    private PetType type;
    private String name;
    private long cusId;
    private long scheId;

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScheId() {
		return scheId;
	}

	public void setScheId(long scheId) {
		this.scheId = scheId;
	}

	public long getCusId() {
		return cusId;
	}

	public void setCusId(long cusId) {
		this.cusId = cusId;
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

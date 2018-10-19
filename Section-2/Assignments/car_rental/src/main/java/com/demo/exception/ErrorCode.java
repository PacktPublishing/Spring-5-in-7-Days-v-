package com.demo.exception;

public enum ErrorCode {

	ALREADYINUSE(011, "Car is already in use."),
	INSUFFICIENTCASH(012, "Cash in your account is less than car's rent."),
	ONEUSERPERCAR(013, "You Already have a car mapped.");

	private final int id;
	private final String description;

	private ErrorCode(int id, String description) {
		this.id = id;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return id + ": " + description;
	}
}

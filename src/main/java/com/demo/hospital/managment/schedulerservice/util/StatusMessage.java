package com.demo.hospital.managment.schedulerservice.util;

import lombok.Getter;

@Getter
public enum StatusMessage {
	APPOINTMENT_IS_BOOKED("Appointment is save"),
	SERVER_ERROR("Error while processing request"),
	APPOINTMENT_IS_NOT_AVAILABLE("Appointment is not available"),
	APPOINTMENT_IS_DELETED("Appointment deleted successfully"),
	APPOINTMENT_IS_UPDATED("Appointment updated successfully");

	private String message;

	private StatusMessage(String message) {
		this.message = message;
	}
}

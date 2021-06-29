package com.demo.hospital.managment.notesservice.util;

import lombok.Getter;

@Getter
public enum StatusMessage {
	SERVER_ERROR("Error while processing request"),
	APPOINTMENT_IS_NOT_AVAILABLE("Appointment is not available");
	private String message;

	private StatusMessage(String message) {
		this.message = message;
	}
}

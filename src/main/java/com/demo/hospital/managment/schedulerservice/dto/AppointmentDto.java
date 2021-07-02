package com.demo.hospital.managment.schedulerservice.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentDto {
	private Long appointmentId;
	private String meetingTitle;
	private String description;
	private Long physicianId;
	private Long patientId;
	private LocalDateTime dateAndTime;
	private Long edit_id;
}

package com.demo.hospital.managment.schedulerservice.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class AvailableSlotDto {

	private Long id;
	
	private LocalDate appointmentDate;
	
	private LocalTime appointmentStartTime;
	
	private LocalTime appointmentEndTime;
	
	private String role;
}

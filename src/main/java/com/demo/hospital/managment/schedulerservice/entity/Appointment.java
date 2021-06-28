package com.demo.hospital.managment.schedulerservice.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "appointment")
public class Appointment {
	@Id
	@Column(name = "appointment_id")
	private Long appointmentId;
	@Column(name = "meeting_title")
	private String meetingTitle;
	@Column(name = "description")
	private String description;
	@Column(name = "physician_id")
	private Long physicianId;
	@Column(name = "patient_id")
	private Long patientId;
	@Column(name = "appointment_date")
	private LocalDate appointmentDate;
	@Column(name = "appointment_start_time")
	private LocalTime appointmentStartTime;
	@Column(name = "appointment_end_time")
	private LocalTime appointmentEndTime;
	@Column(name = "edit_id")
	private Long edit_id;
}

package com.demo.hospital.managment.schedulerservice.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "appointment")
@ApiModel(description = "Details about the appointment")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "appointment_id")
	private Long appointmentId;
	@Column(name = "meeting_title")
	@ApiModelProperty(notes = "Appointment Title")
	private String meetingTitle;
	@Column(name = "description")
	private String description;
	@Column(name = "physician_id")
	private Long physicianId;
	@Column(name = "patient_id")
	private Long patientId;
	@Column(name = "appointment_date")
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate appointmentDate;
	@Column(name = "appointment_start_time")
	private LocalTime appointmentStartTime;
	@Column(name = "appointment_end_time")
	private LocalTime appointmentEndTime;
	@Column(name = "edit_id")
	private Long edit_id;
}

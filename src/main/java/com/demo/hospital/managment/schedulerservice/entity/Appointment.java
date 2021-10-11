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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "appointment")
@ApiModel(description = "Details About The Appointment")
public class Appointment extends Auditable<String> {
	@Id
	@Column(name = "appointment_id")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long appointmentId;
	@Column(name = "meeting_title")
	@ApiModelProperty(notes = "Appointment Title")
	private String meetingTitle;
	@Column(name = "description")
	@ApiModelProperty(notes = "Appointment Description")
	private String description;
	@Column(name = "physician_id")
	@ApiModelProperty(notes = "Physician Id")
	private Long physicianId;
	@Column(name = "patient_id")
	@ApiModelProperty(notes = "Patient Id")
	private Long patientId;
	@Column(name = "appointment_date")
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@ApiModelProperty(notes = "Appointment Date")
	private LocalDate appointmentDate;
	@Column(name = "appointment_start_time")
	@ApiModelProperty(notes = "Appointment Start Time")
	private LocalTime appointmentStartTime;
	@Column(name = "appointment_end_time")
	@ApiModelProperty(notes = "Appointment End Time")
	private LocalTime appointmentEndTime;
	@Column(name = "edit_id")
	private Long edit_id;
}

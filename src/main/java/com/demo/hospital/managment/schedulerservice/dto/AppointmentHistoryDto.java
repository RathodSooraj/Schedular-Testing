package com.demo.hospital.managment.schedulerservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AppointmentHistoryDto implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	private Long appointmentId;

	private String meetingTitle;

	private String description;

	private Long physicianId;

	private Long patientId;

	private LocalDate appointmentDate;

	private String firstName;

	private String lastName;

	private String patientFirstName;

	private String patientLastName;

}

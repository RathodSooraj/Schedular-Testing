package com.demo.hospital.managment.schedulerservice.serviceinterface;

import java.time.LocalDate;
import java.util.List;

import com.demo.hospital.managment.schedulerservice.entity.Appointment;

public interface AppointmentServiceInteface {
	public List<Appointment> getAppointmentToPhysician(Long physicianId,LocalDate startDate,LocalDate endDate);
	public List<Appointment> getAppointmentToPatient(Long patientId,LocalDate startDate,LocalDate endDate);
}

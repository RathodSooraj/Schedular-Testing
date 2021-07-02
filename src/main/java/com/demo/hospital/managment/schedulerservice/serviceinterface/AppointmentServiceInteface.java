package com.demo.hospital.managment.schedulerservice.serviceinterface;

import java.time.LocalDate;
import java.util.List;

import com.demo.hospital.managment.schedulerservice.entity.Appointment;

public interface AppointmentServiceInteface {

	public Long saveAppointment(Appointment appointment);

	public void deleteAppointment(Long appointmentId);

	public List<Appointment> getAppointmentToPhysician(Long physicianId, LocalDate startDate, LocalDate endDate);

	public List<Appointment> getAppointmentToPatient(Long patientId, LocalDate startDate, LocalDate endDate);

	public Appointment findAppointmentById(Long appointmentId);

	public Long updateAppointment(Appointment appointment);

}

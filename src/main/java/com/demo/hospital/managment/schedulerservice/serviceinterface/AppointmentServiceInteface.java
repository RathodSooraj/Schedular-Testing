package com.demo.hospital.managment.schedulerservice.serviceinterface;

import java.util.List;

import com.demo.hospital.managment.schedulerservice.entity.Appointment;

public interface AppointmentServiceInteface {

	public Long saveAppointment(Appointment appointment);

	public void deleteAppointment(Long appointmentId);

	public Appointment findAppointmentById(Long id);

	public List<Appointment> getAppointmentToPhysician(Long physicianId);

	public List<Appointment> getAppointmentToPatient(Long patientId);


}

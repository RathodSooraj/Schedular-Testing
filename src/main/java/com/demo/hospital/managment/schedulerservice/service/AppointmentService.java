package com.demo.hospital.managment.schedulerservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.hospital.managment.schedulerservice.entity.Appointment;
import com.demo.hospital.managment.schedulerservice.repository.AppointmentRepository;
import com.demo.hospital.managment.schedulerservice.serviceinterface.AppointmentServiceInteface;

@Service
public class AppointmentService implements AppointmentServiceInteface {
	@Autowired
	AppointmentRepository appointmentRepository;

	@Override
	public List<Appointment> getAppointmentToPhysician(Long physicianId) {
		return appointmentRepository.getAppointmentToPhysician(physicianId);
	}

	@Override
	public List<Appointment> getAppointmentToPatient(Long patientId) {

		return appointmentRepository.getAppointmentToPatient(patientId);
	}

	@Override
	public Long saveAppointment(Appointment appointment) {
		Appointment appt = appointmentRepository.save(appointment);
		return appt.getAppointmentId();
	}

	@Override
	public void deleteAppointment(Long appointmentId) {
		Appointment appointment = findAppointmentById(appointmentId);
		appointmentRepository.delete(appointment);
	}

	@Override
	public Appointment findAppointmentById(Long id) {
		return appointmentRepository.findById(id).get();
	}

}

package com.demo.hospital.managment.schedulerservice.serviceImpl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.hospital.managment.schedulerservice.entity.Appointment;
import com.demo.hospital.managment.schedulerservice.entity.User;
import com.demo.hospital.managment.schedulerservice.repository.AppointmentRepository;
import com.demo.hospital.managment.schedulerservice.serviceinterface.AppointmentServiceInteface;
import com.demo.hospital.managment.schedulerservice.util.EmailUtil;

@Service
public class AppointmentService implements AppointmentServiceInteface {
	@Autowired
	AppointmentRepository appointmentRepository;


	@Override
	public List<Appointment> getAppointmentToPhysician(Long physicianId, LocalDate startDate, LocalDate endDate) {
		return appointmentRepository.getAppointmentToPhysician(physicianId, startDate, endDate);
	}

	@Override
	public List<Appointment> getAppointmentToPatient(Long patientId, LocalDate startDate, LocalDate endDate) {

		return appointmentRepository.getAppointmentToPatient(patientId, startDate, endDate);
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
	public Appointment findAppointmentById(Long appointmentId) {
		Appointment appointment = appointmentRepository.findById(appointmentId).get();
		return appointment;
	}

	@Override
	public Long updateAppointment(Appointment appointment) {
		return saveAppointment(appointment);
	}

	

}

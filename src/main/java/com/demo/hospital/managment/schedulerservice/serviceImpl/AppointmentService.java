package com.demo.hospital.managment.schedulerservice.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.hospital.managment.schedulerservice.dto.AppointmentDto;
import com.demo.hospital.managment.schedulerservice.entity.Appointment;
import com.demo.hospital.managment.schedulerservice.repository.AppointmentRepository;
import com.demo.hospital.managment.schedulerservice.serviceinterface.AppointmentServiceInteface;

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
		return appointmentRepository.findById(appointmentId).get();
	}

	@Override
	public Long updateAppointment(Appointment appointment) {
		return saveAppointment(appointment);
	}

	@Override
	public AppointmentDto getAppointmentById(Long id) {
		
		Appointment apt = appointmentRepository.findById(id).get();
		AppointmentDto dto = new AppointmentDto();
		dto.setAppointmentId(apt.getAppointmentId());
		dto.setAppointmentDate(apt.getAppointmentDate());
		dto.setAppointmentEndTime(apt.getAppointmentEndTime());
		dto.setAppointmentStartTime(apt.getAppointmentStartTime());
		dto.setDescription(apt.getDescription());
		dto.setMeetingTitle(apt.getMeetingTitle());
		dto.setPatientId(apt.getPatientId());
		dto.setPhysicianId(apt.getPhysicianId());
		dto.setEdit_id(apt.getEdit_id());
		return dto;
	}


}

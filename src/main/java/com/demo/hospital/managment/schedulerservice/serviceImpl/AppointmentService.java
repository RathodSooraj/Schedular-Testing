package com.demo.hospital.managment.schedulerservice.serviceImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.demo.hospital.managment.schedulerservice.dto.AppointmentDto;
import com.demo.hospital.managment.schedulerservice.dto.AvailableSlotDto;

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

	@Override
	public boolean isSlotAvailable(AvailableSlotDto availableSlot) {
		List<Appointment> listApt = new ArrayList<>();
		if (availableSlot.getRole().equalsIgnoreCase("Physician")) {
			listApt = getAppointmentToPatient(availableSlot.getId(),availableSlot.getAppointmentDate(),availableSlot.getAppointmentDate());
		} else if (availableSlot.getRole( ).equalsIgnoreCase("Patient")) {
			listApt = getAppointmentToPhysician(availableSlot.getId(),availableSlot.getAppointmentDate(),availableSlot.getAppointmentDate());
		}
		Optional<Appointment> optional =listApt.stream().filter(apt -> apt.getAppointmentStartTime().equals(availableSlot.getAppointmentStartTime())).findFirst();
		return !optional.isPresent();
	}

}

package com.demo.hospital.managment.schedulerservice.serviceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.demo.hospital.managment.schedulerservice.controller.AppointmentController;
import com.demo.hospital.managment.schedulerservice.dto.AppointmentDto;
import com.demo.hospital.managment.schedulerservice.dto.AppointmentHistoryDto;
import com.demo.hospital.managment.schedulerservice.dto.AvailableSlotDto;
import com.demo.hospital.managment.schedulerservice.entity.Appointment;
import com.demo.hospital.managment.schedulerservice.entity.User;
import com.demo.hospital.managment.schedulerservice.repository.AppointmentRepository;
import com.demo.hospital.managment.schedulerservice.serviceinterface.AppointmentServiceInteface;

@Service
public class AppointmentService implements AppointmentServiceInteface {
	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	RestTemplate restTemplate;

	private Logger log = LoggerFactory.getLogger(AppointmentService.class);

	@Override
	public List<Appointment> getAppointmentToPhysician(Long physicianId, LocalDate startDate, LocalDate endDate) {
		log.info("Inside getAppointmentToPhysician Service");
		return appointmentRepository.getAppointmentToPhysician(physicianId, startDate, endDate);
	}

	@Override
	public List<Appointment> getAppointmentToPatient(Long patientId, LocalDate startDate, LocalDate endDate) {
		log.info("Inside getAppointmentToPatient Service");

		return appointmentRepository.getAppointmentToPatient(patientId, startDate, endDate);
	}

	@Override
	public Long saveAppointment(Appointment appointment) {
		log.info("Inside saveAppointment Service");

		Appointment appt = appointmentRepository.save(appointment);

		return appt.getAppointmentId();
	}

	@Override
	public void deleteAppointment(Long appointmentId) {
		log.info("Inside deleteAppointment Service");

		Appointment appointment = findAppointmentById(appointmentId);
		appointmentRepository.delete(appointment);
	}

	@Override
	public Appointment findAppointmentById(Long appointmentId) {
		log.info("Inside findAppointmentById Service");
		Appointment appointment = appointmentRepository.findById(appointmentId).get();
		return appointment;
	}

	@Override
	public Long updateAppointment(Appointment appointment) {
		log.info("Inside updateAppointment Service");
		return saveAppointment(appointment);
	}

	@Override
	public AppointmentDto getAppointmentById(Long id) {
		log.info("Inside getAppointmentById Service");

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
		log.info("Inside isSlotAvailable Service");
		List<Appointment> listApt = new ArrayList<>();
		if (availableSlot.getRole().equalsIgnoreCase("Physician")) {
			listApt = getAppointmentToPatient(availableSlot.getId(), availableSlot.getAppointmentDate(),
					availableSlot.getAppointmentDate());
		} else if (availableSlot.getRole().equalsIgnoreCase("Patient")) {
			listApt = getAppointmentToPhysician(availableSlot.getId(), availableSlot.getAppointmentDate(),
					availableSlot.getAppointmentDate());
		}
		Optional<Appointment> optional = listApt.stream()
				.filter(apt -> apt.getAppointmentStartTime().equals(availableSlot.getAppointmentStartTime()))
				.findFirst();
		return !optional.isPresent();
	}

	@Override
	public List<Appointment> getAllAppointment() {
		log.info("Inside getAllAppointment Service");
		return appointmentRepository.findAll();
	}

	@Override
	public List<Appointment> getAllAppointmentByPatientId(Long Id) {
		log.info("Inside getAllAppointmentByPatientId Service");
		return appointmentRepository.findByPatientId(Id);
	}

	@Override
	public List<Appointment> getAllAppointmentByPhysicianId(Long Id) {
		log.info("Inside getAllAppointmentByPhysicianId Service");
		return appointmentRepository.findByPhysicianId(Id);
	}

	@Override
	public List<AppointmentHistoryDto> getAllAppointmentHistory() {

		log.info("Inside getAllAppointmentHistory Service");
		List<AppointmentHistoryDto> apptHistoryList = new ArrayList<AppointmentHistoryDto>();

		List<Appointment> list = getAllAppointment();

		list.forEach(id -> {
			AppointmentHistoryDto historyDto = new AppointmentHistoryDto();

			String URI_USERS = "http://localhost:8081/user/getAllUser";
			User[] userArray = restTemplate.getForObject(URI_USERS, User[].class);
			List<User> users = Arrays.asList(userArray);
			users.forEach(user -> {
				historyDto.setAppointmentId(id.getAppointmentId());
				historyDto.setMeetingTitle(id.getMeetingTitle());
				historyDto.setDescription(id.getDescription());
				historyDto.setAppointmentDate(id.getAppointmentDate());
				historyDto.setPatientId(id.getPatientId());
				historyDto.setPhysicianId(id.getPhysicianId());
				if (id.getPatientId() == user.getUserId()) {
					historyDto.setPatientFirstName(user.getFirstName());
					historyDto.setPatientLastName(user.getLastName());
				} else if (id.getPhysicianId() == user.getUserId()) {
					historyDto.setFirstName(user.getFirstName());
					historyDto.setLastName(user.getLastName());
				}
			});
			apptHistoryList.add(historyDto);

		});

		return apptHistoryList;
	}

}

package com.demo.hospital.managment.schedulerservice.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.hospital.managment.schedulerservice.entity.Appointment;
import com.demo.hospital.managment.schedulerservice.entity.User;
import com.demo.hospital.managment.schedulerservice.serviceinterface.AppointmentServiceInteface;
import com.demo.hospital.managment.schedulerservice.util.AppointmentUtil;
import com.demo.hospital.managment.schedulerservice.util.EmailUtil;
import com.demo.hospital.managment.schedulerservice.util.MessageResponseDto;
import com.demo.hospital.managment.schedulerservice.util.StatusMessage;

/**
 * @author suraj
 *
 */
@RestController
@RequestMapping("appointment")
@CrossOrigin(value = "http://localhost:4200")
public class AppointmentController {

	private Logger log = LoggerFactory.getLogger(AppointmentController.class);

	@Autowired
	AppointmentServiceInteface appointmentService;

	@Autowired
	private EmailUtil emailUtil;

	@Autowired
	RestTemplate restTemplate;

	@Value("${to.email}")
	private String emailAddress;

	/**
	 * Below function is used to save an appointment
	 * 
	 * @param appointment
	 * @return Confirmation Message
	 */
	@PostMapping("/save")
	public ResponseEntity<MessageResponseDto> saveAppointment(@RequestBody Appointment appointment) {
		ResponseEntity<MessageResponseDto> resp = null;
		try {

			Long id = appointmentService.saveAppointment(appointment);
			if (id > 0 && id != null) {
				log.info(emailAddress);

				new Thread(() -> {
					String subject = "New Appointment Of " + appointment.getMeetingTitle();
					String text = "This is the confirmation for your schedule appointment at"
							+ appointment.getAppointmentStartTime() + " On " + appointment.getAppointmentDate();
					emailUtil.sendEmail(emailAddress, subject, text);
				}).start();

			}
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.APPOINTMENT_IS_BOOKED.getMessage()),
					HttpStatus.CREATED);
		} catch (

		Exception e) {
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.SERVER_ERROR.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * This API is use to get all appointment of physician
	 * 
	 * @author AnaghaJ2
	 * @param
	 * @return
	 */

	@GetMapping(path = "/getAppointmentToPhysician")
	public ResponseEntity<List<Appointment>> getAppointmentToPhysician(@RequestParam Long physicianId,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		try {
			List<Appointment> listAppointment = appointmentService.getAppointmentToPhysician(physicianId, startDate,
					endDate);
			return new ResponseEntity<>(listAppointment, HttpStatus.OK);
		} catch (Exception e) {
			e.getMessage();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * This API is use to get all appointment of patient
	 * 
	 * @author AnaghaJ2
	 * @param
	 * @return
	 */

	@GetMapping(path = "/getAppointmentToPatient")
	public ResponseEntity<List<Appointment>> getAppointmentToPatient(@RequestParam Long patientId,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

		try {
			List<Appointment> listAppointment = appointmentService.getAppointmentToPatient(patientId, startDate,
					endDate);
			return new ResponseEntity<>(listAppointment, HttpStatus.OK);
		} catch (Exception e) {
			e.getMessage();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @param appointmentId
	 * @return Confirmation Message
	 */
	@DeleteMapping("/delete/{appointmentId}")
	public ResponseEntity<MessageResponseDto> removeAppointmentByID(@PathVariable Long appointmentId) {
		ResponseEntity<MessageResponseDto> resp = null;
		try {
			appointmentService.deleteAppointment(appointmentId);
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.APPOINTMENT_IS_DELETED.getMessage()),
					HttpStatus.OK);
		} catch (Exception e) {
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.SERVER_ERROR.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);

			e.printStackTrace();
		}
		return resp;
	}

	/**
	 * @param appointmentId
	 * @param appointment
	 * @return
	 */
	@PutMapping("/modify/{appointmentId}")
	public ResponseEntity<MessageResponseDto> updateAppointment(@PathVariable Long appointmentId,
			@RequestBody Appointment appointment) {
		ResponseEntity<MessageResponseDto> resp = null;
		try {
			Appointment dbappointment = appointmentService.findAppointmentById(appointmentId);
			AppointmentUtil.copyNonNullValues(dbappointment, appointment);
			appointmentService.updateAppointment(dbappointment);
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.APPOINTMENT_IS_UPDATED.getMessage()),
					HttpStatus.OK);
		} catch (Exception e) {
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.SERVER_ERROR.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return resp;
	}

}

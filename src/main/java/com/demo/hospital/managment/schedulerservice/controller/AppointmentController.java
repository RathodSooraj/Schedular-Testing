package com.demo.hospital.managment.schedulerservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.hospital.managment.notesservice.util.MessageResponseDto;
import com.demo.hospital.managment.notesservice.util.StatusMessage;
import com.demo.hospital.managment.schedulerservice.entity.Appointment;
import com.demo.hospital.managment.schedulerservice.serviceinterface.AppointmentServiceInteface;

/**
 * @author suraj
 *
 */
/**
 * @author suraj
 *
 */
@RestController
@RequestMapping("/appointment")
@CrossOrigin(value = "http://localhost:4200")
public class AppointmentController {

	@Autowired
	AppointmentServiceInteface appointmentServiceInteface;

	/**
	 * Below api is used to book an appointment
	 * 
	 * @param appointment
	 * @return String (Confirmation Message)
	 */
	@PostMapping("/save")
	public ResponseEntity<MessageResponseDto> saveAppointment(@RequestBody Appointment appointment) {
		ResponseEntity<MessageResponseDto> resp = null;
		try {
			Long id = appointmentServiceInteface.saveAppointment(appointment);
			resp = new ResponseEntity<MessageResponseDto>(
					new MessageResponseDto(StatusMessage.APPOINTMENT_IS_BOOKED.getMessage()), HttpStatus.CREATED);
		} catch (Exception e) {
			resp = new ResponseEntity<MessageResponseDto>(
					new MessageResponseDto(StatusMessage.SERVER_ERROR.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
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
	public ResponseEntity<List<Appointment>> getAppointmentToPhysician(@RequestParam Long physicianId) {
		try {
			List<Appointment> listAppointment = appointmentServiceInteface.getAppointmentToPhysician(physicianId);
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
	public ResponseEntity<List<Appointment>> getAppointmentToPatient(@RequestParam Long patientId) {

		try {
			List<Appointment> listAppointment = appointmentServiceInteface.getAppointmentToPatient(patientId);
			return new ResponseEntity<>(listAppointment, HttpStatus.OK);
		} catch (Exception e) {
			e.getMessage();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}

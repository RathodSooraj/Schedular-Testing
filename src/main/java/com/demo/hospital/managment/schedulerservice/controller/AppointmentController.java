package com.demo.hospital.managment.schedulerservice.controller;

import java.time.LocalDate;
import java.util.List;

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

import com.demo.hospital.managment.schedulerservice.dto.AppointmentDto;
import com.demo.hospital.managment.schedulerservice.dto.AppointmentHistoryDto;
import com.demo.hospital.managment.schedulerservice.dto.AvailableSlotDto;
import com.demo.hospital.managment.schedulerservice.entity.Appointment;
import com.demo.hospital.managment.schedulerservice.serviceinterface.AppointmentServiceInteface;
import com.demo.hospital.managment.schedulerservice.util.AppointmentUtil;
import com.demo.hospital.managment.schedulerservice.util.EmailUtil;
import com.demo.hospital.managment.schedulerservice.util.MessageResponseDto;
import com.demo.hospital.managment.schedulerservice.util.StatusMessage;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @author suraj
 *
 */
@RestController
@RequestMapping("/appointment")
//@CrossOrigin(value = "http://localhost:4200")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AppointmentController {

	private Logger log = LoggerFactory.getLogger(AppointmentController.class);

	@Autowired
	AppointmentServiceInteface appointmentService;

	@Autowired
	private EmailUtil emailUtil;

	@Value("${to.email}")
	private String emailAddress;

	/**
	 * Below function is used to save an appointment
	 * 
	 * @param appointment
	 * @return Confirmation Message
	 */
	@PostMapping("/save")
	@ApiOperation(value = "Save Appointment", notes = "Provide Appointment Details And Store It In Database")

	public ResponseEntity<MessageResponseDto> saveAppointment(
			@ApiParam(value = "Appoinetment Object To Store Data", required = true) @RequestBody Appointment appointment) {
		ResponseEntity<MessageResponseDto> resp = null;
		try {
			log.info("Saving Appointment");
			Long id = appointmentService.saveAppointment(appointment);
			if (id > 0 && id != null) {
				log.info(emailAddress);

				new Thread(() -> {
					String subject = "New Appointment Of " + appointment.getMeetingTitle();
					String text = "This is the confirmation for your schedule appointment at"
							+ appointment.getAppointmentStartTime() + " On " + appointment.getAppointmentDate()
							+ " Till " + appointment.getAppointmentEndTime();
					log.info("Sending Email");

					emailUtil.sendEmail(emailAddress, subject, text);
				}).start();

			}
			log.info("Sending response after booking appointment");
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.APPOINTMENT_IS_BOOKED.getMessage()),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception happen" + e.getMessage());
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.SERVER_ERROR.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
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
	@ApiOperation(value = "Fetch Physician Appointment", notes = "Provide Date In Range (From-To)")

	public ResponseEntity<List<Appointment>> getAppointmentToPhysician(
			@ApiParam(value = "Physician Id/startDate/endDate", required = true) @RequestParam Long physicianId,
			@ApiParam(value = "Start Date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@ApiParam(value = "End Date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {
		try {
			log.info("Getting all appointment of physician from {Startdate} - {Enddate}");
			List<Appointment> listAppointment = appointmentService.getAppointmentToPhysician(physicianId, startDate,
					endDate);
			log.info("Sending response getAppointmentToPhysician ");
			return new ResponseEntity<>(listAppointment, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception happen" + e.getMessage());
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
	@ApiOperation(value = "Fetch Patient Appointment", notes = "Provide Date In Range (From-To)")
	public ResponseEntity<List<Appointment>> getAppointmentToPatient(
			@ApiParam(value = "Patient Id", required = true) @RequestParam Long patientId,
			@ApiParam(value = "Start Date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
			@ApiParam(value = "End Date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate) {

		try {
			log.info("Getting all appointment of patient from {Startdate} - {Enddate}");

			List<Appointment> listAppointment = appointmentService.getAppointmentToPatient(patientId, startDate,
					endDate);
			log.info("Sending response getAppointmentToPatient ");
			return new ResponseEntity<>(listAppointment, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception happen" + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * This API is use to get all appointment of physician
	 * 
	 * @author AnaghaJ2
	 * @param
	 * @return
	 */

	@GetMapping(path = "/getAppointmentById")
	@ApiOperation(value = "Fetch An Existing Appointment", notes = "Provide Date In Range (From-To {Date})")
	public ResponseEntity<AppointmentDto> getAppointmentById(

			@ApiParam(value = "Appointment Id", required = true) @RequestParam("id") Long id) {
		try {

			log.info("Get AppointmentById");
			AppointmentDto appointmentDto = appointmentService.getAppointmentById(id);
			log.info("Sending response getAppointmentById ");
			return new ResponseEntity<>(appointmentDto, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception happen" + e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	/**
	 * @param appointmentId
	 * @return Confirmation Message
	 */
	@DeleteMapping("/delete/{appointmentId}")
	@ApiOperation(value = "Cancel Appointment", notes = "Provide Date In Range (From-To {Date})")
	public ResponseEntity<MessageResponseDto> removeAppointmentById(
			@ApiParam(value = "Appointment Id", required = true) @PathVariable Long appointmentId) {
		ResponseEntity<MessageResponseDto> resp = null;
		try {

			log.info("Remove appointment Id");
			appointmentService.deleteAppointment(appointmentId);
			log.info("Sending response deleteById ");
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.APPOINTMENT_IS_DELETED.getMessage()),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception happen" + e.getMessage());
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.SERVER_ERROR.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}
		return resp;
	}

	/**
	 * @param appointmentId
	 * @param appointment
	 * @return
	 */
	@PutMapping("/modify/{appointmentId}")
	@ApiOperation(value = "Update Existing Appointment", notes = "Provide Appointment Details And Update It In Database")
	public ResponseEntity<MessageResponseDto> updateAppointment(
			@ApiParam(value = "Appoinetment Object With Updated And Existing Data", required = true) @PathVariable Long appointmentId,
			@RequestBody Appointment appointment) {
		ResponseEntity<MessageResponseDto> resp = null;
		try {

			log.info("Inside Update Appointment");
			Appointment dbappointment = appointmentService.findAppointmentById(appointmentId);
			AppointmentUtil.copyNonNullValues(dbappointment, appointment);
			appointmentService.updateAppointment(dbappointment);
			log.info("Sending response Update Appointment ");
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.APPOINTMENT_IS_UPDATED.getMessage()),
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception happen" + e.getMessage());
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.SERVER_ERROR.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
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

	@PostMapping(path = "/isSlotAvailable")
	public ResponseEntity<Boolean> isSlotAvailable(@RequestBody AvailableSlotDto availableSlot) {
		try {

			log.info("Inside isSlotAvailableController");
			boolean isAppointment = appointmentService.isSlotAvailable(availableSlot);
			log.info("Sending response slot available ");
			return new ResponseEntity<>(isAppointment, HttpStatus.OK);
		} catch (Exception e) {
			log.error("Exception happen" + e.getMessage());
			return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping(path = "/getAll")
	public ResponseEntity<?> getAllAppointment() {
		ResponseEntity<?> resp = null;
		try {
			log.info("Inside getAllAppointment Controller");

			List<Appointment> list = appointmentService.getAllAppointment();

			log.info("Sending response getAppointments");
			resp = new ResponseEntity<>(list, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Exception happen" + e.getMessage());
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.SERVER_ERROR.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	@GetMapping(path = "/getAllPatient/{patientId}")
	public ResponseEntity<?> getAllAppointmentOfPatient(@PathVariable Long patientId) {
		ResponseEntity<?> resp = null;
		try {

			log.info("Inside get all patient appointment controller");

			List<Appointment> body = appointmentService.getAllAppointmentByPatientId(patientId);
			log.info("Sending response get appointment of patient ");
			resp = new ResponseEntity<List<Appointment>>(body, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Exception happen" + e.getMessage());
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.SERVER_ERROR.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	@GetMapping(path = "/getAllPhysician/{physicianId}")
	public ResponseEntity<?> getAllAppointmentOfPhysician(@PathVariable Long physicianId) {
		ResponseEntity<?> resp = null;
		try {

			log.info("Inside get all physician appointment controller");
			List<Appointment> body = appointmentService.getAllAppointmentByPhysicianId(physicianId);
			log.info("Sending response get appointment of physician");
			resp = new ResponseEntity<List<Appointment>>(body, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Exception happen" + e.getMessage());
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.SERVER_ERROR.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

	@GetMapping(path = "/getAllHistory")
	public ResponseEntity<?> getAllAppointmentHistory() {
		ResponseEntity<?> resp = null;
		try {
			log.info("Inside get all appointment history");

			List<AppointmentHistoryDto> list = appointmentService.getAllAppointmentHistory();
			log.info("Sending response get all appointment history");
			resp = new ResponseEntity<List<AppointmentHistoryDto>>(list, HttpStatus.OK);

		} catch (Exception e) {
			log.error("Exception happen" + e.getMessage());
			resp = new ResponseEntity<>(new MessageResponseDto(StatusMessage.SERVER_ERROR.getMessage()),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return resp;
	}

}

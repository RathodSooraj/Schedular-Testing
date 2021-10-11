package com.demo.hospital.managment.schedulerservice.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.hospital.managment.schedulerservice.dto.AppointmentDto;
import com.demo.hospital.managment.schedulerservice.entity.Appointment;
import com.demo.hospital.managment.schedulerservice.serviceinterface.AppointmentServiceInteface;
import com.demo.hospital.managment.schedulerservice.util.AppointmentUtil;
import com.demo.hospital.managment.schedulerservice.util.EmailUtil;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerTest {

	@MockBean
	private AppointmentServiceInteface appointmentService;

	@MockBean
	private EmailUtil emailUtil;

	@MockBean
	private AppointmentUtil util;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testSaveAppointment() throws Exception {
		/*
		 * ObjectMapper om = new ObjectMapper(); String jsonString =
		 * om.writeValueAsString(newAppointment());
		 * when(appointmentService.saveAppointment(any())).thenReturn(55L);
		 */
		this.mockMvc.perform(post("/appointment/save").contentType(MediaType.APPLICATION_JSON).content(

				"{\r\n    \"appointmentId\":11,\r\n    \"meetingTitle\":\"Meeting title 11\",\r\n    \"description\":\"Patient name : Anagha, Age :30 , Gender:Female, Knee pain\",\r\n    \"physicianId\":42,\r\n    \"patientId\":74,\r\n    \"appointmentDate\":\"2021-07-02\",\r\n    \"appointmentStartTime\":\"10:00:00\",\r\n    \"appointmentEndTime\":\"11:00:00\",\r\n    \"edit_id\":5\r\n}\r\n"))
				.andDo(print()).andExpect(status().isOk());
	}

	@Test
	void testGetAppointmentById() throws Exception {
		when(appointmentService.getAppointmentById(any())).thenReturn(newAppointment());
		/*
		 * this.mockMvc.perform(get("/appointment/getAppointmentById").param("id",
		 * "1111L")).andDo(print()) .andExpect(status().isOk());
		 */
		mockMvc.perform(get("/appointment/getAppointmentById").param("id", "2")).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void testRemoveAppointmentById() throws Exception {
		doNothing().when(appointmentService).deleteAppointment(newAppointment().getAppointmentId());
		this.mockMvc.perform(delete("/appointment/delete/" + newAppointment().getAppointmentId())).andDo(print())
				.andExpect(status().isOk());
	}

	@Test
	void testGetAllAppointmentHistory() throws Exception {
		List list = new ArrayList<>();
		list.add(newAppointment());
		list.add(newAppointment());
		list.add(newAppointment());

		when(appointmentService.getAllAppointmentHistory()).thenReturn(list);
		mockMvc.perform(get("/appointment/getAllHistory")).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(3)));
	}

	@Test
	void testGetAllAppointmentOfPhysician() throws Exception {
		List list = new ArrayList<>();
		list.add(newAppointment());
		list.add(newAppointment());
		list.add(newAppointment());

		when(appointmentService.getAllAppointmentByPhysicianId(any())).thenReturn(list);
		mockMvc.perform(get("/appointment/getAllPhysician/" + newAppointment().getPhysicianId())).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(3)))
				.andExpect(jsonPath("$.[1].appointmentId").value(1111L));

	}

	@Test
	void testGetAllAppointmentOfPatient() throws Exception {

		List list = new ArrayList<>();
		list.add(newAppointment());
		list.add(newAppointment());

		when(appointmentService.getAllAppointmentByPatientId(any())).thenReturn(list);

		mockMvc.perform(get("/appointment/getAllPatient/" + newAppointment().getPatientId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(2)));

	}

	@Test
	void testGetAllAppointment() throws Exception {

		List list = new ArrayList<>();
		list.add(updatedAppointment());

		when(appointmentService.getAllAppointment()).thenReturn(list);
		mockMvc.perform(get("/appointment/getAll")).andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(1)));
	}

	@Test
	void testUpdateAppointment() throws Exception {

		when(appointmentService.findAppointmentById(any())).thenReturn(legacyAppointment());
//		when(util.copyNonNullValues(any(), any())).thenReturn(latestAppointment());

		when(appointmentService.updateAppointment(any())).thenReturn(latestAppointment().getAppointmentId());
		mockMvc.perform(put("/appointment/modify/{appointmentId}", legacyAppointment().getAppointmentId())
				.contentType(MediaType.APPLICATION_JSON)
				.contentType("{\r\n    \"appointmentId\":11,\r\n   " + " \"meetingTitle\":\"Meeting title 11\",\r\n   "
						+ " \"description\":\"Patient name : Anagha, Age :26 ," + " Gender:Female, Knee pain\",\r\n  "
						+ "  \"physicianId\":42,\r\n    \"patientId\":74,\r\n   "
						+ " \"appointmentDate\":\"2021-07-02\",\r\n    \"appointmentStartTime\":\"10:00:00\",\r\n  "
						+ "  \"appointmentEndTime\":\"11:00:00\",\r\n  " + "  \"edit_id\":5\r\n}\r\n"))
				.andExpect(jsonPath("$.meetingTitle").value("Meeting title 11"));

	}

	public Appointment legacyAppointment() {

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(1111L);
		appointment.setMeetingTitle("Meeting Title 11");
		appointment.setDescription("Appt to Keen issue");
		appointment.setAppointmentDate(LocalDate.of(2010, 07, 02));
		appointment.setAppointmentStartTime(LocalTime.of(11, 00));
		appointment.setAppointmentEndTime(LocalTime.of(12, 00));
		appointment.setPatientId(23L);
		appointment.setPhysicianId(41L);
		appointment.setEdit_id(45L);
		return appointment;
	}

	public Appointment latestAppointment() {

		Appointment appointment = new Appointment();
		appointment.setAppointmentId(222l);
		appointment.setMeetingTitle("Meeting Title 11");
		appointment.setDescription("Appt to Keen issue");
		appointment.setAppointmentDate(LocalDate.of(2010, 07, 02));
		appointment.setAppointmentStartTime(LocalTime.of(11, 00));
		appointment.setAppointmentEndTime(LocalTime.of(12, 00));
		appointment.setPatientId(23L);
		appointment.setPhysicianId(41L);
		appointment.setEdit_id(45L);
		return appointment;
	}

	public AppointmentDto newAppointment() {

		AppointmentDto appointment = new AppointmentDto();
		appointment.setAppointmentId(1111L);
		appointment.setMeetingTitle("Meeting Title 11");
		appointment.setDescription("Appt to Keen issue");
		appointment.setAppointmentDate(LocalDate.of(2010, 07, 02));
		appointment.setAppointmentStartTime(LocalTime.of(11, 00));
		appointment.setAppointmentEndTime(LocalTime.of(12, 00));
		appointment.setPatientId(23L);
		appointment.setPhysicianId(41L);
		appointment.setEdit_id(45L);
		return appointment;
	}

	public AppointmentDto updatedAppointment() {
		AppointmentDto appointment = new AppointmentDto();
		appointment.setAppointmentId(1111L);
		appointment.setMeetingTitle("Meeting Title 655");
		appointment.setDescription("Soar Throat");
		appointment.setAppointmentDate(LocalDate.of(2010, 07, 02));
		appointment.setAppointmentStartTime(LocalTime.of(11, 00));
		appointment.setAppointmentEndTime(LocalTime.of(12, 00));
		appointment.setPatientId(23L);
		appointment.setPhysicianId(41L);
		appointment.setEdit_id(45L);

		return appointment;
	}

}

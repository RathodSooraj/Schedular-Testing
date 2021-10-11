package com.demo.hospital.managment.schedulerservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.demo.hospital.managment.schedulerservice.entity.Appointment;
import com.demo.hospital.managment.schedulerservice.repository.AppointmentRepository;

@SpringBootTest
class AppointmentServiceTest {

	@MockBean
	private AppointmentRepository repository;

	@Test
	void testGetAppointmentToPhysician() {
		List<Appointment> list = new ArrayList<Appointment>();
		list.add(legacyAppointment());
		list.add(legacyAppointment());
		list.add(legacyAppointment());
		when(repository.getAppointmentToPhysician(legacyAppointment().getPhysicianId(),
				legacyAppointment().getAppointmentDate(), legacyAppointment().getAppointmentDate())).thenReturn(list);
		assertNotNull(list);
	}

	@Test
	void testGetAppointmentToPatient() {

		List<Appointment> list = new ArrayList<Appointment>();
		list.add(legacyAppointment());
		list.add(legacyAppointment());
		list.add(legacyAppointment());
		when(repository.getAppointmentToPatient(any(), any(), any())).thenReturn(list);
		assertNotNull(list);
	}

	@Test
	void testSaveAppointment() {
		when(repository.save(any())).thenReturn(legacyAppointment().getAppointmentId());
		assertEquals(1111L, legacyAppointment().getAppointmentId());
	}

	@Test
	void testDeleteAppointment() {
		doNothing().when(repository).delete(legacyAppointment());

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
}

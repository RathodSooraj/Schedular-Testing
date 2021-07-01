package com.demo.hospital.managment.schedulerservice.util;

import com.demo.hospital.managment.schedulerservice.entity.Appointment;

public class AppointmentUtil {

	public static void copyNonNullValues(Appointment dbAppointment, Appointment appointment) {
		if (appointment.getMeetingTitle() != null)
			dbAppointment.setMeetingTitle(appointment.getMeetingTitle());
		if (appointment.getAppointmentDate() != null)
			dbAppointment.setAppointmentDate(appointment.getAppointmentDate());
		if (appointment.getDescription() != null)
			dbAppointment.setDescription(appointment.getDescription());
		if (appointment.getAppointmentStartTime() != null)
			dbAppointment.setAppointmentStartTime(appointment.getAppointmentStartTime());
		if (appointment.getAppointmentEndTime() != null)
			dbAppointment.setAppointmentEndTime(appointment.getAppointmentEndTime());
		if (appointment.getPatientId() != null)
			dbAppointment.setPatientId(appointment.getPatientId());
		if (appointment.getPhysicianId() != null)
			dbAppointment.setPhysicianId(appointment.getPhysicianId());
	}

}

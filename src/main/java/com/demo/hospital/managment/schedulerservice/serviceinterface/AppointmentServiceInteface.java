package com.demo.hospital.managment.schedulerservice.serviceinterface;

import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;

import com.demo.hospital.managment.schedulerservice.entity.Appointment;

public interface AppointmentServiceInteface {
	public List<Appointment> getAppointmentToPhysician(Long physicianId);
	public List<Appointment> getAppointmentToPatient(@RequestParam Long patientId);
}

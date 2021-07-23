package com.demo.hospital.managment.schedulerservice.serviceinterface;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.demo.hospital.managment.schedulerservice.dto.AppointmentDto;
import com.demo.hospital.managment.schedulerservice.dto.AvailableSlotDto;
import com.demo.hospital.managment.schedulerservice.entity.Appointment;
import com.demo.hospital.managment.schedulerservice.entity.User;

public interface AppointmentServiceInteface {

	public Long saveAppointment(Appointment appointment);

	public void deleteAppointment(Long appointmentId);

	public List<Appointment> getAppointmentToPhysician(Long physicianId, LocalDate startDate, LocalDate endDate);

	public List<Appointment> getAppointmentToPatient(Long patientId, LocalDate startDate, LocalDate endDate);

	public Appointment findAppointmentById(Long appointmentId);

	public Long updateAppointment(Appointment appointment);

	public AppointmentDto getAppointmentById(Long id);

	public boolean isSlotAvailable(AvailableSlotDto availableSlot);

	public List<Appointment> getAllAppointment();

	public List<Appointment> getAllAppointmentByPatientId(Long Id);

	public List<Appointment> getAllAppointmentByPhysicianId(Long Id);

}

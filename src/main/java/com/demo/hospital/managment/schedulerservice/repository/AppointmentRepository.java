package com.demo.hospital.managment.schedulerservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.hospital.managment.schedulerservice.entity.Appointment;
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long>{
	
	@Query("select a from Appointment a where a.physicianId = ?1")
	List<Appointment> getAppointmentToPhysician(Long physicianId);
	
	@Query("select a from Appointment a where a.patientId = ?1")
	List<Appointment> getAppointmentToPatient(Long patientId);
	
	/*@Query("select a from Appointment a where a.physicianId = ?1 And a.patientId = ?2")
	List<Appointment> getAppointmentToNurse(Long physicianId,Long patientId);*/
}
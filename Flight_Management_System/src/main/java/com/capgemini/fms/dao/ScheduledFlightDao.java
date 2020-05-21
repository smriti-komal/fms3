package com.capgemini.fms.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.capgemini.fms.entity.ScheduledFlight;

@Repository
@CrossOrigin(origins = "http://localhost:4200")
public interface ScheduledFlightDao extends JpaRepository<ScheduledFlight , Integer>{

}
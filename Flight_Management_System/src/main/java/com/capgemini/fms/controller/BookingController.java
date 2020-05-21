package com.capgemini.fms.controller;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.fms.entity.Airport;
import com.capgemini.fms.entity.Booking;
import com.capgemini.fms.entity.Flight;
import com.capgemini.fms.exception.BookingException;
import com.capgemini.fms.service.BookingService;

@CrossOrigin(origins="http://localhost:4200")
@RestController
public class BookingController {
	@Autowired
	private BookingService bookingservice;

	@CrossOrigin 
	@PostMapping("/addbooking")
	public ResponseEntity<String> addBooking(@Valid @RequestBody Booking booking, BindingResult br)
			throws BookingException {
		String err = "";
		if (br.hasErrors()) {
			List<FieldError> errors = br.getFieldErrors();
			for (FieldError error : errors)
				err += error.getDefaultMessage() + "<br/>";
			throw new BookingException(err);
		}
		try {
			bookingservice.addbooking(booking);
			return new ResponseEntity<String>("booking done successfully", HttpStatus.OK);

		} catch (DataIntegrityViolationException ex) {
			throw new BookingException("booking already exists");
		}
	}

	
	@GetMapping("/viewallbooking")
	@CrossOrigin(origins="http://localhost:4200")
	public ResponseEntity<List<Booking>> getBookinglist() {
		List<Booking> bookingList = bookingservice.show();
		
		return new ResponseEntity<List<Booking>>(bookingList, HttpStatus.OK);
	}
	
	@CrossOrigin
	@DeleteMapping("/deletebooking/{bookingId}")
	public ResponseEntity<String> deletebooking( @PathVariable long bookingId) throws BookingException
	{
		try
		{
			bookingservice.deletebooking( bookingId);
			return new ResponseEntity<String>("BookingId is deleted", HttpStatus.OK);
		}
		catch (DataIntegrityViolationException ex) {
			throw new BookingException("this bookingId does not exist");
		}
	}
	
	@CrossOrigin
	@PutMapping("/updatebooking")
	public ResponseEntity<String> updatebooking(@Valid @RequestBody Booking booking, BindingResult br ) throws BookingException
	{
		String err = "";
		if (br.hasErrors()) {
			List<FieldError> errors = br.getFieldErrors();
			for (FieldError error : errors)
				err += error.getDefaultMessage() + "<br/>";
			throw new BookingException(err);
		}
		try {
			bookingservice.updatebooking(booking);
			return new ResponseEntity<String>("Flight updated successfully", HttpStatus.OK);

		} catch (DataIntegrityViolationException ex) {
			throw new BookingException("bookingId already exist");
		}
	}
	@CrossOrigin
	@GetMapping("/getbookingdetails")
	public ResponseEntity<List<Booking>> bookingdetails(@Valid @RequestParam long bookingId){
		List<Booking> bookingList = bookingservice.show();
		return new ResponseEntity<List<Booking>>(bookingList,HttpStatus.OK);
		}
		
	}
		
	
	
	



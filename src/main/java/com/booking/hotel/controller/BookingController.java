package com.booking.hotel.controller;

import com.booking.hotel.entity.BookingEntity;
import com.booking.hotel.payload.request.BookingRequest;
import com.booking.hotel.payload.response.BaseResponse;
import com.booking.hotel.service.imp.BookingServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("bookings")
@CrossOrigin
public class BookingController {
    @Autowired
    private BookingServiceImp bookingServiceImp;
    @GetMapping("allbookings")
    public ResponseEntity<?> getAllBookings(){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(bookingServiceImp.getAllBookings());
        return new  ResponseEntity<>(baseResponse, HttpStatus.OK);
    }
    @PostMapping("room/{roomId}/booking")
    public ResponseEntity<?> saveBooking(@PathVariable int roomId, BookingRequest bookingRequest){
        try {
            String confirmationCode = bookingServiceImp.saveBooking(roomId,bookingRequest);
            return new ResponseEntity<>("Room booked successfully, Your booking confirmation code is: " + confirmationCode, HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException("Loi khong the dat phong: " + e.getMessage());
        }
    }
    @GetMapping("confirmation/{confirmationCode}")
    public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode){
        try{
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setData(bookingServiceImp.findByConfirmationCode(confirmationCode));
            return ResponseEntity.ok(baseResponse);
        }catch (Exception ex){
            System.out.println("Kiem tra catch");
            throw new RuntimeException("Error get booking by Confirmation Code:  "+ ex.getMessage());
        }
    }
    @GetMapping("/user/{email}/booking")
    public ResponseEntity<?> getBookingsByUserEmail(@PathVariable String email){
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(bookingServiceImp.getBookingsByUserEmail(email));
        return new ResponseEntity<>(baseResponse,HttpStatus.OK);
    }

    @GetMapping("/{bookingId}/delete")
    public void cancelBooking(@PathVariable int bookingId){
        bookingServiceImp.cancelBooking(bookingId);
    }
}

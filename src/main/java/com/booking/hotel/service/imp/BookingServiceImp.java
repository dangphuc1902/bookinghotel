package com.booking.hotel.service.imp;

import com.booking.hotel.dto.BookingDto;
import com.booking.hotel.entity.BookingEntity;
import com.booking.hotel.payload.request.BookingRequest;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

public interface BookingServiceImp {
//    boolean addBooking(LocalDate checkIn , LocalDate checkOut, int adults, int children, int userId, int roomId);
    List<BookingDto> getAllBookings();
    List<BookingDto> getBookingsByUserEmail(String email);
    BookingDto findByConfirmationCode(String confirmationCode);
    String saveBooking(int roomId, BookingRequest bookingRequest);
    List<BookingEntity> getAllBookingsByRoomId(int roomId);
    void cancelBooking(int bookingId);
}

package com.booking.hotel.service;

import com.booking.hotel.dto.BookingDto;
import com.booking.hotel.dto.RoomDTO;
import com.booking.hotel.entity.BookingEntity;
import com.booking.hotel.entity.RoomEntity;
import com.booking.hotel.entity.UsersEntity;
import com.booking.hotel.payload.request.BookingRequest;
import com.booking.hotel.payload.request.RoomRequest;
import com.booking.hotel.payload.response.BaseResponse;
import com.booking.hotel.repository.BookingRepository;
import com.booking.hotel.service.imp.BookingServiceImp;
import com.booking.hotel.service.imp.RoomServiceImp;
import com.booking.hotel.service.imp.UserServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class  BookingService implements BookingServiceImp {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private UserServiceImp userServiceImp;
    @Autowired
    private RoomService roomService;

    @Override
    public List<BookingDto> getAllBookings() {
        List<BookingEntity> bookingEntities = bookingRepository.findAll();
        List<BookingDto> bookingDtos = getBookingResponse(bookingEntities);
        return bookingDtos;
    }

    @Override
    public List<BookingDto> getBookingsByUserEmail(String email) {
        List<BookingEntity> bookingEntities = bookingRepository.findByUserEmail(email);
        List<BookingDto> bookingDtos = getBookingResponse(bookingEntities);
        return bookingDtos;
    }

    private List<BookingDto> getBookingResponse(List<BookingEntity> bookingEntities){
        List<BookingDto> bookingDtos = new ArrayList<>();
        bookingEntities.forEach(item ->{
            BookingDto dto = new BookingDto();
            dto.setId(item.getId());
            dto.setCheckInRoom(item.getCheckIn());
            dto.setCheckOutRoom(item.getCheckOut());
            dto.setFullNameGuest(item.getFullName());
            dto.setUserEmail(item.getUserEmail());
            dto.setGuestAdults(item.getAdults());
            dto.setGuestChildren(item.getChildren());
            dto.setBookingTotalGuest(item.getTotalGuest());
            dto.setBookingConfirmationCode(item.getConfirmationCode());
            bookingDtos.add(dto);
        });
        return bookingDtos;
    }


    @Override
    public BookingDto findByConfirmationCode(String confirmationCode) {
        BookingEntity bookingEntity = bookingRepository.findByConfirmationCode(confirmationCode).orElseThrow(()
                -> new RuntimeException("No booking found with booking code :" + confirmationCode));
        BookingDto dto = new BookingDto();
        dto.setId(bookingEntity.getId());
        dto.setCheckInRoom(bookingEntity.getCheckIn());
        dto.setCheckOutRoom(bookingEntity.getCheckOut());
        dto.setGuestAdults(bookingEntity.getAdults());
        dto.setFullNameGuest(bookingEntity.getFullName());
        dto.setUserEmail(bookingEntity.getUserEmail());
        dto.setGuestChildren(bookingEntity.getChildren());
        dto.setBookingTotalGuest(bookingEntity.getTotalGuest());
        dto.setBookingConfirmationCode(bookingEntity.getConfirmationCode());
        return dto;
    }
    @Override
    public String saveBooking(int roomId, BookingRequest bookingRequest) {
        if (bookingRequest.getCheckOut().isBefore(bookingRequest.getCheckIn())){
            throw new RuntimeException("Check-in date must come before check-out date");
        }
        RoomEntity roomEntity = roomService.roomById(roomId);
        List<BookingEntity> existingBookings = roomEntity.getBookings();
        boolean roomIsAvailable = roomIsAvailable(bookingRequest,existingBookings);
        String ConfirmationCode = "";
        if (roomIsAvailable){
            BookingEntity booking = new BookingEntity();
            booking.setChildren(bookingRequest.getChildren());
            booking.setAdults(bookingRequest.getAdults());
            booking.setCheckIn(bookingRequest.getCheckIn());
            booking.setCheckOut(bookingRequest.getCheckOut());
            booking.setUserEmail(bookingRequest.getUserEmail());
            booking.setFullName(bookingRequest.getFullNameGuest());
            ConfirmationCode = roomEntity.addBooking(booking);
            bookingRepository.save(booking);
        }else {
            throw new RuntimeException("Sorry, Thi s room is not Available  for the selected dates;");
        }
        return ConfirmationCode;

    }

    @Override
    public List<BookingEntity> getAllBookingsByRoomId(int roomId) {
        return bookingRepository.findByRoomId(roomId);
    }

    @Override
    public void cancelBooking(int bookingId) {
        bookingRepository.deleteById(bookingId);
    }

    private boolean roomIsAvailable(BookingRequest bookingRequest, List<BookingEntity> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getCheckIn().equals(existingBooking.getCheckIn())
                                || bookingRequest.getCheckOut().isBefore(existingBooking.getCheckOut())
                                || (bookingRequest.getCheckIn().isAfter(existingBooking.getCheckIn())
                                && bookingRequest.getCheckIn().isBefore(existingBooking.getCheckOut()))
                                || (bookingRequest.getCheckIn().isBefore(existingBooking.getCheckIn())

                                && bookingRequest.getCheckOut().equals(existingBooking.getCheckOut()))
                                || (bookingRequest.getCheckIn().isBefore(existingBooking.getCheckIn())

                                && bookingRequest.getCheckOut().isAfter(existingBooking.getCheckOut()))

                                || (bookingRequest.getCheckIn().equals(existingBooking.getCheckOut())
                                && bookingRequest.getCheckOut().equals(existingBooking.getCheckIn()))

                                || (bookingRequest.getCheckIn().equals(existingBooking.getCheckOut())
                                && bookingRequest.getCheckOut().equals(bookingRequest.getCheckIn()))
                );
    }
}

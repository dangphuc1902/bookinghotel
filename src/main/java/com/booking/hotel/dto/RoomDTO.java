package com.booking.hotel.dto;

import com.booking.hotel.payload.request.BookingRequest;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

public class RoomDTO {
    private int id;

    private String RoomType;

    private double roomPrice;

    private String image;

    private List<BookingDto> bookings;

    public List<BookingDto> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingDto> bookings) {
        this.bookings = bookings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomType() {
        return RoomType;
    }

    public void setRoomType(String roomTypes) {
        RoomType = roomTypes;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    // Phương thức để thiết lập đường dẫn ảnh động
    public void setImageDynamic(String imageName) {
        // Đường dẫn cơ bản của ảnh
        String basePath = "http://localhost:8088/file/";

        // Xây dựng đường dẫn đầy đủ
        this.image = basePath + imageName;
    }


}

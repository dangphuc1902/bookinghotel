package com.booking.hotel.service.imp;

import com.booking.hotel.dto.RoomDTO;
import com.booking.hotel.dto.RoomTypesDTO;
import com.booking.hotel.entity.RoomEntity;
import com.booking.hotel.payload.request.RoomRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface RoomServiceImp {

    boolean addRoom(String roomType, double roomPrice, MultipartFile image);

    List<RoomDTO> getAllRoom();

    List<RoomTypesDTO> getRoomType();

    boolean deleteRoom(int id);

    boolean updateRoom(RoomRequest roomRequest);

    RoomDTO getRoomById(int id);
    Optional<RoomEntity> getRoomByID(int id);
}

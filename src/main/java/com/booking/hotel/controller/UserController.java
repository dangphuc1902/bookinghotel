package com.booking.hotel.controller;

import com.booking.hotel.dto.RoleDTO;
import com.booking.hotel.dto.UserDTO;
import com.booking.hotel.entity.UserEntity;
import com.booking.hotel.exception.InvalidOldPasswordException;
import com.booking.hotel.exception.NotRoleDeleteException;
import com.booking.hotel.exception.UserAlreadyExistsException;
import com.booking.hotel.payload.request.PasswordRequest;
import com.booking.hotel.payload.request.UserRequest;
import com.booking.hotel.payload.response.BaseResponse;
import com.booking.hotel.service.UserService;
import com.booking.hotel.service.imp.UserServiceImp;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserServiceImp userServiceImp;

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getUsers(){

        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setData(userServiceImp.getUser());

        return new ResponseEntity<>(baseResponse, HttpStatus.FOUND);
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable("email") String email){

        try{
            BaseResponse baseResponse = new BaseResponse();
            UserEntity usersEntity = userServiceImp.getUserByEmail(email);
            UserDTO userDTO = new UserDTO(
                    usersEntity.getId()
                    , usersEntity.getEmail()
                    , usersEntity.getFirstName()
                    , usersEntity.getLastName()
                    , new RoleDTO(
                            usersEntity.getRole().getId()
                            , usersEntity.getRole().getName())
                    , usersEntity.getAvata()
            );
            baseResponse.setData(userDTO);
            return ResponseEntity.ok(baseResponse);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error fetching user");
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable("userId") String email, HttpServletRequest request){
        try {
            userServiceImp.deleteUser(email, request);
            BaseResponse baseResponse = new BaseResponse();
            baseResponse.setMessage("User deleted successfully");
            return ResponseEntity.ok(baseResponse);
        }catch (UsernameNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }catch (NotRoleDeleteException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user: " + e.getMessage());
        }

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(UserRequest userRequest){
        BaseResponse baseResponse = new BaseResponse();
        System.out.println(userRequest.getId());
        try{
            userServiceImp.updateUser(userRequest);
            baseResponse.setMessage("Update Successful!");
            return ResponseEntity.ok(baseResponse);
        }catch (UsernameNotFoundException | UserAlreadyExistsException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword( PasswordRequest passwordRequest) {
        BaseResponse baseResponse = new BaseResponse();
        try {
            userServiceImp.changePassword(passwordRequest);
            baseResponse.setMessage("Password changed successfully.");
            return ResponseEntity.ok(baseResponse);
        } catch (InvalidOldPasswordException e) {
            baseResponse.setStatusCode(400);
            baseResponse.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(baseResponse);
        } catch (Exception e) {
            baseResponse.setStatusCode(500);
            baseResponse.setMessage("An error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(baseResponse);
        }
    }
}

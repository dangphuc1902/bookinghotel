package com.booking.hotel.service;

import com.booking.hotel.dto.RoleDTO;
import com.booking.hotel.dto.UserDTO;
import com.booking.hotel.entity.RoleEntity;
import com.booking.hotel.entity.UserEntity;
import com.booking.hotel.exception.InvalidOldPasswordException;
import com.booking.hotel.exception.NotRoleDeleteException;
import com.booking.hotel.exception.UserAlreadyExistsException;
import com.booking.hotel.exception.UserNameNotFoundException;
import com.booking.hotel.payload.request.PasswordRequest;
import com.booking.hotel.payload.request.UserRequest;
import com.booking.hotel.repository.RoleRepository;
import com.booking.hotel.repository.UserRepository;
import com.booking.hotel.service.imp.BookingServiceImp;
import com.booking.hotel.service.imp.UserServiceImp;
import com.booking.hotel.utils.JwtUltils;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserServiceImp {

    @Autowired
    private UserRepository usersRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUltils jwtUltils;

    @Autowired
    private FileService fileService;

    @Autowired
    private BookingServiceImp bookingServiceImp;

    @Autowired
    Gson gson;

    @Override
    public List<UserDTO> getUser() {
        List<UserEntity> usersEntities = usersRepository.findAll();
        List<UserDTO> userDTOS = usersEntities
                .stream()
                .map(user -> new UserDTO(
                        user.getId()
                        ,user.getEmail()
                        ,user.getFirstName()
                        ,user.getLastName()
                        ,new RoleDTO(
                              user.getRole().getId()
                            ,user.getRole().getName()),
                        user.getAvata()
                ))
                .toList();
        return userDTOS;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new UserNameNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(String email, HttpServletRequest request) {
        UserEntity usersEntity = getUserByEmail(email);
        String role = "";
        String headerAuthen = request.getHeader("Authorization");
        if(headerAuthen != null && headerAuthen.trim().length() > 0) {
            String token = headerAuthen.substring(7);
            //Giải mã token
            String data = jwtUltils.decryptToken(token);
            if(data != null){
                JsonObject jsonObject = JsonParser.parseString(data).getAsJsonObject();
                role = jsonObject.get("name").getAsString();
                System.out.println(getUserNameBycookei(request));
            }
            if(usersEntity != null){
                if (role.equals("ROLE_ADMIN") || (role.equals("ROLE_USER")) && usersEntity.getEmail().equals(getUserNameBycookei(request))) {
                    usersRepository.delete(usersEntity);
                }else {
                    throw new NotRoleDeleteException("There is no right to delete");
                }
            }

        }

    }

    @Override
    public void updateUser(UserRequest userRequest) {

        UserEntity user = usersRepository.findById(userRequest.getId())
                .orElseThrow(() -> new UserNameNotFoundException("User not found"));
        if(user.getEmail().equals(userRequest.getEmail())){
            bookingServiceImp.updateEmail(user.getEmail(), userRequest.getEmail());
            saveUser(userRequest, user);
        }else if(usersRepository.existsByEmail(userRequest.getEmail())){
            throw new UserAlreadyExistsException(userRequest.getEmail() + " already exists");
        }else {
            bookingServiceImp.updateEmail(user.getEmail(), userRequest.getEmail());
            saveUser(userRequest, user);
        }
    }

    @Override
    public void changePassword(PasswordRequest passwordRequest) throws InvalidOldPasswordException {
        Optional<UserEntity> optionalUser = usersRepository.findByEmail(passwordRequest.getUserName());
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        UserEntity user = optionalUser.get();

        if (!passwordEncoder.matches(passwordRequest.getOldPassword(), user.getPassword())) {
            throw new InvalidOldPasswordException("Password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(passwordRequest.getNewPassword()));
        usersRepository.save(user);
    }

    private void saveUser(UserRequest userRequest, UserEntity user){
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        if(userRequest.getEmail() == null || userRequest.getEmail().isEmpty()){
            userEntity.setEmail(user.getEmail());
        }else {
            userEntity.setEmail(userRequest.getEmail());
        }
        if(userRequest.getFirstName() == null || userRequest.getFirstName().isEmpty()){
            userEntity.setFirstName(user.getFirstName());
        }else {
            userEntity.setFirstName(userRequest.getFirstName());
        }
        if(userRequest.getLastName() == null || userRequest.getLastName().isEmpty()){
            userEntity.setLastName(user.getLastName());
        }else {
            userEntity.setLastName(userRequest.getLastName());
        }
        userEntity.setPassword(user.getPassword());
        userEntity.setRole(user.getRole());
        if(userRequest.getAvatar() == null || userRequest.getAvatar().isEmpty()){
            userEntity.setAvata(user.getAvata());
        }else {
            fileService.saveFile(userRequest.getAvatar());
            userEntity.setAvata(userRequest.getAvatar().getOriginalFilename());
        }
        usersRepository.save(userEntity);
    }

    public String getUserNameBycookei(HttpServletRequest request) {
        String userName = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userName")) {
                    try {
                        userName = URLDecoder.decode(cookie.getValue(), StandardCharsets.UTF_8);
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }


                }
            }
        }
        return userName;
    }
}

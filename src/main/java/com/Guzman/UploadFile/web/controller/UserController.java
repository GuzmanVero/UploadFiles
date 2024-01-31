package com.Guzman.UploadFile.web.controller;

import com.Guzman.UploadFile.persistence.entity.UserEntity;
import com.Guzman.UploadFile.web.config.JwtUtil;
import com.Guzman.UploadFile.domain.service.UserService;
import com.Guzman.UploadFile.domain.service.dto.LoginDto;
import com.Guzman.UploadFile.persistence.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    public UserController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }
    @PostMapping("/signup")
    public UserEntity save(@RequestBody UserEntity user){

        return userService.save(user);
    }
    @GetMapping("/{id}")
    public UserEntity getId(@PathVariable("id") int userId) {
        return userService.getId(userId);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken login = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
        Authentication authentication = this.authenticationManager.authenticate(login);

        System.out.println(authentication.isAuthenticated());
        System.out.println(authentication.getPrincipal());

        String jwt = this.jwtUtil.create(loginDto.getEmail());

        LoginResponse response = new LoginResponse("Login exitoso", loginDto.getEmail(), jwt);

        return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwt).body(response);
    }
    @GetMapping("/login/profile")
    public ResponseEntity<?> getProfile(Authentication authentication) {
        String email = ((UserDetails) authentication.getPrincipal()).getUsername();
        UserEntity user = userService.getprofile(email);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        Map<String, Object> profile = new HashMap<>();
        profile.put("email", user.getEmail());
        profile.put("username", user.getUsername());

        return ResponseEntity.ok(profile);
    }
}

package com.UniProject.Controller;

import com.UniProject.DTO.JwtResponse;
import com.UniProject.DTO.RefreshToken;
import com.UniProject.DTO.SignInRequest;
import com.UniProject.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse>login(@RequestBody SignInRequest signInRequest){
        return ResponseEntity.ok(authenticationService.signin(signInRequest));
    }
    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse>refreshToken(@RequestBody RefreshToken refreshToken){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }
}

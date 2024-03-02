package com.UniProject.Controller;

import com.UniProject.DTO.*;
import com.UniProject.Services.AuthenticationService;
import com.UniProject.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private int code;
    private String userEmail;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public Object userLogin(@RequestBody SignInRequest info){
        JwtResponse response;
        try{
            response=authenticationService.login(info);
            return ResponseEntity.ok(response);
        }catch (Exception e){
            Message message=new Message();
            message.setMessage("Wrong username or password");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(message);
        }
    }

    @PostMapping("/save")
    public ResponseEntity<String> saveUser(@RequestBody UserDto user) {
        if (!userService.checkForDuplicateEmail(user.getEmail())) {
            System.out.println(user);
            // Redirect back to the form with an error message
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exist try another email");
        }
        //user.setEnabled(false);
        int code = userService.saveUser(user);
        if(code==1){
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Information saved Successfully");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred while saving");
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtResponse>refreshToken(@RequestBody RefreshToken refreshToken){
        return ResponseEntity.ok(authenticationService.refreshToken(refreshToken));
    }

    /*@PostMapping("/check")
    public ResponseEntity<String> show(@RequestBody VerCode verCode){
        int userCode = verCode.getCode();
        if(userCode==code){
            userService.verifyUser(userEmail);
            return ResponseEntity.status(HttpStatus.OK).body("Verified");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Wrong verification code");
    }*/

    /*@GetMapping("/verify/{email}")
    public ResponseEntity<String> sendEmail(@PathVariable String email){
        userEmail=email;
        code=userService.getVerCode(email);
        System.out.println(code);
        return ResponseEntity.status(HttpStatus.OK).body("Sent");
    }*/

}

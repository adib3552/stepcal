package com.UniProject.Controller;

import com.UniProject.DTO.LoginDetails;
import com.UniProject.DTO.TaskDto;
import com.UniProject.DTO.UserDto;
import com.UniProject.Entities.User;
import com.UniProject.DTO.VerCode;
import com.UniProject.Services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private int code;
    private String userEmail;

    @Autowired
    UserService userService;

    @GetMapping("/message")
    public ResponseEntity<String>showMessage(){
        return ResponseEntity.status(HttpStatus.OK).body("This is for User");
    }

    @GetMapping("/profile")
    public UserDto showProfile(HttpServletRequest request){
        String email= (String) request.getAttribute("email");
        return userService.getUser(email);
    }

    @GetMapping("/task")
    public TaskDto sendTask(HttpServletRequest request){
        String email=(String) request.getAttribute("email");
        return userService.forTask(email);
    }

    @GetMapping("/verify/{email}")
    public ResponseEntity<String> sendEmail(@PathVariable String email){
        userEmail=email;
        code=userService.getVerCode(email);
        System.out.println(code);
        return ResponseEntity.status(HttpStatus.OK).body("Sent");
    }

    @PostMapping("/check")
    public ResponseEntity<String> show(@RequestBody VerCode verCode){
        int userCode = verCode.getCode();
        if(userCode==code){
            userService.verifyUser(userEmail);
            return ResponseEntity.status(HttpStatus.OK).body("Verified");
        }
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Wrong verification code");
    }

}

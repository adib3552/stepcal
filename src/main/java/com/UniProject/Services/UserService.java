package com.UniProject.Services;

import com.UniProject.Entities.User;
import com.UniProject.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;


    public int saveUser(User user){
        try{
            User newUser=userRepository.save(user);
            if(newUser!=null){
                return 1;//Success
            }
            else{
                return 2;//Failed saving
            }
        }catch (Exception e){
            return 0;//Internal server error
        }
    }

    @Transactional
    public void verifyUser(String email){
         userRepository.updateUserEnable(email);
    }
    public User getUser(String email){
        return userRepository.findByEmail(email);
    }

    public User checkUser(String email,String pass){
        return userRepository.findByEmailAndPassword(email,pass);
    }

    /**
     * For mail
     **/
    public boolean checkForDuplicateEmail(String email){
        User check=userRepository.findByEmail(email);
        return check==null;
    }
    public boolean checkEmailVerification(String email){
        return userRepository.findByEmail(email).isEnabled();
    }
    public int getVerCode(String toEmail){
        return emailService.sendVerEmail(toEmail);
    }
}

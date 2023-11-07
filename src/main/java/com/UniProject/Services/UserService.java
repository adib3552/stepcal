package com.UniProject.Services;

import com.UniProject.DTO.DtoImpl;
import com.UniProject.DTO.UserDto;
import com.UniProject.Entities.User;
import com.UniProject.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DtoImpl dto;




    public int saveUser(UserDto userDto){
        try{
            User newUser=userRepository.save(dto.convertUserDtoToUser(userDto));
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
    public UserDto getUser(String email){
        return dto.convertUserToUserDto(userRepository.findByEmail(email));
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
    public User findEmail(String email){
        return userRepository.findByEmail(email);
    }
    public boolean checkEmailVerification(String email){
        return userRepository.findByEmail(email).isEnabled();
    }
    public int getVerCode(String toEmail){
        return emailService.sendVerEmail(toEmail);
    }
}

package com.UniProject.Services;

import com.UniProject.DTO.DtoImpl;
import com.UniProject.DTO.TaskDto;
import com.UniProject.DTO.TaskParam;
import com.UniProject.DTO.UserDto;
import com.UniProject.Entities.User;
import com.UniProject.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private DtoImpl dto;

    private TaskParam taskParam;




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
    public TaskDto forTask(String email){
        UserDto user=dto.convertUserToUserDto(userRepository.findByEmail(email));
        //setting the parameter to compute
        taskParam=new TaskParam(user.getAge(),user.getHeight(),user.getWeight(),user.getGoal(),user.getGender());

        TaskDto task=new TaskDto();
        task.setTarget_Calorie(computeTargetCalorie(taskParam));

        return task;
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

    public double computeTargetCalorie(TaskParam taskParam){
        //calculate bmr
        double bmr=88.362+(13.397 * taskParam.getWeight())+(4.799 * taskParam.getHeight())-(5.677*taskParam.getAge());
        return bmr;
    }
}

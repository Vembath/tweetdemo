package com.tweetapp.service;

import com.tweetapp.config.SecurityConfig;
import com.tweetapp.exception.UserNotFoundException;
import com.tweetapp.model.Constants;
import com.tweetapp.model.UserProfile;
import com.tweetapp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class TweetappUserServiceImpl implements TweetappUserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    SecurityConfig config;

    @Override
    public String registerUser(UserProfile users) {
        users.setPassword( config.passwordEncoder().encode(users.getPassword()));
        userRepo.save(users);
        return " Successfully Created";
    }



    @Override
    public String toresetPassword(String userName,UserProfile users) {
        if(userRepo.findById(userName).isPresent()){
           UserProfile userdata= userRepo.findById(userName).get();
            userdata.setPassword(users.getPassword());
            userdata.setConfirmpassword(users.getPassword());
                userRepo.save(userdata);
                return "Password changed successfully";
            }

           throw new UserNotFoundException(Constants.USERNOTFOUND);


    }

    @Override
    public List<UserProfile> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public List<String> userNameSearch(UserProfile user) {
        if(userRepo.getUsers(user.getLoginid())!=null){
            List<UserProfile> userList= userRepo.getUsers(user.getLoginid());
            List<String> userNamesList=new ArrayList<>();
            for(int i=0;i<userList.size();i++){
                userNamesList.add(userList.get(i).getLoginid());
            }
            return userNamesList;
        }
        throw new UserNotFoundException(Constants.USERNOTFOUND);
    }
}

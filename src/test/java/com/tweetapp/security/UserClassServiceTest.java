package com.tweetapp.security;

import com.tweetapp.exception.UserNotFoundException;
import com.tweetapp.model.UserProfile;
import com.tweetapp.repo.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
 class UserClassServiceTest {

    @InjectMocks
    private UserClassService userClassService;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    public  void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void loadUserByUsernameTest(){
        UserProfile userObj = new UserProfile();
        userObj.setLoginid("TestId");
        userObj.setFristname("TestFirst");
        userObj.setLastname("TestLast");
        userObj.setEmail("testEmail@test.com");
        userObj.setPassword("TestPwd");
        userObj.setConfirmpassword("TestPwd");
        userObj.setContactnumber(9444770238L);
        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(java.util.Optional.of(userObj));
        Assertions.assertEquals(userObj.getLoginid(),userClassService.loadUserByUsername("TestId").getUsername());
    }

    @Test
    void loadUserByUsernameNotFoundExceptionTest(){
        UserProfile userObj = new UserProfile();

        Mockito.when(userRepo.findById(Mockito.anyString())).thenReturn(Optional.of(userObj));
        Assertions.assertThrows(UserNotFoundException.class,()->userClassService.loadUserByUsername("TestId"));
    }
}

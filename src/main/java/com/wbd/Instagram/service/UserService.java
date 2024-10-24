package com.wbd.Instagram.service;

import com.wbd.Instagram.model.User;
import com.wbd.Instagram.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User loadUserByName(String userName) throws UsernameNotFoundException{
        return userRepository.findByUserName(userName)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
    public User getUserByName(String userName){
        return userRepository.findByUserName(userName)
                .orElseThrow(()->new RuntimeException("User not found"));
    }
    public User registerUser(User user){
        return userRepository.save(user);
    }
}

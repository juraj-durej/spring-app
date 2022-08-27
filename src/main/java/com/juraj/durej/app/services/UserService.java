package com.juraj.durej.app.services;

import com.juraj.durej.app.entities.User;
import com.juraj.durej.app.repositories.UserRepository;
import com.juraj.durej.app.util.SortUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class UserService{

    Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        userRepository.save(user);
        return user;
    }

    public Page<User> getAllUsers(String filterStr, String rangeStr, String sortStr) {

        Pageable sorted = SortUtils.getPage(filterStr, rangeStr, sortStr);

        return userRepository.findAll(sorted);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public boolean deleteUser(Long id) {
        User user =  userRepository.findById(id).get();
        userRepository.delete(user);
        return true;
    }

    public User updateUser(Long id, User user) {
        userRepository.save(user);
        return user;
    }

}
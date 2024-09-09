package com.educandoweb.course.services;

import com.educandoweb.course.entities.User;
import com.educandoweb.course.repositories.UserRepository;
import com.educandoweb.course.services.exceptions.DatabaseException;
import com.educandoweb.course.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(long id){
        return userRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(id));
    }

    public User insert(User user){
        return userRepository.save(user);
    }

    public void delete(Long id){
        try {
            User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
            userRepository.deleteById(user.getId());
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException(e.getMessage());
        }

    }

    public User update(User user, Long id){
        try {
            User entity = userRepository.getReferenceById(id);
            updateData(entity, user);
            return userRepository.save(entity);
        } catch (jakarta.persistence.EntityNotFoundException e) {
            throw new ResourceNotFoundException(id);
        }

    }

    private void updateData(User entity, User user) {
        entity.setName(user.getName());
        entity.setEmail(user.getEmail());
        entity.setPhone(user.getPhone());
    }
}

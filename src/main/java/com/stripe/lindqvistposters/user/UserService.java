package com.stripe.lindqvistposters.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void deleteUserByRole(Integer id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            if (user.get().getRole().equals(Role.ADMIN)) {
                throw new IllegalStateException("You don't have permission to delete an admin!");
            }
            userRepository.deleteById(id);
        } else {
            throw new IllegalStateException("User with id " + id + " does not exist!");
        }
    }


}

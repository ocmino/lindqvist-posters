package com.stripe.lindqvistposters.user;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RequestMapping("api/v1/users")
@RestController
public class UserController {

    private final UserRepository userRepository;

    private final UserService userService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    //get all users from database
    @GetMapping("/get-all-users")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    //delete user by id
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<?> deleteUserByRole(@PathVariable Integer id) {
        userService.deleteUserByRole(id);
        return ResponseEntity.ok("User deleted!");
    }

    //update user
    @PutMapping("/update-user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody User user) {
        User currentUser = userRepository.findById(id).orElse(null);
        if (currentUser == null) {
            return ResponseEntity.badRequest().body("User not found.");
        }
        currentUser.setUsername(user.getUsername());
        currentUser.setEmail(user.getEmail());
        currentUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        currentUser.setRole(user.getRole());
        User updatedUser = userRepository.save(currentUser);
        return ResponseEntity.ok(updatedUser);
    }


}

package code.cf1v1.controller;

import code.cf1v1.entity.User;
import code.cf1v1.repository.UserRepository;
import code.cf1v1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    UserRepository userRepository;
    @PostMapping
    public ResponseEntity<?> registerUser(@RequestBody User user) throws Exception{
        return userService.register(user);
    }
    @GetMapping("/me")
    public User aboutMe(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByUsername(auth.getName());
    }
    @PostMapping("/add-friend/{username}")
    public ResponseEntity<?> addFriend(@PathVariable String username){
        Authentication auth= SecurityContextHolder.getContext().getAuthentication();
        User currUser=userRepository.findByUsername(auth.getName());
        if(userRepository.findByUsername(username)!=null && !currUser.getFriends().contains(username)){
            currUser.getFriends().add(username);
            userRepository.save(currUser);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/get-friends")
    public ResponseEntity<List<String>> getAllFriends(){
        Authentication auth=SecurityContextHolder.getContext().getAuthentication();
        User currUser=userRepository.findByUsername(auth.getName());
        return new ResponseEntity<>(currUser.getFriends(), HttpStatus.OK);
    }
}

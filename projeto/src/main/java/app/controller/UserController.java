package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import app.entity.User;
import app.service.UserService;

import java.util.List;

@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public User findById(@PathVariable Long id){
        return userService.findById(id);
    }

    @GetMapping
    public List<User> findAll(){
        return userService.findAll();
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam String name) {
        return userService.getUsersByName(name);  
    }

    @PostMapping
    public User save(@RequestBody User user){
        return userService.postMapping(user);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        userService.deleteMapping(id);
    }

    @PutMapping
    public User edit(@RequestBody User user){
        return userService.putMapping(user);
    }

}

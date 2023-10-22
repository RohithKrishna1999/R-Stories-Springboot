package com.Rtech.Media.Controllers;

import com.Rtech.Media.Exceptions.UserNotFountException;
import com.Rtech.Media.Modals.Users;
import com.Rtech.Media.Services.JwtTokenUtil;
import com.Rtech.Media.Services.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping(value = "/api")
//@CrossOrigin
public class Login {
    @Autowired
    private LoginServiceImpl loginServiceImpl;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/save")
    public ResponseEntity<String> signUp(@RequestBody Users user){
        try{
            String _users = loginServiceImpl.signUp(user);
            return new ResponseEntity<>(_users, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @RequestMapping(method = {POST},value = "/login")
    public String login(@RequestBody Users users) {
        final String username = loginServiceImpl.loginPerson(users).orElseThrow(UserNotFountException::new);
        final String token=jwtTokenUtil.generateToken(username);
        return token;
    }
    @GetMapping("/getUserById")
    public ResponseEntity<Optional<Users>> getUserById(@RequestParam("id") int id){
        try{
            Optional<Users> _users = loginServiceImpl.getById(id);
            return new ResponseEntity<>(_users, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getUserByUsername")
    public ResponseEntity<Users> getUserById(@RequestParam("name") String name){
        try{
            Users _users = loginServiceImpl.getUserByUsername(name);
            return new ResponseEntity<>(_users, HttpStatus.CREATED);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

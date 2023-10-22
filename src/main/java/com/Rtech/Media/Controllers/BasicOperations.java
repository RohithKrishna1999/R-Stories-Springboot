package com.Rtech.Media.Controllers;

import com.Rtech.Media.Modals.Operations;
import com.Rtech.Media.Services.BasicOpsServiceImpl;
import com.Rtech.Media.Services.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ops")
public class BasicOperations {

    @Autowired
    private BasicOpsServiceImpl basicOpsService;

    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping("/postOps")
    public ResponseEntity<String> postBasicOps(@RequestBody Operations operations){
        try{
            String returnValue=basicOpsService.basicOps(operations);
            if(returnValue=="OK"){
                return new ResponseEntity<>(returnValue,HttpStatus.OK);
            }
            else{
                throw new Exception("Not Success");
            }
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/dislike")
    public ResponseEntity<String> disLike(@RequestBody Operations operations){
        try{
            basicOpsService.disLike(operations);
            return new ResponseEntity<>("OK",HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/comment")
    public ResponseEntity<List<Operations>> comments(@RequestParam("id") Integer post_id){
        try{
            List<Operations> cmm=basicOpsService.comments(post_id);
            return new ResponseEntity<>(cmm,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getUserIdByToken")
    public ResponseEntity<Integer> getUserIdByToken(@RequestParam("token") String token){
        try{
            Integer cmm=loginService.getUserIdByToken(token);
            return new ResponseEntity<>(cmm,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

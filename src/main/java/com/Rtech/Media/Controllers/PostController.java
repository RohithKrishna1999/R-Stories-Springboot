package com.Rtech.Media.Controllers;

import com.Rtech.Media.Exceptions.UserNotFountException;
import com.Rtech.Media.Modals.PostWithLike;
import com.Rtech.Media.Modals.Posts;
import com.Rtech.Media.Modals.Users;
import com.Rtech.Media.Services.LoginServiceImpl;
import com.Rtech.Media.Services.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostServiceImpl postService;
    @Autowired
    private LoginServiceImpl loginService;

    @PostMapping("/save")
    public ResponseEntity<String> savePost(@RequestBody Posts posts) throws UserNotFountException {
        try{
            Integer postID = 0;
            String posted= new String("");
            String _users=loginService.getUserById(posts.getUser_id());
            if(_users ==""){
                throw new UserNotFountException();
            }
            if(posts.getPost_id()!=null) {
                postID = postService.saveThePost(posts);
            }
            else{
                throw new NullPointerException();
            }

            if(postID!=0){
                posted="Saved";
            }else{
                throw new Exception("Post Not Saved");
            }

            return new ResponseEntity<>(posted, HttpStatus.CREATED);
        }
        catch (UserNotFountException u){
            return new ResponseEntity<>(u.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/AllPosts")
    public ResponseEntity<List<?>> getAllPost(@RequestParam("id") Integer userID){
        try{
            List<?> allPosts= postService.getAllPost(userID);
            return new ResponseEntity<>(allPosts, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/TopPosts")
    public ResponseEntity<List<?>> getTopPosts(@RequestParam("id") Integer userID){
        try{
            List<?> allPosts=postService.getTopPosts(userID);
            return new ResponseEntity<>(allPosts, HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

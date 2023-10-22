package com.Rtech.Media.Services;

import com.Rtech.Media.Modals.PostWithLike;
import com.Rtech.Media.Modals.Posts;
import com.Rtech.Media.Repositorys.BasicOperationRepository;
import com.Rtech.Media.Repositorys.PostRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Base64;
import javax.persistence.EntityManager;
import java.sql.*;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

@Service
public class PostServiceImpl {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private BasicOperationRepository basicOperationRepository;

    public Integer saveThePost(Posts posts){
        Integer postId=0;
        Connection cc = ((SessionImpl) entityManager.getDelegate()).connection();
        CallableStatement callableStatement;
        try{
            //byte[] imageBytes = org.apache.commons.codec.binary.Base64.decodeBase64(posts.getImageUrl());
            callableStatement = cc.prepareCall("{call dbo.savePosts(?,?,?,?,?,?,?)}"); //Output # 1
            callableStatement.setInt(1, posts.getUser_id());//Parameter #1
            callableStatement.setString(2, posts.getCreatedDate());//Parameter #2
            callableStatement.setString(3, posts.getImageUrl());//Parameter #3
            callableStatement.setString(4, posts.getStory());//Parameter #4
            callableStatement.setString(5, posts.getTitle());//Parameter #5
            callableStatement.setString(6, posts.getFiletype());//Parameter #5
            callableStatement.registerOutParameter(7, Types.INTEGER); //Output # 1
            callableStatement.execute();
            postId = callableStatement.getInt(7);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return postId;
    }
    public List<?> getAllPost(Integer userID){
        List<?> posts=new ArrayList<>();
        Connection cc = ((SessionImpl) entityManager.getDelegate()).connection();
        CallableStatement callableStatement;
        try{
            callableStatement = cc.prepareCall("{call dbo.GET_TOP_POSTS(?,?)}"); //Output # 1
            callableStatement.setInt(1, userID);//Parameter #1
            callableStatement.registerOutParameter(2, Types.JAVA_OBJECT); //Output # 1
            callableStatement.execute();
            String jsonPosts= callableStatement.getString(2);
            // Initialize the Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert JSON to Java List
            posts = objectMapper.readValue(jsonPosts, new TypeReference<List<?>>() {});
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }
    public List<?> getTopPosts(Integer userID){
        List<?> posts=new ArrayList<>();
        Connection cc = ((SessionImpl) entityManager.getDelegate()).connection();
        CallableStatement callableStatement;
        try{
            callableStatement = cc.prepareCall("{call dbo.GET_TOP_10_POSTS(?,?)}"); //Output # 1
            callableStatement.setInt(1, userID);//Parameter #1
            callableStatement.registerOutParameter(2, Types.JAVA_OBJECT); //Output # 1
            callableStatement.execute();
            String jsonPosts= callableStatement.getString(2);
            // Initialize the Jackson ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();

            // Convert JSON to Java List
            posts = objectMapper.readValue(jsonPosts, new TypeReference<List<?>>() {});
        }catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (JsonMappingException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return posts;
    }
}

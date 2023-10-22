package com.Rtech.Media.Services;

import com.Rtech.Media.Modals.Operations;
import com.Rtech.Media.Repositorys.BasicOperationRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;

@Service
public class BasicOpsServiceImpl {
    @Autowired
    private BasicOperationRepository basicOperationRepository;
    @Autowired
    private EntityManager entityManager;

    public String basicOps(Operations operations){
        String returnValue="";
        Operations operations1=new Operations();
        operations1.setUser_id(operations.getUser_id());
        operations1.setPost_id(operations.getPost_id());
        operations1.setType(operations.getType());
        operations1.setComments(operations.getComments());
        operations1.setSno(operations.getSno());
        Operations operations2=basicOperationRepository.save(operations1);
        if(operations2.getSno()!=0){
            returnValue = "OK";
        }
        return returnValue;
    }

    public void disLike(Operations operations){
        Integer id=0;
        Connection cc = ((SessionImpl) entityManager.getDelegate()).connection();
        CallableStatement callableStatement;
        try{
            callableStatement = cc.prepareCall("{call dbo.DISLIKETHEPOST(?,?,?)}"); //Output # 1
            callableStatement.setInt(1, operations.getPost_id());//Parameter #1
            callableStatement.setInt(2, operations.getUser_id());//Parameter #2
            callableStatement.registerOutParameter(3, Types.INTEGER); //Output # 1
            callableStatement.execute();
            id = callableStatement.getInt(3);
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Operations> comments(Integer post_Id){
        List<Operations> comment = basicOperationRepository.comments(post_Id);
        return comment;
    }
}

package com.Rtech.Media.Services;

import com.Rtech.Media.Connections.GenericDatabaseCaller;
import com.Rtech.Media.Modals.Users;
import com.Rtech.Media.Repositorys.LoginRepository;
import org.hibernate.internal.SessionImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class LoginServiceImpl {

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private JavaMailSender javaMailSender;
    public Optional<String> loginPerson(Users users) {
        String token=new String("");
        Users users1=new Users();
        users1.setUsername(users.getUsername());
        users1.setPassword(users.getPassword());
        token= loginRepository.LoginPerson(users1.getUsername(),users1.getPassword());
        return Optional.ofNullable(token);
    }

    public String signUp(Users users) {
        Users users1=new Users();
        users1.setUsername(users.getUsername());
        users1.setUnlockKey(users.getUnlockKey());
        users1.setPassword(users.getPassword());
        users1.setName(users.getName());
        users1.setWorkName(users.getWorkName());
        users1.setUser_id(users.getUser_id());
        users1.setEmail(users.getEmail());
        Connection cc = ((SessionImpl) entityManager.getDelegate()).connection();
        CallableStatement callableStatement;
        String outputValue="";
        try {
            callableStatement = cc.prepareCall("{call dbo.SaveTheUserToDB(?,?,?,?,?,?)}");
            callableStatement.registerOutParameter(1, Types.VARCHAR); //Output # 1
            callableStatement.setString(2, users1.getName());//Parameter #1
            callableStatement.setString(3, users1.getWorkName());////Parameter #2
            callableStatement.setString(4, users1.getUnlockKey());// //Parameter #3
            callableStatement.setString(5, users1.getPassword());//Parameter # 4
            callableStatement.setString(6, users1.getEmail());//Parameter # 5
            callableStatement.execute();

            outputValue = callableStatement.getString(1);
            if(outputValue!=""){
                String body="Your Username "+outputValue+" Your Password "+users1.getPassword();
                sendEmail(users1.getEmail(),"Login Details",body);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return outputValue;
    }
    public String getUserById(int id){
        return loginRepository.getUserNameById(id);
    }
    public Optional<Users> getById(int id){
        return Optional.ofNullable(loginRepository.getUserById(id));
    }
    public Users getUserByUsername(String userName){
        return loginRepository.getUserByUserName(userName);
    }

    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        javaMailSender.send(message);
    }
    public Integer getUserIdByToken(String token){
        String username=jwtTokenUtil.getUsernameFromToken(token);
        Users user1=getUserByUsername(username);
        return user1.getUser_id();
    }
}

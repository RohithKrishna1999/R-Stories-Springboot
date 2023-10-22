package com.Rtech.Media.Repositorys;

import com.Rtech.Media.Modals.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoginRepository extends CrudRepository<Users,Integer> {
    @Query(value = "select Username from dbo.Users where Username=:username AND Password=:password", nativeQuery = true)
    public String LoginPerson(String username,String password);
    @Query(value = "select Username from dbo.Users where id=:id", nativeQuery = true)
    public String getUserNameById(Integer id);
    @Query(value = "select * from dbo.Users where Username=:username", nativeQuery = true)
    public Users getUserByUserName(String username);

    @Query(value = "select * from dbo.Users where id=:id", nativeQuery = true)
    public Users getUserById(Integer id);
}
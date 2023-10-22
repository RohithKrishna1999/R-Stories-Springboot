package com.Rtech.Media.Repositorys;

import com.Rtech.Media.Modals.Operations;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasicOperationRepository extends JpaRepository<Operations,Integer> {
    @Query(value = "select count(*) from dbo.operations where post_id=:post_id and type=1", nativeQuery = true)
    public Integer likes(Integer post_id);

    @Query(value = "select * from dbo.operations where post_id=:post_id and type=2", nativeQuery = true)
    public List<Operations> comments(Integer post_id);
}

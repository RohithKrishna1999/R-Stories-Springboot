package com.Rtech.Media.Repositorys;

import com.Rtech.Media.Modals.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository extends JpaRepository<Posts,Integer> {
    @Query(value = "call dbo.GET_TOP_POSTS_BASEDON_LIKE", nativeQuery = true)
    public List<Posts> allPosts();
}

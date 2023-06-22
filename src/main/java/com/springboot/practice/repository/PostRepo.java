package com.springboot.practice.repository;

import com.springboot.practice.entities.Post;
import com.springboot.practice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post, Integer> {
    List<Post> findByUser( User user);
    @Query("select p from Post p where p.title like :key")
    List<Post> searchByTitle(@Param("key") String title);


}

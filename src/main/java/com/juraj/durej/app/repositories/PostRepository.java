package com.juraj.durej.app.repositories;

import com.juraj.durej.app.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}

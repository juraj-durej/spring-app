package com.juraj.durej.app.repositories;

import com.juraj.durej.app.entities.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

}

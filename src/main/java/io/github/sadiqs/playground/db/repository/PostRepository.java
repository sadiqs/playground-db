package io.github.sadiqs.playground.db.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.github.sadiqs.playground.db.model.Post;

public interface PostRepository extends JpaRepository<Post, UUID> {

    @Query("SELECT p FROM Post p WHERE p.id = :id FOR UPDATE")
    Optional<Post> selectForUpdate(UUID id);
}
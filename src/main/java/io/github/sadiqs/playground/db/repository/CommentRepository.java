package io.github.sadiqs.playground.db.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.github.sadiqs.playground.db.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    int countByPostId(UUID postId);
}

package io.github.sadiqs.playground.db.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.retry.annotation.Retryable;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import io.github.sadiqs.playground.db.model.Comment;
import io.github.sadiqs.playground.db.model.CommentRequest;
import io.github.sadiqs.playground.db.model.Post;
import io.github.sadiqs.playground.db.repository.CommentRepository;
import io.github.sadiqs.playground.db.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {

    private static final int MAX_COMMENTS = 1;

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public void savePost(Post post) {
        var savedPost = postRepository.save(post);
        log.info("Saved post: {}", savedPost);
    }

    public Optional<Post> getPost(UUID id) {
        return postRepository.findById(id);
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000), retryFor = ConcurrencyFailureException.class)
    public void saveComment(UUID postId, CommentRequest commentRequest) {
        log.info("Adding comment: {} for post: {}", commentRequest.content(), postId);
        Post post = getPost(postId).orElse(null);
        if (post != null) {
            int commentCount = commentRepository.countByPostId(postId);
            log.info("Comment count: {}", commentCount);
            if (commentCount >= MAX_COMMENTS) {
                log.error("Comment count exceeded: {}", MAX_COMMENTS);
                throw new RuntimeException("Comment count exceeded");
            }
            post.addComment(Comment.builder().content(commentRequest.content()).build());
            try {
                TimeUnit.SECONDS.sleep(commentRequest.lock());
            } catch (InterruptedException e) {
                log.error("Error sleeping", e);
            }
            log.info("Added comment: {} to {}", commentRequest.content(), post.getTitle());
        } else {
            log.error("Post not found: {}", postId);
        }
    }

    public List<Post> getPosts() {
        return postRepository.findAll();
    }
}

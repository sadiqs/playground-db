package io.github.sadiqs.playground.db.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.sadiqs.playground.db.model.CommentRequest;
import io.github.sadiqs.playground.db.model.Post;
import io.github.sadiqs.playground.db.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @PostMapping
    public void savePost(@RequestBody Post post) {
        log.info("Saving post: {}", post);
        postService.savePost(post);
    }

    @PostMapping("/{postId}/comments")
    public void saveComment(@PathVariable UUID postId, @RequestBody CommentRequest commentRequest) {
        log.info("Saving comment: {} for post: {}", commentRequest.content(), postId);
        postService.saveComment(postId, commentRequest);
        log.info("Saved comment: {} for post: {}", commentRequest.content(), postId);
    }

}

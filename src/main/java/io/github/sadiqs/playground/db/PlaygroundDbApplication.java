package io.github.sadiqs.playground.db;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.annotation.EnableRetry;

import io.github.sadiqs.playground.db.model.Comment;
import io.github.sadiqs.playground.db.model.Post;
import io.github.sadiqs.playground.db.repository.PostRepository;
import jakarta.transaction.Transactional;

@SpringBootApplication
@EnableRetry
public class PlaygroundDbApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaygroundDbApplication.class, args);
	}

	/*
	 * 
	 * INSERT INTO posts (id, title, content) VALUES
	 * (1, 'First Post', 'This is my first post content.'),
	 * (2, 'Second Post', 'Another interesting post here!');
	 * 
	 * INSERT INTO comments (id, content, post_id) VALUES
	 * (1, 'Great post!', 1),
	 * (2, 'I learned something new.', 1),
	 * (3, 'This is awesome!', 2);
	 * 
	 */

	@Bean
	public ApplicationRunner runner(PostRepository postRepository) {
		return new ApplicationRunner() {
			@Override
			@Transactional
			public void run(ApplicationArguments args) throws Exception {
				extracted(postRepository);
			}
		};
	}

	private void extracted(PostRepository postRepository) {
		Post post1 = Post.builder().title("First Post").content("This is my first post content.").build();
		Post post2 = Post.builder().title("Second Posts").content("Another interesting post here!").build();
		// Post post3 = Post.builder().title("Third Post")
		// .content("This is my third post content.").build(); 

		// post1.addComment(Comment.builder().content("Great post!").build());
		// post1.addComment(Comment.builder().content("I learned something new.").build());
		// post2.addComment(Comment.builder().content("This is awesome!").build());

		postRepository.saveAll(List.of(post1, post2));
	}

}

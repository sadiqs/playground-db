package io.github.sadiqs.playground.db.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Builder
@AllArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String content;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @Version
    @Column(nullable = false, columnDefinition = "integer DEFAULT 0")
    @Builder.Default
    private int version = 0;

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }
}

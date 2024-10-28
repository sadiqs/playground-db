DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS posts CASCADE;

CREATE TABLE posts (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    title TEXT NOT NULL UNIQUE,
    content TEXT NOT NULL
);

CREATE TABLE comments (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    content TEXT,
    status TEXT,
    post_id UUID NOT NULL,
    FOREIGN KEY (post_id) REFERENCES posts(id)
);

-- INSERT INTO posts (id, title, content) VALUES
-- (1, 'First Post', 'This is my first post content.'),
-- (2, 'Second Post', 'Another interesting post here!');

-- INSERT INTO comments (id, content, post_id) VALUES
-- (1, 'Great post!', 1),
-- (2, 'I learned something new.', 1),
-- (3, 'This is awesome!', 2);

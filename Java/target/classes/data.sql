-- Saving topics
INSERT INTO topics (title, author, message) VALUES ("Topic 1 title", "Topic 1 author", "Topic 1 message");
INSERT INTO topics (title, author, message) VALUES ("Topic 2 title", "Topic 2 author", "Topic 2 message");

-- saving users
INSERT INTO users (user_name, email) VALUES('user1', 'user1@email.es');
INSERT INTO users (user_name, email) VALUES('user2', 'user2@email.es');

-- saving posts
INSERT INTO posts (post, topic_id, user_id) VALUES ("Topic 2 comment 1", 2, 1);
INSERT INTO posts (post, topic_id, user_id) VALUES ("Topic 2 comment 2", 2, 1);

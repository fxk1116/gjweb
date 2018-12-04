INSERT INTO user (id, username, password, name, email,phone) VALUES (1, 'admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '羽过天晴', 'ygtq@haibusiness.com','12345678');
INSERT INTO user (id, username, password, name, email,phone)  VALUES (2, 'test', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '张三', 'zhangsan@haibusiness.com','87654321');

INSERT INTO authority (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, name) VALUES (2, 'ROLE_USER');
INSERT INTO authority (id, name) VALUES (3, 'ROLE_WAITER');


INSERT INTO user_authority (user_id, authority_id) VALUES (1, 1);
INSERT INTO user_authority (user_id, authority_id) VALUES (2, 2);



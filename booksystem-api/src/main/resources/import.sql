INSERT INTO tb_role (authority) VALUES ('ROLE_ADMIN'),('ROLE_USER');
INSERT INTO tb_user (username, password) VALUES ('raphael-admin', '$2y$10$WVbV./nf2AJQ7p55l0r58O83Fgj2XEf9/Tt3xpJA9aVtx/RxTOlbe');
INSERT INTO tb_user_role (user_id, role_id) VALUES (1,1);

INSERT INTO tb_livro (nome, autor, data_cadastro, saved_by_user_id) VALUES ("Design Patterns", "Erich Gamma", "2017-08-01", 1);
INSERT INTO tb_livro (nome, autor, data_cadastro, saved_by_user_id) VALUES ("Working Effectively with Legacy Code", "Michael C. F.", "2018-10-01", 1);
INSERT INTO tb_livro (nome, autor, data_cadastro, saved_by_user_id) VALUES ("Clean Code", "Robert C. Martin", "2019-05-10", 1);
INSERT INTO tb_livro (nome, autor, data_cadastro, saved_by_user_id) VALUES ("Domain Driven Design", "Erich Evans", "2019-05-11", 1);

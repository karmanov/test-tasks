INSERT INTO app_user (id, name, username, password) values (1, 'Admin admin', 'admin', 'admin');
INSERT INTO app_user (id, name, username, password) values (2, 'User user', 'user', 'user');

INSERT INTO app_user_roles (app_user_id, roles) values (1, 'ADMIN');
INSERT INTO app_user_roles (app_user_id, roles) values (2, 'ADMIN');


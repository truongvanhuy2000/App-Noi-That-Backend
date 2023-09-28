INSERT INTO appnoithat.accountinformation (`name`, `gender`, `email`, `address`, `phone`)
VALUES ('John Doe', 'Male', 'john.doe@example.com', '123 Main St, City', '123-456-7890'),
       ('Jane Smith', 'Female', 'jane.smith@example.com', '456 Elm St, Town', '987-654-3210');

-- Account
INSERT INTO appnoithat.account (`username`, `password`, `active`, `info_id`, `enabled`, `expire_date`)
VALUES ('user', '{noop}user', 1, 1, 1, '2023-12-31'),
       ('admin', '{noop}admin', 1, 2, 1, '2300-12-31');

-- Roles
INSERT INTO appnoithat.roles (`account_id`, `role`)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN');
	 



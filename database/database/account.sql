
INSERT INTO appnoithat.accountinformation (`name`, `gender`, `email`, `address`, `phone`)
VALUES
  ('John Doe', 'Male', 'john.doe@example.com', '123 Main St, City', '123-456-7890'),
  ('Jane Smith', 'Female', 'jane.smith@example.com', '456 Elm St, Town', '987-654-3210');

-- Account
INSERT INTO appnoithat.accountEntity (`username`, `password`, `active`, `info_id`, `enabled`)
VALUES
  ('john_doe', '{noop}123456', 1, 1, 1),
  ('admin', '{noop}admin', 1, 2, 1);

-- Roles
INSERT INTO appnoithat.roles (`username`, `role`)
VALUES
  ('john_doe', 'ROLE_USER'),
  ('admin', 'ROLE_ADMIN');
	 



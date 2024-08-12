CREATE TABLE users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    userage INT NOT NULL,
    userpassword VARCHAR(45) NOT NULL,
    userbalance DECIMAL(10, 2) NOT NULL DEFAULT 0.0,
    useremail VARCHAR(100) NOT NULL UNIQUE,
    usercpf VARCHAR(11) NOT NULL UNIQUE
);

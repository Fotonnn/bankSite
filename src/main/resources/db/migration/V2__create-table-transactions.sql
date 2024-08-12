CREATE TABLE transactions (
    transaction_id SERIAL PRIMARY KEY,
    payer_id INT NOT NULL,
    receiver_id INT NOT NULL,
    amount DOUBLE PRECISION NOT NULL,
    FOREIGN KEY (payer_id) REFERENCES users(user_id),
    FOREIGN KEY (receiver_id) REFERENCES users(user_id)
);

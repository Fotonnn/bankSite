create table transactions(

    transaction_id int not null auto_increment,
    payer_id int not null unique,
    receiver_id int not null unique,
    amount double not null,
    foreign key (payer_id) references users(user_id),
    foreign key (receiver_id) references users(user_id),

    primary key(transaction_id)

);
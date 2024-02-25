create table users(

    user_id int not null auto_increment,
    username varchar(100) not null,
    userage int not null,
    userpassword varchar(45) not null,
    userbalance decimal(10, 2) not null default 0.0,
    useremail varchar(100) not null unique,
    usercpf varchar(11) not null unique,

    primary key(user_id)

);
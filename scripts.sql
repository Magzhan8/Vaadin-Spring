insert into role values(1,"user");
insert into role values(2,"admin");

insert into users(login,password,email,image) values("Magzh","12345","magzh@gmail.com","default.jpg");
insert into users(login,password,email,image) values("Steven","12345","steve@gmail.com","default.jpg");

insert into user_role(role_id,user_id) values(2,1);
insert into user_role(role_id,user_id) values(1,2);
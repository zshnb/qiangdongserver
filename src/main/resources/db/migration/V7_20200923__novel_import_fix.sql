alter table novel modify description varchar(5000) not null comment '简介';
alter table novel drop column subscribe_count;
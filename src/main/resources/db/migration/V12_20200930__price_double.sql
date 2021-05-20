alter table novel_chapter modify column price double(11, 1) not null comment '价格';
alter table comic_chapter modify column price double(11, 1) not null comment '价格';
alter table subscribe modify column coin double(11, 1) not null comment '墙币';

alter table topic add column click_count int(11) not null default 0 after reference_count;
alter table topic add column cover varchar(255) not null after name;
alter table topic alter column reference_count set default 0;

create table topic_follow
(
    id        bigint(11) not null,
    user_id   bigint(11) not null,
    topic_id  bigint(11) not null,
    create_at datetime   not null default current_timestamp,
    update_at datetime   not null default current_timestamp,
    primary key (id),
    index idx_topic_id_user_id (topic_id, user_id) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='话题关注';
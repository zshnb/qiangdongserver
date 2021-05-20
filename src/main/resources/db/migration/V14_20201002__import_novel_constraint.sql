create table third_party_works
(
    id                   bigint(20)   not null,
    third_party_works_id varchar(255) not null,
    third_party          tinyint(4)   not null,
    works_id             bigint(20)   not null,
    works_type           bigint(20)   not null,
    create_at            datetime     not null default current_timestamp,
    update_at            datetime     not null default current_timestamp,
    primary key (id),
    index idx_third_party_works_id_works_type (third_party, third_party_works_id, works_type) using btree
)
alter table user add column allow_charge tinyint(1) not null default 0 comment '允许收费' after creator_identity
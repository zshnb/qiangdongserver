alter table topic drop column user_id;
alter table topic add column user_id bigint(20) not null after id;
update topic set user_id = 1;

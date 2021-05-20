drop index idx_deleted_type_id on novel;
create index idx_deleted_author_id_type_id on novel (author_id, deleted, type_id);
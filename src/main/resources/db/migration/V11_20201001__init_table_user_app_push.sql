DROP TABLE IF EXISTS `user_app_push`;
CREATE TABLE `user_app_push` (
  `id`          bigint(20)  NOT NULL,
  `user_id`     bigint(20)  NOT NULL COMMENT '用户id',
  `tag`         varchar(255) DEFAULT NULL COMMENT '标签',
  `client_id`   varchar(255) NOT NULL COMMENT '个推用户唯一标识',
  `create_at`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_at`   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY idx_user_id (user_id) USING BTREE
) ENGINE=InnoDB
  DEFAULT CHARSET=utf8;



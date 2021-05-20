DROP TABLE IF EXISTS novel;
CREATE TABLE novel
(
    id                   bigint(20)   NOT NULL,
    name                 varchar(255) NOT NULL COMMENT '小说名字',
    author_id            bigint(20)   NOT NULL COMMENT '作者',
    type_id              bigint(20)   NOT NULL COMMENT '分类',
    collect_count        int(11)      NOT NULL COMMENT '收藏数',
    recommend_ticket     int(11)      NOT NULL COMMENT '推荐数',
    wall_ticket          int(11)      NOT NULL COMMENT '墙票',
    coin                 int(11)      NOT NULL COMMENT '墙币',
    click_count          int(11)      NOT NULL COMMENT '点击次数',
    word_count           int(11)      NOT NULL COMMENT '字数',
    update_status        tinyint(4)   NOT NULL COMMENT '更新状态',
    contract_status      tinyint(4)   NOT NULL COMMENT '签约状态',
    show_status          tinyint(4)   NOT NULL COMMENT '上架状态',
    authorization_status tinyint(4)   NOT NULL COMMENT '授权类型',
    cover                varchar(255) NOT NULL COMMENT '封面url',
    description          varchar(255) NOT NULL COMMENT '简介',
    foreword             varchar(255) NOT NULL COMMENT '作者寄语',
    topic_id             bigint(20)   NOT NULL COMMENT '所属专题',
    create_at            datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at            datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted              tinyint(4)   NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_deleted_author_id_statuses (deleted, author_id, update_status, contract_status, show_status) USING BTREE,
    KEY idx_deleted_type_id (deleted, type_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS novel_chapter;
CREATE TABLE novel_chapter
(
    id            bigint(20)    NOT NULL,
    title         varchar(255)  NOT NULL COMMENT '章节名',
    novel_id      bigint(20)    NOT NULL COMMENT '小说',
    price         double(11, 1) NOT NULL COMMENT '订阅价格',
    word_count    int(11)       NOT NULL COMMENT '字数',
    progress      double        NOT NULL COMMENT '进度',
    `index`       int(11)       NOT NULL COMMENT '章节序号',
    type          tinyint(11)   NOT NULL COMMENT '类型(公众| vip)',
    review_status tinyint(4)    NOT NULL COMMENT '审核状态',
    author_words  varchar(5000) NOT NULL COMMENT '作者的话',
    txt_url       varchar(255)  NOT NULL COMMENT '章节文本url',
    create_at     datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at     datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted       tinyint(4)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_deleted_novel_id (deleted, novel_id, id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS comic;
CREATE TABLE comic
(
    id                   bigint(20)   NOT NULL,
    name                 varchar(255) NOT NULL COMMENT '漫画名称',
    author_id            bigint(20)   NOT NULL COMMENT '作者',
    type_id              bigint(20)   NOT NULL COMMENT '分类',
    collect_count        int(11)      NOT NULL COMMENT '收藏数',
    recommend_ticket     int(11)      NOT NULL COMMENT '推荐数',
    wall_ticket          int(11)      NOT NULL COMMENT '墙票',
    coin                 int(11)      NOT NULL COMMENT '墙币',
    picture_count        int(11)      NOT NULL COMMENT '图片数',
    click_count          int(11)      NOT NULL COMMENT '点击次数',
    update_status        tinyint(4)   NOT NULL COMMENT '更新状态',
    contract_status      tinyint(4)   NOT NULL COMMENT '签约状态',
    show_status          tinyint(4)   NOT NULL COMMENT '上架状态',
    authorization_status tinyint(4)   NOT NULL COMMENT '授权类型',
    description          varchar(255) NOT NULL COMMENT '简介',
    cover                varchar(255) NOT NULL COMMENT '封面url',
    topic_id             bigint(20)   NOT NULL COMMENT '所属专题',
    create_at            datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at            datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted              tinyint(4)   NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_deleted_author_id_statuses (deleted, author_id, update_status, contract_status, show_status) USING BTREE,
    KEY idx_deleted_type_id (deleted, type_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS comic_chapter;
CREATE TABLE comic_chapter
(
    id            bigint(20)    NOT NULL,
    title         varchar(255)  NOT NULL COMMENT '章节名',
    comic_id      bigint(20)    NOT NULL COMMENT '漫画',
    price         double(11, 1) NOT NULL COMMENT '订阅价格',
    picture_count int(11)       NOT NULL,
    progress      double        NOT NULL COMMENT '进度',
    `index`       int(11)       NOT NULL COMMENT '章节序号',
    type          tinyint(11)   NOT NULL COMMENT '类型(公众| vip)',
    review_status tinyint(4)    NOT NULL COMMENT '审核状态',
    author_words  varchar(5000) NOT NULL COMMENT '作者的话',
    picture_url   varchar(255)  NOT NULL COMMENT '图片url',
    create_at     datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at     datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted       tinyint(4)    NOT NULL DEFAULT 0,
    PRIMARY KEY (id),
    KEY idx_deleted_novel_id (deleted, comic_id, id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;


DROP TABLE IF EXISTS type;
CREATE TABLE type
(
    id        bigint(20)   NOT NULL,
    name      varchar(255) NOT NULL COMMENT '分类名',
    parent_id bigint(20)            DEFAULT NULL COMMENT '父分类id',
    belong    tinyint(4)   NOT NULL COMMENT '分类所属（小说还是漫画）',
    create_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_belong_parent_id (belong, parent_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS comment;
CREATE TABLE comment
(
    id            bigint(20)    NOT NULL,
    user_id       bigint(20)    NOT NULL,
    content       varchar(5000) NOT NULL COMMENT '评论内容',
    type          tinyint(4)    NOT NULL COMMENT '评论类型',
    associate_id  bigint(20)    NOT NULL COMMENT '评论关联id',
    agree_count   int(11)       NOT NULL DEFAULT 0 COMMENT '赞同数',
    against_count int(11)       NOT NULL DEFAULT 0 COMMENT '反对数',
    top           tinyint(4)    NOT NULL DEFAULT 0 COMMENT '置顶',
    images        varchar(5000) NOT NULL COMMENT '图片',
    create_at     datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at     datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_type_associate_user (type, associate_id, user_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS subscribe;
CREATE TABLE subscribe
(
    id             bigint(20) NOT NULL,
    works_id       bigint(20) NOT NULL COMMENT '作品id',
    associate_id   bigint(20) NOT NULL COMMENT '章节id',
    associate_type tinyint(4) NOT NULL COMMENT '类型，漫画or小说',
    user_id        bigint(20) NOT NULL COMMENT '用户',
    coin           int(11)    NOT NULL,
    auto           tinyint(4) NOT NULL COMMENT '自动订阅',
    create_at      datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at      datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_associate_type_works_associate_id (associate_type, works_id, associate_id) USING BTREE,
    KEY idx_user_id (user_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS reward;
CREATE TABLE reward
(
    id         bigint(20)  NOT NULL,
    user_id    bigint(20)  NOT NULL,
    works_id   bigint(20)  NOT NULL,
    works_type tinyint(20) NOT NULL,
    count      int(11)     NOT NULL COMMENT '打赏数量',
    create_at  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at  datetime    NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    KEY idx_works_type_works_id (works_type, works_id) USING BTREE,
    KEY idx_user_id (user_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

DROP TABLE IF EXISTS book_stand;
CREATE TABLE book_stand
(
    id             bigint(20) NOT NULL,
    associate_id   bigint(20) NOT NULL COMMENT '小说ID或者漫画id',
    associate_type tinyint(4) NOT NULL COMMENT '1:小说 /2:漫画',
    user_id        bigint(20) NOT NULL COMMENT '用户ID',
    create_at      datetime DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    update_at      datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id),
    KEY idx_user_type (user_id, associate_type) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='书架记录';

DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id               bigint(20)   NOT NULL COMMENT 'id',
    username         varchar(255) NOT NULL COMMENT '用户名',
    nickname         varchar(255) NULL COMMENT '笔名',
    sex              tinyint(4)   NOT NULL COMMENT '性别',
    level_id         bigint(20)   NOT NULL COMMENT '等级',
    type_id          bigint(20)   NOT NULL COMMENT '编辑所属类别',
    coin             int(11)      NOT NULL COMMENT '墙币',
    recommend_ticket int(11)      NOT NULL COMMENT '推荐票',
    wall_ticket      int(11)      NOT NULL COMMENT '墙票',
    role             int(11)      NOT NULL COMMENT '用户角色',
    creator_identity tinyint(4)   NULL COMMENT '作家标识',
    allow_charge     tinyint(1)   NOT NULL default 0 COMMENT '允许收费',
    user_sign_status tinyint(4)   NOT NULL COMMENT '签约',
    chat_status      tinyint(4)   NOT NULL COMMENT '聊天状态',
    avatar           varchar(255) NOT NULL COMMENT '头像',
    mobile           varchar(255) NOT NULL COMMENT '手机',
    password         varchar(255) NOT NULL COMMENT '密码 - 非明文',
    introduction     varchar(255) NOT NULL COMMENT '个人签名',
    qq_account       varchar(255) NOT NULL COMMENT 'QQ账号',
    email            varchar(255) NOT NULL COMMENT '邮箱',
    auth_name        varchar(255) NULL COMMENT '真实姓名',
    id_card          varchar(255) NULL COMMENT '身份证正面',
    address          varchar(255) NOT NULL COMMENT '地址',
    signature        varchar(255) NOT NULL COMMENT '个性签名',
    birthday         datetime     NOT NULL COMMENT '出生日期',
    secrecy_config   json         NULL COMMENT '隐私设置',
    last_login_time  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最后一次登录时间',
    create_at        datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at        datetime     NULL,
    deleted          tinyint(4)   NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    key idx_deleted_used_id (deleted, id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户表';

DROP TABLE IF EXISTS user_consumption;
CREATE TABLE IF NOT EXISTS user_consumption
(
    id           bigint(20) NOT NULL,
    user_id      bigint(20) NOT NULL COMMENT '用户id',
    associate_id bigint(20) COMMENT '关联id',
    count        int(11)    NOT NULL COMMENT '消费数量',
    type         tinyint    NOT NULL COMMENT '消费类型',
    description  varchar(255) COMMENT '描述',
    create_at    datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '消费时间',
    update_at    datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    deleted      tinyint(4) NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    key idx_user_id_type (user_id, type) USING BTREE,
    key idx_type_associate_id (type, associate_id) USING BTREE,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_credit_record;
CREATE TABLE user_credit_record
(
    id              bigint(20)     NOT NULL,
    user_id         bigint(20)     NOT NULL COMMENT '付费用户',
    price           decimal(10, 2) NOT NULL COMMENT '充值金额',
    coin            int(11)        NOT NULL COMMENT '虚拟货币-墙币',
    transaction_way varchar(255)   NOT NULL COMMENT '第三方充值方法',
    status          tinyint(4)     NOT NULL COMMENT '支付状态',
    order_number    bigint(20)     NOT NULL COMMENT '订单流水',
    description     varchar(255)   NOT NULL COMMENT '充值说明',
    create_at       datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '充值时间',
    update_at       datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    deleted         tinyint(4)     NOT NULL DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    key idx_user_id_way (deleted, user_id, transaction_way) USING BTREE,
    key idx_way_order_number (transaction_way, order_number)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='虚拟货币充值记录';

DROP TABLE IF EXISTS swiper;
CREATE TABLE swiper
(
    id        bigint(20)   NOT NULL,
    item_id   bigint(20)   NOT NULL COMMENT '轮播项目id',
    link      varchar(255) NOT NULL COMMENT '站外链接',
    cover     varchar(255) NOT NULL COMMENT '封面',
    type      tinyint(4)   NOT NULL COMMENT '类型',
    `index`   int(11)      NOT NULL COMMENT '排序',
    link_type tinyint(4)   NOT NULL COMMENT '跳转方式',
    create_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    expire_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '到期时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='轮播图';

DROP TABLE IF EXISTS function_area;
CREATE TABLE function_area
(
    id        bigint(20)   NOT NULL,
    name      varchar(255) NOT NULL COMMENT '名称',
    icon      varchar(255) NOT NULL COMMENT '图标',
    parent_id bigint(20)            DEFAULT NULL COMMENT '父菜单id',
    create_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='金刚区';

DROP TABLE IF EXISTS follow_relation;
CREATE TABLE follow_relation
(
    id          bigint(20) NOT NULL,
    follower_id bigint(20) NOT NULL COMMENT '粉丝id',
    followed_id bigint(20) NOT NULL COMMENT '被关注用户id',
    follow_each tinyint(4) NOT NULL COMMENT '是否互相关注',
    create_at   datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at   datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    key idx_followed_follower (followed_id, follower_id) USING BTREE,
    key idx_follower_id (follower_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='关注表';

DROP TABLE IF EXISTS user_activity;
CREATE TABLE user_activity
(
    id            bigint(20) NOT NULL,
    user_id       bigint(20) NOT NULL COMMENT '用户id',
    activity_data json       NOT NULL COMMENT '动态内容',
    type          tinyint(4) NOT NULL COMMENT '动态类型',
    top           tinyint(4) NOT NULL COMMENT '置顶',
    create_at     datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at     datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    key idx_user_id_type (user_id, type) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户动态表';

DROP TABLE IF EXISTS user_level;
CREATE TABLE IF NOT EXISTS user_level
(
    id         bigint(20)   NOT NULL,
    level      tinyint(4)   NOT NULL COMMENT '等级',
    level_name varchar(255) NOT NULL COMMENT '等级别称',
    create_at  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='作者用户等级';

DROP TABLE IF EXISTS works_tag;
CREATE TABLE IF NOT EXISTS works_tag
(
    id         bigint(20)   NOT NULL,
    works_id   bigint(20)   NOT NULL COMMENT '作品ID',
    works_type tinyint(4)   NOT NULL COMMENT '作品类型',
    tag_name   varchar(255) NOT NULL COMMENT '标签名',
    group_name varchar(255) NOT NULL COMMENT '标签组名',
    create_at  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at  datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    key idx_works_id_type (works_id, works_type) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='作品标签表';

DROP TABLE IF EXISTS user_apply;
CREATE TABLE IF NOT EXISTS user_apply
(
    id           bigint(20)   NOT NULL,
    user_id      bigint(20)   NOT NULL COMMENT '请求人ID',
    apply_type   tinyint(4)   NOT NULL COMMENT '请求类型',
    apply_reason varchar(255) NOT NULL COMMENT '请求理由',
    apply_status tinyint(4)   NOT NULL COMMENT '请求状态',
    works_id     bigint(20)   NOT NULL COMMENT '作品ID',
    works_type   tinyint(4)   NOT NULL COMMENT '作品类型',
    create_at    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='作者用户申请表';

DROP TABLE IF EXISTS works_topic;
CREATE TABLE works_topic
(
    id          BIGINT(20)   NOT NULL,
    type_id     BIGINT(20)   NOT NULL COMMENT '作品分类',
    works_type  tinyint(4)   NOT NULL COMMENT '专题作品类型',
    name        VARCHAR(255) NOT NULL COMMENT '标题名称',
    description VARCHAR(255) NOT NULL COMMENT '专题简介',
    cover       VARCHAR(255) NOT NULL COMMENT '专题封面',
    create_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at   DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    key idx_works_type_type (works_type, type_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='作品主题表';

DROP TABLE IF EXISTS draft;
CREATE TABLE draft
(
    id            bigint(20)    NOT NULL,
    title         varchar(255)  NOT NULL,
    user_id       bigint(20)    NOT NULL COMMENT '用户id',
    works_id      bigint(20)    NOT NULL COMMENT '章节id',
    type          tinyint(4)    NOT NULL COMMENT '作品类型',
    word_count    int(11)       not null default 0,
    picture_count int(11)       not null default 0,
    txt_url       varchar(255)  not null,
    picture_url   varchar(255)  not null,
    author_words  varchar(5000) NOT NULL COMMENT '作者的话',
    create_at     datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_at     datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    key idx_user_id_type_chapter_id (user_id, type, works_id) USING BTREE
) COMMENT ='草稿表';

DROP TABLE IF EXISTS works_faq;
CREATE TABLE works_faq
(
    id          bigint(20)   NOT NULL,
    user_id     bigint(20)   NOT NULL COMMENT '创作者',
    works_id    bigint(20)   NOT NULL COMMENT '作品ID',
    works_type  tinyint(4)   NOT NULL COMMENT '作品类型',
    faq_type    tinyint(4)   NOT NULL COMMENT '问题分类',
    read_status tinyint(4)   NOT NULL COMMENT '已读状态',
    question    varchar(500) NOT NULL COMMENT '问题',
    answer      varchar(500) NOT NULL COMMENT '回复答案',
    create_at   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    update_at   datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    key idx_user_id_type_works_id (user_id, faq_type, works_id) USING BTREE
);

DROP TABLE IF EXISTS work_day_summary;
CREATE TABLE work_day_summary
(
    id          bigint(20) NOT NULL,
    user_id     bigint(20) NOT NULL COMMENT '创作者',
    word_count  int(11)    NOT NULL,
    work_status tinyint(4) NOT NULL,
    create_at   datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    key idx_user_id (user_id) USING BTREE,
    primary key (id)
);

DROP TABLE IF EXISTS notice;
CREATE TABLE notice
(
    id        bigint(20)   NOT NULL COMMENT 'ID',
    user_id   bigint(20)   NOT NULL COMMENT '发布公告的管理员ID',
    title     varchar(255) NOT NULL COMMENT '公告标题',
    subtitle  varchar(255) NOT NULL COMMENT '公告的副标题',
    content   text         NOT NULL COMMENT '公告内容',
    type      tinyint(4)   NOT NULL COMMENT '公告类型。1-app。2-创作者',
    cover     varchar(255) NOT NULL COMMENT '封面图片',
    create_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    deleted   tinyint(4)            DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id),
    key idx_user_id_type (user_id, type) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='公告表';

DROP TABLE IF EXISTS app_info;
CREATE TABLE app_info
(
    id           bigint(20)    NOT NULL,
    version_code int(255)      NOT NULL COMMENT '版本号',
    version_name varchar(255)  NOT NULL COMMENT '版本名称',
    download_url varchar(255)  NOT NULL COMMENT 'apk文件',
    version_info varchar(5000) NOT NULL COMMENT '更新信息',
    type         tinyint       NOT NULL COMMENT '类型',
    platform     tinyint       NOT NULL COMMENT '平台',
    update_type  tinyint       NOT NULL COMMENT '更新类型',
    force_update tinyint       NOT NULL DEFAULT 0 COMMENT '强制更新',
    create_at    datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at    datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    deleted      tinyint(4)             DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='app信息表';

DROP TABLE IF EXISTS help_feedback;
CREATE TABLE help_feedback
(
    id        bigint(20)    NOT NULL COMMENT 'ID',
    user_id   bigint(20)    NOT NULL,
    type      varchar(255)  NOT NULL COMMENT '分类',
    content   varchar(5000) NOT NULL COMMENT '内容',
    answer    varchar(5000) NOT NULL COMMENT '回答',
    images    varchar(1000) NOT NULL,
    create_at datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at datetime   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    deleted   tinyint(4) DEFAULT '0',
    PRIMARY KEY (id),
    index idx_user_id (user_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='帮助与反馈';

DROP TABLE IF EXISTS user_agreement;
CREATE TABLE user_agreement
(
    id        bigint(20)   NOT NULL COMMENT 'ID',
    version   varchar(255) NOT NULL COMMENT '版本名称',
    content   text         NOT NULL COMMENT '内容',
    enabled   tinyint(4)   NOT NULL COMMENT '状态。1-启用 0-未启用',
    type      tinyint(4)   NOT NULL COMMENT '类型。1-用户协议 2-隐私协议',
    create_at datetime   DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at datetime   DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    deleted   tinyint(4) DEFAULT '0' COMMENT '删除标识',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户协议';

DROP TABLE IF EXISTS topic;
CREATE TABLE topic
(
    id              bigint(20)   NOT NULL COMMENT 'ID',
    user_id         bigint(20)   NOT NULL,
    name            varchar(255) NOT NULL COMMENT '名称',
    cover           varchar(255) NOT NULL,
    reference_count int          NOT NULL default 0 comment '引用次数',
    click_count     int          NOT NULL DEFAULT 0,
    create_at       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at       datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='话题';

DROP TABLE IF EXISTS user_activity_topic;
CREATE TABLE user_activity_topic
(
    id               bigint(20) NOT NULL COMMENT 'ID',
    user_activity_id bigint(20) NOT NULL COMMENT 'ID',
    topic_id         bigint(20) NOT NULL COMMENT 'ID',
    create_at        datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at        datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id),
    key idx_user_activity_id (user_activity_id) USING BTREE,
    key idx_topic_id (topic_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='话题';

DROP TABLE IF EXISTS activity_read_history;
CREATE TABLE activity_read_history
(
    id          bigint(20) NOT NULL,
    user_id     bigint(20) NOT NULL COMMENT '用户ID',
    activity_id bigint(20) NOT NULL COMMENT '动态ID',
    create_at   datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '浏览记录',
    update_at   datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改记录',
    PRIMARY KEY (id),
    key idx_user_id (user_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户动态浏览记录';

DROP TABLE IF EXISTS works_read_history;
CREATE TABLE works_read_history
(
    id                      bigint(20) NOT NULL,
    user_id                 bigint(20) NOT NULL COMMENT '用户ID',
    works_id                bigint(20) NOT NULL COMMENT '作品ID',
    works_type              bigint(20) NOT NULL COMMENT '作品类型',
    last_read_chapter_index int(11)    NOT NULL COMMENT '上次阅读章节下标',
    create_at               datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '',
    update_at               datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '',
    key idx_user_id_works_type (user_id, works_type) USING BTREE,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS user_prefer_type;
CREATE TABLE user_prefer_type
(
    id        bigint(20) NOT NULL,
    user_id   bigint(20) NOT NULL COMMENT '用户ID',
    type_id   bigint(20) NOT NULL COMMENT '分类ID',
    create_at datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (id),
    key idx_user_id (user_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户阅读喜好';

DROP TABLE IF EXISTS user_activity_collection;
CREATE TABLE user_activity_collection
(
    id               bigint(20) NOT NULL,
    user_id          bigint(20) NOT NULL COMMENT '收藏用户',
    user_activity_id bigint(20) NOT NULL COMMENT '收藏的用户动态',
    create_at        datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (id),
    key idx_user_id_activity_id (user_id, user_activity_id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='用户动态收藏';

DROP TABLE IF EXISTS block_user;
CREATE TABLE block_user
(
    id             bigint(20) NOT NULL,
    user_id        bigint(20) NOT NULL COMMENT '收藏用户',
    target_user_id bigint(20) NOT NULL COMMENT '收藏的用户动态',
    create_at      datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '收藏时间',
    PRIMARY KEY (id),
    key idx_user_id (user_id) USING BTREE
);

DROP TABLE IF EXISTS fate_board;
CREATE TABLE fate_board
(
    id        bigint(20)    NOT NULL,
    user_id   bigint(20)    NOT NULL COMMENT '收藏用户',
    status    tinyint       NOT NULL COMMENT '挂起状态',
    content   varchar(255),
    goods_id  bigint(20)    NOT NULL COMMENT '商品id',
    interest  varchar(5000) NOT NULL,
    match_sex tinyint       NOT NULL COMMENT '匹配性别',
    create_at datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at datetime      NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    primary key (id),
    key idx_user_id (user_id) USING BTREE
) comment '三生墙-缘签';

DROP TABLE IF EXISTS message;
CREATE TABLE message
(
    id           bigint(20) NOT NULL,
    user_id      bigint(20) NOT NULL,
    associate_id bigint(20) NOT NULL COMMENT '关联id',
    type         tinyint    NOT NULL COMMENT '消息类别',
    read_status  tinyint    NOT NULL COMMENT '已读状态',
    create_at    datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at    datetime   NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
    primary key (id),
    key idx_user_id_associate_type (user_id, type) USING BTREE,
    key idx_associate_id (associate_id) USING BTREE
) comment '消息';

DROP TABLE IF EXISTS user_login_count;
CREATE TABLE user_login_count
(
    id        BIGINT   NOT NULL,
    count     INT      NOT NULL COMMENT '统计数',
    create_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间/登陆时间',
    primary key (id)
) comment '每日登录数';

DROP TABLE IF EXISTS menu;
CREATE TABLE menu
(
    id        bigint(20)   NOT NULL,
    parent_id bigint(20)   NOT NULL COMMENT '父菜单ID，一级菜单为0',
    name      varchar(255) NOT NULL COMMENT '菜单名称',
    url       varchar(255) NOT NULL COMMENT '菜单URL',
    perms     varchar(255) NOT NULL COMMENT '授权(多个用逗号分隔，如：user:list,user:create)',
    type      tinyint(11)  NOT NULL COMMENT '类型   0：目录   1：菜单   2：按钮',
    role      tinyint(11)  NOT NULL COMMENT '归属角色类型   2：作者   3：编辑  4：管理员',
    icon      varchar(255) NOT NULL COMMENT '菜单图标',
    order_num int(11)      NOT NULL COMMENT '排序',
    create_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '菜单管理';

DROP TABLE IF EXISTS role;
CREATE TABLE role
(
    id        bigint(20)   NOT NULL,
    role_name varchar(255) NOT NULL COMMENT '角色名称',
    remark    varchar(255) NOT NULL COMMENT '备注',
    create_at datetime     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at datetime     NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (id) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT = '角色';

DROP TABLE IF EXISTS goods;
CREATE TABLE goods
(
    id        bigint(20) NOT NULL,
    type      tinyint    NOT NULL COMMENT '类型',
    resource  json       NOT NULL COMMENT '商品资源，图片之类',
    coin      int(11)    NOT NULL COMMENT '价格/墙币',
    create_at datetime   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at datetime   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    primary key (id),
    index idx_type (type) USING BTREE
);

DROP TABLE IF EXISTS user_goods;
CREATE TABLE user_goods
(
    id        bigint(20) NOT NULL,
    user_id   bigint(20) NOT NULL,
    goods_id  bigint(20) NOT NULL,
    create_at datetime   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at datetime   NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
    key idx_user_id (user_id) USING BTREE,
    primary key (id)
);

DROP TABLE IF EXISTS user_chat;
CREATE TABLE user_chat
(
    id           bigint(20)   NOT NULL COMMENT '消息ID',
    user_id      bigint(20)   NOT NULL COMMENT '用户ID',
    chat_user_id bigint(20)   NOT NULL COMMENT '通信方用户ID',
    sender       bigint(20)   NOT NULL COMMENT '信息发起人ID',
    type         tinyint(4)   NOT NULL COMMENT '消息类型',
    message      varchar(255) NOT NULL COMMENT '消息内容',
    top          tinyint(4)   NOT NULL COMMENT '是否置顶',
    username     varchar(255) NOT NULL COMMENT '备注昵称',
    chat_tag     tinyint(4)   NOT NULL COMMENT '标签',
    read_status  tinyint(4)   NOT NULL COMMENT '消息是否已读',
    status       tinyint(4)   NOT NULL COMMENT '发送状态',
    create_at    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_at    datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    deleted      tinyint(4)   Not null DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (id) USING BTREE,
    KEY idx_user_id_chat_user_id (user_id, chat_user_id) USING BTREE
) ENGINE = InnoDB COMMENT ='用户聊天信息表';

DROP TABLE IF EXISTS `user_hobby`;
CREATE TABLE `user_hobby`
(
    id        bigint(20)   NOT NULL,
    user_id   bigint(20)   NOT NULL,
    name      varchar(255) NOT NULL COMMENT '兴趣爱好',
    type      tinyint(4)   NOT NULL COMMENT '兴趣爱好分类',
    create_at datetime     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    KEY idx_user_id (user_id) USING BTREE,
    PRIMARY KEY (`id`) USING BTREE
) comment = '用户爱好兴趣表';

DROP TABLE IF EXISTS withdraw_record;
CREATE TABLE withdraw_record
(
    id        bigint(20)     NOT NULL,
    user_id   bigint(20)     NOT NULL,
    money     decimal(10, 2) NOT NULL,
    create_at datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '充值时间',
    update_at datetime       NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    deleted   tinyint        NOT NULL DEFAULT 0,
    primary key (id),
    key deleted_user_id (deleted, user_id) USING BTREE
);

drop table if exists topic_follow;
create table topic_follow
(
    id        bigint(11) not null,
    user_id   bigint(11) not null,
    topic_id  bigint(11) not null,
    create_at datetime   not null default current_timestamp,
    update_at datetime   not null default current_timestamp,
    primary key (id),
    index idx_topic_id (topic_id) using btree
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4 COMMENT ='话题关注';

drop table if exists third_party_works;
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
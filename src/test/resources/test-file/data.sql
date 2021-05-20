insert into user(id, username, nickname, avatar, mobile, password, introduction, coin, create_at, last_login_time, update_at, role, qq_account, email, auth_name, id_card, user_sign_status, sex, address, level_id, deleted, signature, recommend_ticket, wall_ticket, type_id, birthday, secrecy_config, chat_status)
    values (1, 'user1', 'author 1', '', '13715166405', 'd41d8cd98f00b204e9800998ecf8427e', '', 20000, '2020-02-20 00:00:00', '2020-02-20 00:00:00', '2020-02-02 00:00:00', 2, '', '', '', '', 0, 0, '',  1, 0, '', 1000, 10, 0, '2020-02-20 00:00:00', '{}', 1);
insert into user(id, username, nickname, avatar, mobile, password, introduction, coin, create_at, last_login_time, role, qq_account, email, auth_name, id_card, user_sign_status, sex, address, level_id, deleted, signature, recommend_ticket, wall_ticket, type_id, birthday, secrecy_config, chat_status)
    values (2, 'user2', 'author 2', '13715166404', '', 'user2', '', 20000, '2020-02-20 00:00:00', '2020-02-20 00:00:00', 1, '', '', '', '', 0, 1, '', 1, 0, '', 0, 0, 0, '2020-02-20 00:00:00', '{}', 1);
insert into user(id, username, nickname, avatar, mobile, password, introduction, coin, create_at, last_login_time, role, qq_account, email, auth_name, id_card, user_sign_status, sex, address, level_id, deleted, signature, recommend_ticket, wall_ticket, type_id, birthday, secrecy_config, chat_status)
    values (3, 'user3', '', '', '13715166407', 'e10adc3949ba59abbe56e057f20f883e', '', 0, '2020-06-08 22:35:07', '2020-06-08 22:35:07', 2, '', '', '', '', 0, 1, '', 1, 0, '', 0, 0, 0, '2020-02-20 00:00:00', '{}', 1);
insert into user(id, username, nickname, avatar, mobile, password, introduction, coin, create_at, last_login_time, role, qq_account, email, auth_name, id_card, user_sign_status, sex, address, level_id, deleted, signature, recommend_ticket, wall_ticket, type_id, birthday, secrecy_config, chat_status)
    values (4, 'QD011', '无缺', '', '13715166403', '', '', 0, '2020-06-16 15:22:14', '2020-06-16 15:22:14', 3, '1409482236', '18944199202@163.com', '周华', '', 0, 1, '', 0, 0, '', 0, 0, 3, '2000-02-20 00:00:00', '{}', 1);
insert into user(id, username, nickname, avatar, mobile, password, introduction, coin, create_at, last_login_time, update_at, role, qq_account, email, auth_name, id_card, user_sign_status, sex, address, level_id, deleted, signature, recommend_ticket, wall_ticket, type_id, birthday, secrecy_config, chat_status)
    values (5, 'admin', '', '', '13715166406', '', '', 0, '2020-06-16 15:22:14', '2020-06-16 15:22:14', '2020-02-02 00:00:00', 4, '1409482236', '18944199202@163.com', '周华', '', 0, 1, '', 0, 0, '', 0, 0, 1, '2000-02-20 00:00:00', '{}', 1);
insert into novel(id, cover, author_id, word_count, update_status, contract_status, show_status, create_at, update_at, deleted, name, collect_count, recommend_ticket, wall_ticket, coin, type_id, click_count, description, foreword, authorization_status, topic_id)
    values (1, '', 1, 200000, 1, 1, 1, '2020-02-02 00:00:00', '2020-02-21 00:00:00', 0, 'novel1', 0, 0, 0, 0, 3, 0, '', '', 1, 1);
insert into novel(id, cover, author_id, word_count, update_status, contract_status, show_status, create_at, update_at, deleted, name, collect_count, recommend_ticket, wall_ticket, coin, type_id, click_count, description, foreword, authorization_status, topic_id)
    values (2, '', 1, 1, 1, 1, 1, '2020-02-02 00:00:00', '2020-02-20 00:00:00', 0, 'novel2', 0, 0, 0, 0, 3, 0, '', '', 1, 1);
insert into novel_chapter(id, novel_id, title, word_count, txt_url, progress, create_at, update_at, deleted, `index`, type, review_status, price, author_words)
    values (1, 1, 'novel chapter 1', 70000, '', 0.0, '2020-02-20 00:00:00', '2020-02-20 00:00:00', 0, 1, 2, 2, 10.0, '');
insert into novel_chapter(id, title, novel_id, price, word_count, progress, `index`, type, review_status, author_words, txt_url)
    values (2, 'novel chapter 2', 1, 10.0, 10, 0.0, 2, 1, 0, '', '');
insert into novel_chapter(id, title, novel_id, price, word_count, progress, `index`, type, review_status, author_words, txt_url)
    values (3, 'novel chapter 1', 2, 10.0, 10, 0.0, 1, 1, 0, '', '');
insert into novel_chapter(id, title, novel_id, price, word_count, progress, `index`, type, review_status, author_words, txt_url)
    values (4, 'novel chapter 2', 2, 10.0, 10, 0.0, 2, 1, 0, '', '');
insert into comic(id, name, cover, author_id, update_status, contract_status, show_status, create_at, update_at, deleted, collect_count, recommend_ticket, wall_ticket, coin,  type_id, click_count, description, authorization_status, topic_id, picture_count)
    values (1, 'comic1', '', 1, 1, 1, 1, '2020-02-02 00:00:00', '2020-02-21 00:00:00', 0, 0, 0, 0, 0, 3, 0, '', 1, 1, 1);
insert into comic(id, name, cover, author_id, update_status, contract_status, show_status, create_at, update_at, deleted, collect_count, recommend_ticket, wall_ticket, coin, type_id, click_count, description, authorization_status, topic_id, picture_count)
    values (2, 'comic2', '', 1, 1, 1, 1, '2020-02-02 00:00:00', '2020-02-20 00:00:00', 0, 0, 0, 0, 0, 3, 0, '', 1, 1, 1);
insert into comic_chapter(id, comic_id, title, progress, picture_url, `index`, type, review_status, price, picture_count, author_words)
    values (1, 1, 'comic chapter 1-1', 0.0, '',  1, 2, 2, 10.0, 1, '');
insert into comic_chapter(id, comic_id, title, progress, picture_url, `index`, type, review_status, price, picture_count, author_words)
    values (2, 1, 'comic chapter 1-2', 0.0, '',  2, 1, 2, 10.0, 1, '');
insert into comic_chapter(id, comic_id, title, progress, picture_url, `index`, type, review_status, price, picture_count, author_words)
    values (3, 2, 'comic chapter 2-1', 0.0, '',  1, 1, 0, 10.0, 1, '');
insert into comic_chapter(id, comic_id, title, progress, picture_url, `index`, type, review_status, price, picture_count, author_words)
    values (4, 2, 'comic chapter 2-2', 0.0, '',  2, 1, 0, 10.0, 1, '');
insert into type values (1, '分类1', 0, 2, '2020-02-20 00:00:00', '2020-02-20 00:00:00');
insert into type values (2, '分类2', 0, 2, '2020-02-20 00:00:00', '2020-02-20 00:00:00');
insert into type values (3, '分类1-novel', 1, 2, '2020-02-20 00:00:00', '2020-02-20 00:00:00');
insert into type values (4, '分类2-novel', 2, 2, '2020-02-20 00:00:00', '2020-02-20 00:00:00');
insert into type(id, name, parent_id, belong, create_at, update_at) values (5, '分类2-novel-1', 4, 2, '2020-02-20 00:00:00', '2020-02-20 00:00:00');
insert into type(id, name, parent_id, belong, create_at, update_at) values (6, '分类1-comic', 0, 1, '2020-02-20 00:00:00', '2020-02-20 00:00:00');
insert into type(id, name, parent_id, belong, create_at, update_at) values (7, '分类2-comic', 0, 1, '2020-02-20 00:00:00', '2020-02-20 00:00:00');
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top) values (1, 1, 'content', 1, 1, 1, 1, '', 0);
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top, create_at) values (11, 1, 'top content', 3, 1, 1, 1, '', 1, '2020-06-20 00:00:00');
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top, create_at) values (12, 1, 'top content', 3, 1, 1, 1, '', 1, '2020-02-02 00:00:00');
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top) values (2, 1, 'content', 1, 2, 1, 1, '', 0);
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top) values (3, 1, 'content', 3, 1, 1, 1, '', 0);
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top) values (4, 1, 'content', 3, 2, 1, 1, '', 0);
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top) values (5, 1, 'content', 2, 1, 1, 1, '', 0);
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top) values (6, 1, 'content', 2, 2, 1, 1, '', 0);
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top) values (7, 1, 'content', 4, 1, 1, 1, '', 0);
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top) values (8, 1, 'content', 4, 2, 1, 1, '', 0);
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top) VALUES (9, 1, 'content', 6, 1, 1, 1, '', 0);
insert into comment(id, user_id, content, type, associate_id, agree_count, against_count, images, top) VALUES (10, 1, 'content', 5, 9, 1, 1, '', 0);
insert into function_area(id, name, icon, parent_id, create_at, update_at) values (1, '功能1', '', null, '2020-02-20 00:00:00', '2020-02-20 00:00:00');
insert into function_area(id, name, icon, parent_id, create_at, update_at) values (2, '功能2', '', null, '2020-02-20 00:00:00', '2020-02-20 00:00:00');
insert into function_area(id, name, icon, parent_id, create_at, update_at) values (3, '功能1-1', '', 1, '2020-02-20 00:00:00', '2020-02-20 00:00:00');
insert into swiper(id, item_id, link, cover, type, `index`, link_type) values (1, 1, '', '', 1, 2, 1);
insert into swiper(id, item_id, link, cover, type, `index`, link_type) values (2, 2, '', '', 1, 2, 1);
insert into swiper(id, item_id, link, cover, type, `index`, link_type) values (3, 1, '', '', 2, 2, 1);
insert into swiper(id, item_id, link, cover, type, `index`, link_type) values (4, 0, '', '', 2, 2, 2);
insert into user_level values (1, 1, 'LV1', '2020-06-17 11:17:49', '2020-06-17 11:17:49');
insert into user_level values (2, 2, 'LV2', '2020-06-17 11:17:53', '2020-06-17 11:17:53');
insert into user_level values (3, 3, 'LV3', '2020-06-17 11:18:03', '2020-06-17 11:18:03');
insert into user_level values (4, 4, '白银', '2020-06-17 11:18:14', '2020-06-17 11:18:14');
insert into user_level values (5, 5, '黄金', '2020-06-17 11:18:22', '2020-06-17 11:18:22');
insert into user_level values (6, 6, '大神', '2020-06-17 11:18:35', '2020-06-17 11:18:35');
insert into works_tag (id, works_id, works_type, tag_name, group_name, create_at, update_at) values (1,1,2,'冒险', '风格',  '2020-06-16 15:22:14', '2020-06-16 15:22:14');
insert into works_tag (id, works_id, works_type, tag_name, group_name, create_at, update_at) values (2,1,2,'学院', '故事流派', '2020-06-16 15:22:34', '2020-06-16 15:22:34');
insert into works_tag (id, works_id, works_type, tag_name, group_name, create_at, update_at) values (3,1,2,'热血', '故事流派', '2020-06-16 15:22:50', '2020-06-16 15:22:50');
insert into works_tag (id, works_id, works_type, tag_name, group_name, create_at, update_at) values (4,2,2,'仙侠', '故事流派', '2020-06-16 15:23:06', '2020-06-16 15:23:06');
insert into user_apply values (1,1,1,'apply',1,0,0,'2020-06-16 19:38:03','2020-06-16 19:38:03');
insert into works_topic values (1, 2, 2, 'new Topic', 'this is topic', '', '2020-06-24 10:40:35', '2020-06-24 10:40:35');
insert into works_topic values (2, 2, 2, '小说专题', '值得一看', '', '2020-07-24 10:40:35', '2020-06-24 10:40:35');
insert into user_activity(id, user_id, activity_data, type, top) values (1, 1, '{"createActivity":{"content":"content1","shareCount":1,"commentCount":3,"agreeCount":5,"againstCount":5, "images": ["image"]}}', 1, 1);
insert into user_activity(id, user_id, activity_data, type, top) values (2, 1, '{"createActivity":{"content":"content2","shareCount":2,"commentCount":6,"agreeCount":3,"againstCount":7}}', 1, 1);
insert into user_activity(id, user_id, activity_data, type, top) values (3, 2, '{"createActivity":{"content":"content3","shareCount":0,"commentCount":0,"agreeCount":0,"againstCount":0}}', 1, 1);
insert into follow_relation(id, follower_id, followed_id, follow_each) values (1, 1, 2, 1);
insert into follow_relation(id, follower_id, followed_id, follow_each) values (2, 2, 1, 1);
insert into draft(id, title, user_id, works_id, type, txt_url, picture_url, author_words) VALUES (1, 'draft title', 1, 1, 1, '', '', '');
insert into works_faq(id, user_id, works_id, works_type, faq_type, read_status, question, answer) values (1, 1, 1, 2, 1, 1, 'xxx', '');
insert into works_faq(id, user_id, works_id, works_type, faq_type, read_status, question, answer) values (2, 1, 2, 2, 1, 1, 'xxxx', '');
insert into works_faq(id, user_id, works_id, works_type, faq_type, read_status, question, answer) values (3, 1, 1, 2, 4, 0, 'xxx', 'Hi,BOY');
insert into book_stand(id, associate_id, associate_type, user_id, create_at, update_at) values (1, 1, 2, 1, '2020-06-16 15:22:14', '2020-06-16 15:22:14');
insert into book_stand(id, associate_id, associate_type, user_id, create_at, update_at) values (2, 1, 1, 1, '2020-06-16 15:22:14', '2020-06-16 15:22:14');
INSERT INTO notice (id, title, subtitle, content, type, cover, create_at, update_at, user_id, deleted) VALUES(1,'test Title','test subTitle','test content',2,'test imgUrl','2020-07-06 18:14:33','2020-07-06 18:14:33',10,0);
INSERT INTO notice (id, title, subtitle,content, type, cover, create_at, update_at, user_id, deleted) VALUES(2,'test Title2','test subTitle2','test content2',1,'test imgUrl2','2020-07-06 18:14:33','2020-07-06 18:14:33',10,0);
INSERT INTO app_info (id, version_code, version_name, download_url, version_info, type, platform, update_type)VALUES (1, 1, 'test name1', 'test url1', 'test content1', 1, 2, 2);
INSERT INTO app_info (id, version_code, version_name, download_url, version_info, type, platform, update_type, create_at)VALUES (2, 1, 'test name2', 'test url2', 'test content2', 1, 2, 2, '2020-02-20 00:00:00');
INSERT INTO help_feedback (id, content, answer, type, user_id, images) VALUES (1,'content','test content1', 'answer', 1, '');
INSERT INTO help_feedback (id, content, answer, type, user_id, images) VALUES (2,'content','test content2', 'answer', 2, '');
INSERT INTO user_agreement(id, version, content, enabled, type)VALUES (1, 'test name1', 'test content1', 0, 1);
INSERT INTO user_agreement(id, version, content, enabled, type, update_at)VALUES (2, 'test name2', 'test content2', 1, 2, '2020-01-01 00:00:00');
insert into topic(id, user_id, name, reference_count, cover) VALUES (1, 2, 'topic1', 0, '');
insert into topic(id, user_id, name, reference_count, cover) VALUES (2, 2, 'topic2', 0, '');
insert into topic(id, user_id, name, reference_count, cover) VALUES (3, 2, 'topic3', 0, '');
INSERT INTO activity_read_history(id, user_id, activity_id) VALUES (1, 1, 1);
INSERT INTO activity_read_history(id, user_id, activity_id) VALUES (2, 4, 1);
insert into works_read_history(id, user_id, works_id, works_type, last_read_chapter_index) VALUES (1, 1, 1, 2, 1);
insert into works_read_history(id, user_id, works_id, works_type, last_read_chapter_index, update_at) VALUES (2, 1, 2, 2, 1, '2020-02-02 00:00:00');
insert into works_read_history(id, user_id, works_id, works_type, last_read_chapter_index) VALUES (3, 2, 1, 2, 1);
insert into works_read_history(id, user_id, works_id, works_type, last_read_chapter_index) VALUES (4, 1, 1, 1, 1);
INSERT INTO user_prefer_type VALUES (1, 1, 1, '2020-07-17 15:23:55', '2020-07-17 15:23:55');
INSERT INTO user_prefer_type VALUES (2, 1, 2, '2020-07-17 15:25:55', '2020-07-18 15:25:55');
insert into user_consumption(id, description, user_id, associate_id, count, type, create_at, update_at) VALUES (1, '', 1, 1, 10, 1, '2020-07-17 15:25:55', '2020-07-18 15:25:55');
insert into user_consumption(id, description, user_id, associate_id, count, type, create_at, update_at) VALUES (2, '', 2, 1, 20, 1, '2020-07-17 15:25:55', '2020-07-18 15:25:55');
INSERT INTO user_credit_record (id, description, price, coin, user_id, order_number, transaction_way, create_at, update_at, status, deleted) VALUES (1, '', 10.00, 10, 1, 1, '1', '2020-07-15 17:40:21', '2020-07-15 17:40:21', 1, 0);
INSERT INTO user_credit_record (id, description, price, coin, user_id, order_number, transaction_way, create_at, update_at, status, deleted) VALUES (2, '', 10.00, 10, 1, 2, '1', '2020-07-15 17:40:49', '2020-07-15 17:40:49', 1, 0);
INSERT INTO user_credit_record (id, description, price, coin, user_id, order_number, transaction_way, create_at, update_at, status, deleted) VALUES (3, '', 50.00, 50, 2, 3, '1', '2020-07-15 17:41:07', '2020-07-15 17:41:07', 1, 0);
INSERT INTO user_credit_record (id, description, price, coin, user_id, order_number, transaction_way, create_at, update_at, status, deleted) VALUES (4, '', 20.00, 20, 3, 4, '1', '2020-07-14 17:41:23', '2020-07-22 11:49:37', 1, 0);
INSERT INTO user_credit_record (id, description, price, coin, user_id, order_number, transaction_way, create_at, update_at, status, deleted) VALUES (5, '', 30.00, 30, 1, 5, '1', '2020-07-15 17:41:46', '2020-07-15 17:41:49', 0, 0);
INSERT INTO user_activity_collection(id, user_id, user_activity_id) VALUES (1, 2, 1);
INSERT INTO user_activity_collection(id, user_id, user_activity_id) VALUES (2, 2, 2);
insert into block_user(id, user_id, target_user_id, create_at) VALUES (1, 1, 2, '2020-07-18 15:25:55');
insert into fate_board (id, user_id, status, content, interest, match_sex, goods_id) values (1, 1, 1, 'content', '["aa", "bb"]', 1, 1);
insert into fate_board (id, user_id, status, content, interest, match_sex, goods_id) values (2, 2, 1, 'content', '["aa", "bb"]', 1, 1);
insert into message(id, user_id, associate_id, type, read_status) VALUES (1, 2, 1, 1, 2);
insert into message(id, user_id, associate_id, type, read_status) VALUES (2, 2, 1, 2, 2);
insert into message(id, user_id, associate_id, type, read_status) VALUES (3, 2, 1, 3, 2);
insert into message(id, user_id, associate_id, type, read_status) VALUES (4, 2, 1, 4, 2);
INSERT INTO subscribe(id, associate_id, works_id, associate_type, user_id, create_at, update_at, auto, coin) VALUES (1, 1, 1, 2, 1, '2020-07-29 10:38:36', '2020-07-29 10:38:36', 0, 3);
INSERT INTO subscribe(id, associate_id, works_id, associate_type, user_id, create_at, update_at, auto, coin) VALUES (2, 4, 2, 2, 1, '2020-07-29 10:38:46', '2020-07-29 10:49:02', 0, 3);
INSERT INTO subscribe(id, associate_id, works_id, associate_type, user_id, create_at, update_at, auto, coin) VALUES (3, 3, 2, 2, 1, '2020-07-29 10:43:05', '2020-07-29 10:48:58', 0, 5);
INSERT INTO subscribe(id, associate_id, works_id, associate_type, user_id, create_at, update_at, auto, coin) VALUES (4, 3, 2, 2, 2, '2020-07-29 10:49:34', '2020-07-29 10:49:34', 0, 5);
insert into user_login_count(id, count, create_at) values (1, 1, '2020-07-18 15:25:55');
insert into user_login_count(id, count, create_at) values (2, 1, '2020-06-18 15:25:55');
insert into user_login_count(id, count, create_at) values (3, 1, '2020-05-18 15:25:55');
insert into user_login_count(id, count, create_at) values (4, 1, '2020-09-18 15:25:55');
insert into user_login_count(id, count, create_at) values (5, 1, '2020-09-19 15:25:55');
insert into goods(id, type, resource, coin) VALUES (1, 1, '{"fateBoardGoods": {"upAppearance": "aa","downAppearance": "bb", "name": "board1"}}', 0.0);
insert into goods(id, type, resource, coin) VALUES (2, 1, '{"fateBoardGoods": {"upAppearance": "aa","downAppearance": "bb", "name": "board2"}}', 20.0);
insert into user_goods(id, user_id, goods_id) VALUES (1, 1, 2);
INSERT INTO reward(id, user_id, count, create_at, update_at, works_id, works_type) VALUES (1, 3, 100, '2020-07-29 06:34:22', '2020-07-29 19:34:22', 1, 2);
INSERT INTO user_chat(id, user_id, chat_user_id, sender, type, message, top, username, chat_tag, create_at, update_at, read_status, status, deleted) VALUES (1, 1, 2, 1, 0, '你好', 1, 'xxx', 1, '2020-08-10 02:43:55', '0000-00-00 00:00:00', 2, 1, 0);
INSERT INTO user_chat(id, user_id, chat_user_id, sender, type, message, top, username, chat_tag, create_at, update_at, read_status, status, deleted) VALUES (2, 2, 1, 1, 0, '你好', 1, 'xxx', 1, '2020-08-10 02:44:12', '0000-00-00 00:00:00', 1, 1, 0);
INSERT INTO user_chat(id, user_id, chat_user_id, sender, type, message, top, username, chat_tag, create_at, update_at, read_status, status, deleted) VALUES (3, 1, 3, 1, 0, 'Hi', 1, 'xxx', 1, '2020-08-10 03:01:46', '0000-00-00 00:00:00', 2, 1, 0);
INSERT INTO user_chat(id, user_id, chat_user_id, sender, type, message, top, username, chat_tag, create_at, update_at, read_status, status, deleted) VALUES (4, 3, 1, 1, 0, 'Hi', 1, 'xxx', 1, '2020-08-10 03:01:43', '0000-00-00 00:00:00', 1, 1, 0);
INSERT INTO user_chat(id, user_id, chat_user_id, sender, type, message, top, username, chat_tag, create_at, update_at, read_status, status, deleted) VALUES (5, 2, 1, 2, 0, 'HI，很高兴认识你', 1, 'xxx', 1, '2020-08-10 02:55:58', '2020-08-10 02:56:00', 2, 1, 0);
INSERT INTO user_chat(id, user_id, chat_user_id, sender, type, message, top, username, chat_tag, create_at, update_at, read_status, status, deleted) VALUES (6, 1, 2, 2, 0, 'HI，很高兴认识你', 1, 'xxx', 1, '2020-08-10 02:56:16', '2020-08-10 02:56:21', 1, 1, 0);
INSERT INTO user_chat(id, user_id, chat_user_id, sender, type, message, top, username, chat_tag, create_at, update_at, read_status, status, deleted) VALUES (7, 2, 1, 2, 0, '理理我', 1, 'xxx', 1, '2020-08-10 03:09:36', '2020-08-10 03:09:36', 2, 1, 0);
INSERT INTO user_chat(id, user_id, chat_user_id, sender, type, message, top, username, chat_tag, create_at, update_at, read_status, status, deleted) VALUES (8, 1, 2, 2, 0, '理理我', 1, 'xxx', 1, '2020-08-10 03:09:36', '2020-08-10 03:09:36', 1, 1, 0);
INSERT INTO user_chat(id, user_id, chat_user_id, sender, type, message, top, username, chat_tag, create_at, update_at, read_status, status, deleted) VALUES (9, 3, 1, 2, 0, 'hellow', 1, 'xxx', 1,'2020-08-10 03:10:18', '2020-08-10 03:10:18', 2, 1, 0);
INSERT INTO user_chat(id, user_id, chat_user_id, sender, type, message, top, username, chat_tag, create_at, update_at, read_status, status, deleted) VALUES (10, 1, 3, 2, 0, 'hellow', 1, 'xxx', 1, '2020-08-10 03:10:18', '2020-08-10 03:10:18', 1, 1, 0);
INSERT INTO user_hobby(id, user_id, name, type, create_at) VALUES (1, 1, '篮球', 3, '2020-08-17 18:26:02');
INSERT INTO user_hobby(id, user_id, name, type, create_at) VALUES (2, 1, '美食', 3, '2020-08-17 18:27:02');
insert into withdraw_record(id, user_id, money) VALUES (1, 1, 1.00);
insert into withdraw_record(id, user_id, money) VALUES (2, 3, 2.00);
insert into user_activity_topic(id, user_activity_id, topic_id) VALUES (1, 1, 1);
insert into user_activity_topic(id, user_activity_id, topic_id) VALUES (2, 2, 1);
insert into user_activity_topic(id, user_activity_id, topic_id) VALUES (3, 1, 2);

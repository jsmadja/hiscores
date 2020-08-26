insert into player (id, name, shmup_user_id, twitter, vip, hide_medals, created_at, updated_at, comments)
values (1, 'anzymus', 33489, '@jsmadja', 1, 0, '2013-11-29 19:13:32', '2020-08-01 17:33:53', null);

insert into game (id, cover, cover_backup, title, thread, general_ranking, shmup_name, created_at, updated_at)
values (1, '/covers/172.jpg', '/assets/images/cover/STRIKERS+1945+PLUS+1.jpg', 'Strikers 1945 PLUS',
        'http://forum.shmup.com/viewtopic.php?t=8620&f=20', false, 'Strikers 1945 Plus', '2014-08-15 12:14:58',
        '2014-08-15 12:14:58');
insert into platform (id, name, game_id, created_at, updated_at)
values (1, 'NG', 1, '2014-08-15 12:14:58', '2014-08-15 12:14:58');
insert into platform (id, name, game_id, created_at, updated_at)
values (2, 'PCB', 1, '2013-11-29 19:12:15', '2013-11-29 19:12:15');
insert into platform (id, name, game_id, created_at, updated_at)
values (3, 'X360', 1, '2013-11-29 19:12:15', '2013-11-29 19:12:15');
insert into mode (id, name, sort_order, game_id, score_type, created_at, updated_at)
values (2, 'Black Label', 20, 1, '', '2013-11-29 19:12:15', '2013-11-29 19:12:15');
insert into difficulty (id, name, sort_order, game_id, created_at, updated_at)
values (4, 'Original', 1, 1, '2013-11-29 19:12:15', '2013-11-29 19:12:15');

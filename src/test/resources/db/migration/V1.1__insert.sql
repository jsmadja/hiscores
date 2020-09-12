-- PLAYERS
insert into player (id, name, shmup_user_id, twitter, vip, hide_medals, created_at, updated_at, comments)
values (1, 'anzymus', 33489, '@jsmadja', 1, 0, '2013-11-29 19:13:32', '2020-08-01 17:33:53', null);
insert into player (id, name, shmup_user_id, twitter, vip, hide_medals, created_at, updated_at, comments)
values (2, 'Mickey', 33490, '@mickey', 1, 0, '2013-11-29 19:13:32', '2020-08-01 17:33:53', null);

-- GAMES
insert into game (id, cover, cover_backup, title, thread, general_ranking, shmup_name, created_at, updated_at)
values (1, '/covers/172.jpg', '/assets/images/cover/STRIKERS+1945+PLUS+1.jpg', 'Strikers 1945 PLUS',
        'http://forum.shmup.com/viewtopic.php?t=8620&f=20', false, 'Strikers 1945 Plus', '2014-08-15 12:14:58',
        '2014-08-15 12:14:58');

-- PLATFORMS
insert into platform (id, name, game_id, created_at, updated_at)
values (1, 'NG', 1, '2014-08-15 12:14:58', '2014-08-15 12:14:58');
insert into platform (id, name, game_id, created_at, updated_at)
values (2, 'PCB', 1, '2013-11-29 19:12:15', '2013-11-29 19:12:15');
insert into platform (id, name, game_id, created_at, updated_at)
values (3, 'X360', 1, '2013-11-29 19:12:15', '2013-11-29 19:12:15');

-- MODES
insert into mode (id, name, sort_order, game_id, score_type, created_at, updated_at)
values (1, 'Black Label', 20, 1, '', '2013-11-29 19:12:15', '2013-11-29 19:12:15');
insert into mode (id, name, sort_order, game_id, score_type, created_at, updated_at)
values (2, 'White Label', 30, 1, '', '2013-11-29 19:12:15', '2013-11-29 19:12:15');

-- DIFFICULTIES
insert into difficulty (id, name, sort_order, game_id, created_at, updated_at)
values (1, 'Original', 1, 1, '2013-11-29 19:12:15', '2013-11-29 19:12:15');

-- STAGES
insert into stage (id, name, sort_order, game_id, created_at, updated_at)
values (1, '1-4', 3, 1, '2014-08-15 12:14:58', '2014-08-15 12:14:58');

-- SHIPS
insert into ship (id, name, sort_order, game_id, created_at, updated_at)
values (1, 'Type I', 1, 1, '2013-12-01 00:36:53', '2013-12-01 00:36:53');

-- SCORES
insert into score (id, game_id, player_id, stage_id, mode_id, difficulty_id, platform_id, value, comment, photo, replay,
                   rank, ship_id, created_at, updated_at, onecc, progression, inp)
values (1, 1, 2, NULL, 1, 1, 1, 425027421, 'All(Lx4) Reco Normal', null, null, 1, null, '2013-11-30 16:59:00',
        '2019-11-23 19:04:31', 1, null, null);

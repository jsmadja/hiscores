create table base_model
(
    id         bigint auto_increment
        primary key,
    created_at datetime not null,
    updated_at datetime not null
)
    engine = MyISAM;

create table difficulty
(
    id         bigint auto_increment
        primary key,
    name       varchar(255) collate utf8_unicode_ci null,
    sort_order int                                  null,
    game_id    bigint                               null,
    created_at datetime                             not null,
    updated_at datetime                             not null
)
    engine = MyISAM;

create index game_id
    on difficulty (game_id);

create index name
    on difficulty (name);

create index sort_order
    on difficulty (sort_order);

create table event
(
    id          bigint auto_increment
        primary key,
    name        varchar(255)  not null,
    description varchar(1024) null,
    game_id     bigint        not null,
    created_at  datetime      not null,
    updated_at  datetime      not null
);

create table game
(
    id              bigint auto_increment
        primary key,
    cover           varchar(255) collate utf8_unicode_ci null,
    cover_backup    varchar(255)                         null,
    title           varchar(255) collate utf8_unicode_ci null,
    thread          varchar(255) collate utf8_unicode_ci null,
    general_ranking bit default b'0'                     not null,
    shmup_name      varchar(255)                         null,
    created_at      datetime                             not null,
    updated_at      datetime                             not null
)
    engine = MyISAM;

create table hibernate_sequence
(
    next_val bigint null
)
    engine = MyISAM;

create table mode
(
    id         bigint auto_increment
        primary key,
    name       varchar(255) collate utf8_unicode_ci null,
    sort_order int                                  null,
    game_id    bigint                               null,
    score_type varchar(255)                         null,
    created_at datetime                             not null,
    updated_at datetime                             not null
)
    engine = MyISAM;

create index ix_mode_game_2
    on mode (game_id);

create index name
    on mode (name);

create index sort_order
    on mode (sort_order);

create table platform
(
    id         bigint auto_increment
        primary key,
    name       varchar(255) collate utf8_unicode_ci null,
    game_id    bigint                               null,
    created_at datetime                             not null,
    updated_at datetime                             not null
)
    engine = MyISAM;

create index ix_platform_game_3
    on platform (game_id);

create index name
    on platform (name);

create table play_evolutions
(
    id            int                                 not null
        primary key,
    hash          varchar(255)                        not null,
    applied_at    timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    apply_script  text                                null,
    revert_script text                                null,
    state         varchar(255)                        null,
    last_problem  text                                null
)
    engine = MyISAM;

create table player
(
    id            bigint auto_increment
        primary key,
    name          varchar(255) collate utf8_unicode_ci null,
    shmup_user_id bigint                               null,
    twitter       varchar(255) collate utf8_unicode_ci null,
    vip           tinyint    default 1                 not null,
    hide_medals   tinyint(1) default 0                 not null,
    created_at    datetime                             not null,
    updated_at    datetime                             not null,
    comments      varchar(255)                         null
);

create table score
(
    id            bigint auto_increment
        primary key,
    game_id       bigint        null,
    player_id     bigint        null,
    stage_id      bigint        null,
    mode_id       bigint        null,
    difficulty_id bigint        null,
    platform_id   bigint        null,
    value         decimal(22)   null,
    comment       longtext      null,
    photo         varchar(1024) null,
    replay        varchar(255)  null,
    `rank`        bigint        null,
    ship_id       bigint        null,
    created_at    datetime      not null,
    updated_at    datetime      not null,
    onecc         tinyint(1)    not null,
    progression   int           null,
    inp           varchar(1024) null
)
    collate = utf8_unicode_ci;

create index ix_score_difficulty_8
    on score (difficulty_id);

create index ix_score_game_4
    on score (game_id);

create index ix_score_mode_7
    on score (mode_id);

create index ix_score_platform_9
    on score (platform_id);

create index ix_score_player_5
    on score (player_id);

create index ix_score_stage_6
    on score (stage_id);

create index onecc
    on score (onecc);

create index `rank`
    on score (`rank`);

create table ship
(
    id         bigint auto_increment
        primary key,
    name       varchar(255) null,
    sort_order int          null,
    game_id    bigint       null,
    created_at datetime     not null,
    updated_at datetime     not null
)
    engine = MyISAM
    collate = utf8_unicode_ci;

create index ix_ship_game_2
    on ship (game_id);

create index name
    on ship (name);

create index sort_order
    on ship (sort_order);

create table shmup
(
    id           bigint(40) auto_increment
        primary key,
    nom          varchar(50)  default ''  not null,
    annee        varchar(4)   default ''  not null,
    fabriquant   varchar(50)  default ''  not null,
    support      varchar(50)  default ''  not null,
    url_rom      varchar(50)  default ''  not null,
    url_img1     varchar(80)  default ''  not null,
    url_img2     varchar(80)  default ''  not null,
    commentaire  text                     not null,
    site         varchar(80)  default ''  not null,
    note_tot     varchar(10)  default ''  not null,
    note_gra     tinytext                 not null,
    note_son     tinytext                 not null,
    rom_server   varchar(50)  default ''  not null,
    link         varchar(100) default ''  not null,
    nbrdl        mediumint    default 0   not null,
    sens         char(3)      default ''  not null,
    joueur       char(2)      default ''  not null,
    auteur       varchar(100) default '0' not null,
    email_auteur varchar(150) default ''  not null,
    url_img3     varchar(80)  default ''  not null,
    famille      varchar(20)  default ''  not null,
    url_img4     varchar(80)  default ''  not null,
    url_img5     varchar(80)  default ''  not null,
    etymologie   varchar(250) default ''  not null,
    date_update  varchar(20)  default ''  not null,
    clone        varchar(6)   default ''  not null
)
    engine = MyISAM;

create table stage
(
    id         bigint auto_increment
        primary key,
    name       varchar(255) collate utf8_unicode_ci null,
    sort_order int                                  null,
    game_id    bigint                               null,
    created_at datetime                             not null,
    updated_at datetime                             not null
)
    engine = MyISAM;

create index ix_stage_game_10
    on stage (game_id);

create index name
    on stage (name);

create index sort_order
    on stage (sort_order);
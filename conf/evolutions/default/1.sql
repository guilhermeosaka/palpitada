# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table group_team (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  constraint pk_group_team primary key (id))
;

create table linked_account (
  id                        bigint auto_increment not null,
  user_id                   bigint,
  provider_user_id          varchar(255),
  provider_key              varchar(255),
  constraint pk_linked_account primary key (id))
;

create table matches (
  id                        bigint auto_increment not null,
  date                      datetime,
  constraint pk_matches primary key (id))
;

create table matchteam (
  match_id                  bigint,
  team_id                   bigint,
  constraint pk_matchteam primary key (match_id, team_id))
;

create table security_role (
  id                        bigint auto_increment not null,
  role_name                 varchar(255),
  constraint pk_security_role primary key (id))
;

create table stadium (
  id                        bigint auto_increment not null,
  name                      varchar(255),
  image                     varchar(255),
  city                      varchar(255),
  state                     varchar(255),
  capacity                  bigint,
  constraint pk_stadium primary key (id))
;

create table stage (
  id                        bigint auto_increment not null,
  constraint pk_stage primary key (id))
;

create table stagename (
  language                  varchar(255),
  stage_id                  bigint,
  name                      varchar(255),
  constraint pk_stagename primary key (language, stage_id))
;

create table team (
  id                        bigint auto_increment not null,
  logo                      varchar(255),
  group_id                  bigint,
  constraint pk_team primary key (id))
;

create table team_name (
  language                  varchar(255),
  team_id                   bigint,
  name                      varchar(255),
  constraint pk_team_name primary key (language, team_id))
;

create table token_action (
  id                        bigint auto_increment not null,
  token                     varchar(255),
  target_user_id            bigint,
  type                      varchar(2),
  created                   datetime,
  expires                   datetime,
  constraint ck_token_action_type check (type in ('EV','PR')),
  constraint uq_token_action_token unique (token),
  constraint pk_token_action primary key (id))
;

create table users (
  id                        bigint auto_increment not null,
  email                     varchar(255),
  name                      varchar(255),
  first_name                varchar(255),
  last_name                 varchar(255),
  last_login                datetime,
  active                    tinyint(1) default 0,
  email_validated           tinyint(1) default 0,
  team_id                   bigint,
  constraint pk_users primary key (id))
;

create table user_permission (
  id                        bigint auto_increment not null,
  value                     varchar(255),
  constraint pk_user_permission primary key (id))
;


create table users_security_role (
  users_id                       bigint not null,
  security_role_id               bigint not null,
  constraint pk_users_security_role primary key (users_id, security_role_id))
;

create table users_user_permission (
  users_id                       bigint not null,
  user_permission_id             bigint not null,
  constraint pk_users_user_permission primary key (users_id, user_permission_id))
;
alter table linked_account add constraint fk_linked_account_user_1 foreign key (user_id) references users (id) on delete restrict on update restrict;
create index ix_linked_account_user_1 on linked_account (user_id);
alter table matchteam add constraint fk_matchteam_match_2 foreign key (match_id) references matches (id) on delete restrict on update restrict;
create index ix_matchteam_match_2 on matchteam (match_id);
alter table matchteam add constraint fk_matchteam_team_3 foreign key (team_id) references team (id) on delete restrict on update restrict;
create index ix_matchteam_team_3 on matchteam (team_id);
alter table stagename add constraint fk_stagename_stage_4 foreign key (stage_id) references stage (id) on delete restrict on update restrict;
create index ix_stagename_stage_4 on stagename (stage_id);
alter table team add constraint fk_team_group_5 foreign key (group_id) references group_team (id) on delete restrict on update restrict;
create index ix_team_group_5 on team (group_id);
alter table team_name add constraint fk_team_name_team_6 foreign key (team_id) references team (id) on delete restrict on update restrict;
create index ix_team_name_team_6 on team_name (team_id);
alter table token_action add constraint fk_token_action_targetUser_7 foreign key (target_user_id) references users (id) on delete restrict on update restrict;
create index ix_token_action_targetUser_7 on token_action (target_user_id);
alter table users add constraint fk_users_team_8 foreign key (team_id) references team (id) on delete restrict on update restrict;
create index ix_users_team_8 on users (team_id);



alter table users_security_role add constraint fk_users_security_role_users_01 foreign key (users_id) references users (id) on delete restrict on update restrict;

alter table users_security_role add constraint fk_users_security_role_security_role_02 foreign key (security_role_id) references security_role (id) on delete restrict on update restrict;

alter table users_user_permission add constraint fk_users_user_permission_users_01 foreign key (users_id) references users (id) on delete restrict on update restrict;

alter table users_user_permission add constraint fk_users_user_permission_user_permission_02 foreign key (user_permission_id) references user_permission (id) on delete restrict on update restrict;

# --- !Downs

SET FOREIGN_KEY_CHECKS=0;

drop table group_team;

drop table linked_account;

drop table matches;

drop table matchteam;

drop table security_role;

drop table stadium;

drop table stage;

drop table stagename;

drop table team;

drop table team_name;

drop table token_action;

drop table users;

drop table users_security_role;

drop table users_user_permission;

drop table user_permission;

SET FOREIGN_KEY_CHECKS=1;


# DROP SCHEMA systemairac;
# CREATE SCHEMA systemairac
#     DEFAULT CHARACTER SET utf8mb4
#     COLLATE utf8mb4_general_ci;
#
# USE systemairac;
#
# create table user_seq (next_val BIGINT) engine=InnoDB;
# insert into user_seq values ( 1 );
#
# drop table if exists tbl_users cascade;
# create table tbl_users (
#    id              BIGINT,
#    name            VARCHAR(255) UNIQUE NOT NULL ,
#    password        VARCHAR(255) not null,
#    full_name       VARCHAR(255),
#    name_company    VARCHAR(255),
#    address_company VARCHAR(255),
#    post            VARCHAR(255),
#    phone           BIGINT,
#    email           VARCHAR(255) UNIQUE NOT NULL ,
#    role varchar(255),
#    PRIMARY KEY (id)
# );
#
#
# drop table if exists tbl_roles;
# create table tbl_roles (
#    id TINYINT,
#    name VARCHAR(128) unique not null,
#    primary key (id)
# );
#
# DROP TABLE IF EXISTS tbl_users_roles;
# CREATE TABLE tbl_users_roles (
#     user_id BIGINT NOT NULL ,
#     role_id TINYINT NOT NULL ,
#     CONSTRAINT fk__tbl_users_roles__tbl_users FOREIGN KEY (user_id) REFERENCES tbl_users (id),
#     CONSTRAINT fk__tbl_users_roles__tbl_roles FOREIGN KEY (role_id) REFERENCES tbl_roles (id)
# );
#
# DROP TABLE IF EXISTS tbl_unit_types CASCADE;
# CREATE TABLE tbl_unit_types (
#     id SMALLINT,
#     table_name VARCHAR(255),
#     PRIMARY KEY (id)
# );
#
# INSERT INTO tbl_unit_types (id, table_name)
#     VALUES (1, 'tbl_humidifiers');
#
# drop sequence if exists unit_seq;
# create sequence unit_seq start with 1 increment by 1;
#
# drop table if exists tbl_units cascade;
# create table tbl_units (
#     id BIGINT,
#     type SMALLINT,
#     id_in_subtable BIGINT,
#     primary key(id),
#     CONSTRAINT fk__tbl_units__tbl_unit_types
#         FOREIGN KEY (type) REFERENCES tbl_unit_types (id)
# );
#
# # TODO: если у нас Unit / Calculation -- OneToOne, то они должны быть слиты в единую таблицу!!
# # Но, скорее всего, это просто ошибка, они ведь должны быть "много Calculation на один Unit" (?)
#
# drop sequence if exists calculation_seq;
# create sequence calculation_seq start with 1 increment by 1;
# drop table if exists tbl_calculations cascade;
# create table tbl_calculations (
#   id BIGINT,
#   project_id BIGINT,
#   date timestamp,
#   primary key(id)
# );
#
# create table tbl_calculations_unit (
#     calculation_id bigint not null,
#     unit_id bigint not null,
#     constraint uk__tbl_calculations_tbl_units unique (unit_id),
#     constraint fk__tbl_calculations_tbl_units foreign key (unit_id) references tbl_units(id),
#     constraint fk__tbl_calculations_tbl_calculations foreign key (calculation_id) references tbl_calculations(id)
#  );
#
# drop sequence if exists project_seq;
# create sequence project_seq start with 1 increment by 1;
#
# DROP TABLE IF EXISTS tbl_projects CASCADE;
# CREATE TABLE tbl_projects (
#     id bigint,
#     address varchar (255),
#     title varchar (255),
#     user_id BIGINT NOT NULL,
#     primary key(id),
#     CONSTRAINT fk__tbl_projects__tbl_users FOREIGN KEY (user_id) REFERENCES tbl_users (id)
# );
# ALTER TABLE tbl_calculations
#     ADD CONSTRAINT fk__tbl_calculations__tbl_projects FOREIGN KEY (project_id) REFERENCES tbl_projects (id);
#
# drop sequence if exists brand_seq;
# create sequence brand_seq start with 1 increment by 1;
#
# drop table if exists tbl_brands cascade;
# create table tbl_brands (
#   id tinyint,
#   name varchar (255) UNIQUE NOT NULL ,
#   primary key(id)
# );
#
# -- Humidifiers and all about them
# drop sequence if exists humidifier_component_seq;
# create sequence humidifier_component_seq start with 1 increment by 1;
#
# drop table if exists tbl_humidifier_components cascade;
# create table tbl_humidifier_components (
#    id BIGINT,
#    brand_id tinyint NOT NULL ,
#    article_number varchar (255) NOT NULL ,
#    optional BOOLEAN,
#    type varchar (255),
#    primary key(id),
#    CONSTRAINT fk__tbl_humidifier_components__tbl_brands FOREIGN KEY (brand_id) REFERENCES tbl_brands (id)
# );
#
# drop sequence if exists vapor_distributor_seq;
# create sequence vapor_distributor_seq start with 1 increment by 1;
#
# drop table if exists tbl_vapor_distributors cascade;
# create table tbl_vapor_distributors (
#     id SMALLINT,
#     brand_id TINYINT NOT NULL ,
#     article_number varchar (255) NOT NULL ,
#     length INT NOT NULL ,
#     diameter INT NOT NULL ,
#     price DECIMAL (10, 2),
#     primary key(id),
#     CONSTRAINT fk__tbl_vapor_distributors__tbl_brands FOREIGN KEY (brand_id) REFERENCES tbl_brands (id)
# );
#
# DROP TABLE IF EXISTS tbl_humidifier_types CASCADE;
# CREATE TABLE tbl_humidifier_types
# (
#     id        TINYINT,
#     type_name VARCHAR(255) UNIQUE NOT NULL,
#     PRIMARY KEY (id)
# );
#
# drop sequence if exists humidifier_seq;
# create sequence humidifier_seq start with 1 increment by 1;
#
# drop table if exists tbl_humidifiers cascade;
# create table tbl_humidifiers (
#     id BIGINT,
#     brand_id TINYINT NOT NULL ,
#     article_number varchar (255) unique NOT NULL ,
#     humidifier_type TINYINT,
#     electric_power DECIMAL(6, 2),
#     capacity DECIMAL(6, 2),
#     phase TINYINT,
#     voltage TINYINT,
#     number_of_cylinders TINYINT,
#     vapor_pipe_diameter TINYINT,
#     price DECIMAL(10, 2),
#     primary key(id),
#     CONSTRAINT fk__tbl_humidifiers__tbl_brands
#         FOREIGN KEY (brand_id) REFERENCES tbl_brands (id),
#     CONSTRAINT fk__tbl_humidifiers__tbl_humidifier_types
#         FOREIGN KEY (humidifier_type) REFERENCES tbl_humidifier_types (id)
# );
#
#
# drop table if exists tbl_humidifiers_humidifier_components cascade;
# CREATE TABLE tbl_humidifiers_humidifier_components (
#     humidifier_id BIGINT NOT NULL ,
#     humidifier_component_id BIGINT NOT NULL ,
#     CONSTRAINT fk__tbl_humidifiers_humidifier_components__tbl_humidifiers
#         FOREIGN KEY (humidifier_id) REFERENCES tbl_humidifiers (id),
#     CONSTRAINT fk__tbl_humidifiers_humidifier_components__tbl_humidifier_compon     # слишком длинный!
#         FOREIGN KEY (humidifier_component_id) REFERENCES tbl_humidifier_components (id)
# );
#
# DROP TABLE IF EXISTS tbl_humidifiers_vapor_distributors CASCADE;
# CREATE TABLE tbl_humidifiers_vapor_distributors (
#    humidifier_id BIGINT NOT NULL ,
#    vapor_distributor_id SMALLINT NOT NULL ,
#    CONSTRAINT fk__tbl_humidifiers_vapor_distributors__tbl_humidifiers
#        FOREIGN KEY (humidifier_id) REFERENCES tbl_humidifiers (id),
#    CONSTRAINT fk__tbl_humidifiers_vapor_distributors__tbl_vapor_distributors
#        FOREIGN KEY (vapor_distributor_id) REFERENCES tbl_vapor_distributors (id)
# );

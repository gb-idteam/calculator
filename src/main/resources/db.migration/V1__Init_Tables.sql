drop table if exists tbl_projects cascade;
create table tbl_projects (
                    id bigserial
                    address varchar (255),
                    title varchar(255),
                    user varchar (255),
                    primary key(id)
);


drop table if exists tbl_brands cascade;
create table tbl_brands (
                              id bigserial
                              name varchar (255),
                              primary key(id)
);


drop table if exists tbl_units cascade;
create table tbl_units (
                              id bigserial
                              name varchar (255),
                              unitType varchar(255),
                              primary key(id)
);

-- Users and Roles
drop table if exists tbl_users cascade;
create table tbl_users (
                       id              bigserial,
                       name            VARCHAR(255),
                       password        VARCHAR(255) not null,
                       role            VARCHAR(255),
                       fullName        VARCHAR(255),
                       nameCompany     VARCHAR(255),
                       addressCompany  VARCHAR(255),
                       post            VARCHAR(255),
                       phone           bigserial,
                       email           VARCHAR(50) UNIQUE,
                       PRIMARY KEY (id)
);

insert into users (name, password, role, fullName, nameCompamy, addressComapany, post, phone, email) values
('Ivan','$2y$12$pDKtGkFNC9Gbp1BhK4SeNOSsqRiHapo83WE9VqyMD/MVjvMnvLluK',
 'admin','Ivanov', 'X-Company', 'Earth, worldCity, AAA', 'XXX555', 8999888777666555, 'admin@email.com');


drop table if exists tbl_roles;
create table tbl_roles (
                       id                    serial,
                       name                  VARCHAR(50) not null,
                       primary key (id)
);

insert into roles (name)
values
('USER'), ('ADMIN');

drop table if exists users_roles cascade;
create table users_roles (
                             user_id               INT NOT NULL,
                             role_id               INT NOT NULL,
                             primary key (user_id, role_id),
                             FOREIGN KEY (user_id)
                             REFERENCES users (id),
                             FOREIGN KEY (role_id)
                             REFERENCES roles (id)
);

insert into users_roles (user_id, role_id)
values
(1, 1),
(1, 2);

drop table if exists tbl_calculations cascade;
create table tbl_calculations (
                            id bigserial,
                            unit_name varchar (255),
                            project_title varchar (255),
                            date current_date,
                            primary key(id)
);

-- Humidifiers and all about them
drop table if exists tbl_humidifier_components cascade;
create table tbl_humidifier_components (
                                           id bigserial,
                                           brand_name varchar (255),
                                           articleNumber varchar (255),
                                           HumidifierComponentType varchar (255),
                                           primary key(id)
);

drop table if exists tbl_vapor_distributors cascade;
create table tbl_vapor_distributors (
                                           id bigserial,
                                           brand_name varchar (255),
                                           articleNumber varchar (255),
                                           length int (10),
                                           diameter int (10),
                                           price numeric (8, 2),
                                           primary key(id)
);

drop table if exists tbl_humidifiers cascade;
create table tbl_humidifiers (
                                        id bigserial,
                                        unitType varchar (255) unique,
                                        articleNumber varchar (255) unique,
                                        brand_name varchar (255),
                                        humidifier_type varchar (255),
                                        electricPower float (10),
                                        maxVaporOutput float (10),
                                        phase int (10),
                                        voltage int (10),
                                        numberOfCylinders int (10),
                                        vaporPipeDiameter int (10),
                                        price numeric (8, 2),
                                        primary key(id),
                                        constraint humidifiers_vaporDistributors foreign key (humidifier_id) references tbl_vapor_distributors (id),
                                        constraint humidifiers_humidifierComponents foreign key (humidifier_id) references tbl_humidifier_components (id)
);
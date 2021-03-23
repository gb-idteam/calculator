insert into users (name, password, role, fullName, nameCompamy, addressComapany, post, phone, email) values
('Ivan','$2y$12$pDKtGkFNC9Gbp1BhK4SeNOSsqRiHapo83WE9VqyMD/MVjvMnvLluK',
 'admin','Ivanov', 'X-Company', 'Earth, worldCity, AAA', 'XXX555', 8999888777666555, 'admin@email.com');



insert into roles (name)
values
('USER'), ('ADMIN');


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

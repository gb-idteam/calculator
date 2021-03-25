CREATE TABLE brand_seq (next_val BIGINT) engine = InnoDB;

INSERT INTO brand_seq
VALUES (1);

CREATE TABLE brands (
     id BIGINT NOT NULL
    ,NAME VARCHAR(255)
    ,PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE calculation_seq (next_val BIGINT) engine = InnoDB;

INSERT INTO calculation_seq
VALUES (1);

CREATE TABLE calculations (
     id BIGINT NOT NULL
    ,DATE DATETIME (6)
    ,project_id BIGINT
    ,PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE calculations_unit (
     calculation_id BIGINT NOT NULL
    ,unit_id BIGINT NOT NULL
) engine = InnoDB;

CREATE TABLE humidifier_component_seq (next_val BIGINT) engine = InnoDB;

INSERT INTO humidifier_component_seq
VALUES (1);

CREATE TABLE humidifier_components (
     id BIGINT NOT NULL
    ,article_number VARCHAR(255)
    ,optional BIT NOT NULL
    ,type VARCHAR(255)
    ,brand_id BIGINT
    ,PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE humidifier_seq (next_val BIGINT) engine = InnoDB;

INSERT INTO humidifier_seq
VALUES (1);

CREATE TABLE humidifier_types (
     id BIGINT NOT NULL
    ,table_name VARCHAR(255)
    ,PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE humidifiers (
     id BIGINT NOT NULL
    ,article_number VARCHAR(255)
    ,capacity FLOAT
    ,electric_power FLOAT
    ,number_of_cylinders INTEGER
    ,phase INTEGER
    ,price DECIMAL(19, 2)
    ,vapor_pipe_diameter INTEGER
    ,voltage INTEGER
    ,brand_id BIGINT
    ,humidifier_type_id BIGINT
    ,PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE humidifiers_humidifier_components (
     humidifier_id BIGINT NOT NULL
    ,humidifier_component_id BIGINT NOT NULL
) engine = InnoDB;

CREATE TABLE humidifiers_vapor_distributors (
     humidifier_id BIGINT NOT NULL
    ,vapor_distributor_id BIGINT NOT NULL
) engine = InnoDB;

CREATE TABLE project_seq (next_val BIGINT) engine = InnoDB;

INSERT INTO project_seq
VALUES (1);

CREATE TABLE projects (
     id BIGINT NOT NULL
    ,address VARCHAR(255)
    ,title VARCHAR(255)
    ,user_id BIGINT
    ,PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE roles (
     id BIGINT NOT NULL
    ,ROLE VARCHAR(255)
    ,PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE roles_seq (next_val BIGINT) engine = InnoDB;

INSERT INTO roles_seq
VALUES (1);

CREATE TABLE unit_seq (next_val BIGINT) engine = InnoDB;

INSERT INTO unit_seq
VALUES (1);

CREATE TABLE unit_types (
     id BIGINT NOT NULL
    ,table_name VARCHAR(255)
    ,PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE unit_types_seq (next_val BIGINT) engine = InnoDB;

INSERT INTO unit_types_seq
VALUES (1);

INSERT INTO unit_types_seq
VALUES (1);

CREATE TABLE units (
     id BIGINT NOT NULL
    ,id_sub_table BIGINT
    ,type_id BIGINT
    ,PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE user_seq (next_val BIGINT) engine = InnoDB;

INSERT INTO user_seq
VALUES (1);

CREATE TABLE users (
     id BIGINT NOT NULL
    ,address_company VARCHAR(255)
    ,email VARCHAR(255)
    ,full_name VARCHAR(255)
    ,NAME VARCHAR(255)
    ,name_company VARCHAR(255)
    ,password VARCHAR(255)
    ,phone BIGINT
    ,post VARCHAR(255)
    ,PRIMARY KEY (id)
) engine = InnoDB;

CREATE TABLE users_roles (
    user_id BIGINT NOT NULL
    ,role_id BIGINT NOT NULL
) engine = InnoDB;

CREATE TABLE vapor_distributor_seq (next_val BIGINT) engine = InnoDB;

INSERT INTO vapor_distributor_seq
VALUES (1);

CREATE TABLE vapor_distributors (
    id BIGINT NOT NULL
    ,article_number VARCHAR(255)
    ,diameter INTEGER NOT NULL
    ,length INTEGER NOT NULL
    ,price DECIMAL(19, 2)
    ,brand_id BIGINT
    ,PRIMARY KEY (id)
) engine = InnoDB;

ALTER TABLE brands ADD CONSTRAINT UK_oce3937d2f4mpfqrycbr0l93m UNIQUE (NAME);

ALTER TABLE calculations_unit ADD CONSTRAINT UK_si15mvqfk9vqrlxlc3ykkngc1 UNIQUE (unit_id);

ALTER TABLE humidifier_components ADD CONSTRAINT UK_9siwr4ieol5l0djc2mlleymxh UNIQUE (article_number);

ALTER TABLE humidifiers ADD CONSTRAINT UK_rrb8kt1vc78a56b78nk9kve2q UNIQUE (article_number);

ALTER TABLE vapor_distributors ADD CONSTRAINT UK_idn47mnxt7xpsv1o2vi1m7uu6 UNIQUE (article_number);

ALTER TABLE calculations ADD CONSTRAINT FKihqfbpgvaxsevcpnvno6qvyv4 FOREIGN KEY (project_id) REFERENCES projects (id);

ALTER TABLE calculations_unit ADD CONSTRAINT FKypqsawft7yo8rogvmd64ey0v FOREIGN KEY (unit_id) REFERENCES units (id);

ALTER TABLE calculations_unit ADD CONSTRAINT FKdoy2mmrpaqha1i5inxijaxtyl FOREIGN KEY (calculation_id) REFERENCES calculations (id);

ALTER TABLE humidifier_components ADD CONSTRAINT FKcoyv3bg2d4jmqy887nd2llox9 FOREIGN KEY (brand_id) REFERENCES brands (id);

ALTER TABLE humidifiers ADD CONSTRAINT FK6nx4o47eghx5usqac89rldk1a FOREIGN KEY (brand_id) REFERENCES brands (id);

ALTER TABLE humidifiers ADD CONSTRAINT FKn9je50p0ydnb2pqsbdf17od5b FOREIGN KEY (humidifier_type_id) REFERENCES humidifier_types (id);

ALTER TABLE humidifiers_humidifier_components ADD CONSTRAINT FK2l6owunjsippriodwbvxiyttn FOREIGN KEY (humidifier_component_id) REFERENCES humidifier_components (id);

ALTER TABLE humidifiers_humidifier_components ADD CONSTRAINT FKsild4njyutdw1mv9y3csw5n9p FOREIGN KEY (humidifier_id) REFERENCES humidifiers (id);

ALTER TABLE humidifiers_vapor_distributors ADD CONSTRAINT FKouymnq3c0fvmvnu8b057dwbqi FOREIGN KEY (vapor_distributor_id) REFERENCES vapor_distributors (id);

ALTER TABLE humidifiers_vapor_distributors ADD CONSTRAINT FKght2dvb9lpegui5x9f37efafu FOREIGN KEY (humidifier_id) REFERENCES humidifiers (id);

ALTER TABLE projects ADD CONSTRAINT FKhswfwa3ga88vxv1pmboss6jhm FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE units ADD CONSTRAINT FKcajpqkb2cimfqo21v8v31bowg FOREIGN KEY (type_id) REFERENCES unit_types (id);

ALTER TABLE users_roles ADD CONSTRAINT FKj6m8fwv7oqv74fcehir1a9ffy FOREIGN KEY (role_id) REFERENCES roles (id);

ALTER TABLE users_roles ADD CONSTRAINT FK2o0jvgh89lemvvo17cbqvdxaa FOREIGN KEY (user_id) REFERENCES users (id);

ALTER TABLE vapor_distributors ADD CONSTRAINT FKo6dym8dsq5ksm28i9168pdhhf FOREIGN KEY (brand_id) REFERENCES brands (id);
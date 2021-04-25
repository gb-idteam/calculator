CREATE TABLE brand (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255),
                PRIMARY KEY (id)
) engine = InnoDB;
CREATE TABLE calculation (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    date DATETIME(6),
                    estimate_id BIGINT,
                    project_id BIGINT,
                PRIMARY KEY (id)
) engine = InnoDB;
CREATE TABLE estimate (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    humidifier_id BIGINT,
                    vapor_distributor_id BIGINT,
                PRIMARY KEY (id)
) engine = InnoDB;
CREATE TABLE estimate_humidifier_components (
                    estimate_id BIGINT NOT NULL, 
                    humidifier_components_id BIGINT NOT NULL
) engine = InnoDB;
CREATE TABLE file (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    name VARCHAR(255),
                    project_id BIGINT,
                PRIMARY KEY (id)
) engine = InnoDB;
CREATE TABLE humidifier (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    article_number VARCHAR(255),
                    capacity DOUBLE PRECISION,
                    electric_power DOUBLE PRECISION,
                    humidifier_type VARCHAR(255),
                    number_of_cylinders INTEGER,
                    price DECIMAL(19, 2),
                    title VARCHAR(255),
                    vapor_pipe_diameter INTEGER,
                    voltage VARCHAR(255),
                    brand_id BIGINT,
                    image_id BIGINT,
                PRIMARY KEY (id)
) engine = InnoDB;
CREATE TABLE humidifier_component (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    article_number VARCHAR(255),
                    optional BIT NOT NULL,
                    price DECIMAL(19, 2),
                    type VARCHAR(255),
                    brand_id BIGINT,
                    image_id BIGINT,
                PRIMARY KEY (id)
) engine = InnoDB;
CREATE TABLE humidifiers_humidifier_components (
                    humidifier_id BIGINT NOT NULL, 
                    humidifier_component_id BIGINT NOT NULL
) engine = InnoDB;
CREATE TABLE humidifiers_vapor_distributors (
                    humidifier_id BIGINT NOT NULL, 
                    vapor_distributor_id BIGINT NOT NULL
) engine = InnoDB;
CREATE TABLE image (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    link VARCHAR(255),
                PRIMARY KEY (id)
) engine = InnoDB;
CREATE TABLE project (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    ADDress VARCHAR(255),
                    title VARCHAR(255),
                    user_id BIGINT,
                PRIMARY KEY (id)
) engine = InnoDB;
CREATE TABLE role (
                    id smallint NOT NULL AUTO_INCREMENT,
                    role_name VARCHAR(255),
                PRIMARY KEY (id)
) engine = InnoDB;
CREATE TABLE tech_data (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    air_flow INTEGER NOT NULL,
                    altitude INTEGER,
                    atmosphere_pressure DOUBLE PRECISION,
                    calc_capacity DOUBLE PRECISION NOT NULL,
                    date DATETIME(6),
                    enum_humidifier_type VARCHAR(255),
                    hum_in DOUBLE PRECISION NOT NULL,
                    hum_out DOUBLE PRECISION NOT NULL,
                    height INTEGER NOT NULL,
                    temp_in DOUBLE PRECISION NOT NULL,
                    type_montage VARCHAR(255),
                    voltage VARCHAR(255),
                    width INTEGER NOT NULL,
                    calculation_id BIGINT,
                PRIMARY KEY (id)
) engine = InnoDB;
CREATE TABLE user (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    address_company VARCHAR(255),
                    email VARCHAR(255),
                    is_confirmed BOOLEAN NOT NULL DEFAULT 0,
                    full_name VARCHAR(255),
                    name_company VARCHAR(255),
                    password VARCHAR(255),
                    phone BIGINT,
                    position VARCHAR(255),
                    confirm_keys VARCHAR(255),
                PRIMARY KEY (id)
) engine = InnoDB;
CREATE TABLE users_roles (
                    user_id BIGINT NOT NULL, role_id smallint NOT NULL
) engine = InnoDB;
CREATE TABLE vapor_distributor (
                    id BIGINT NOT NULL AUTO_INCREMENT,
                    article_number VARCHAR(255),
                    diameter INTEGER NOT NULL,
                    length INTEGER NOT NULL,
                    price DECIMAL(19, 2),
                    brand_id BIGINT,
                    image_id BIGINT,
                PRIMARY KEY (id)
) engine = InnoDB;
ALTER TABLE
    brand
    ADD
        CONSTRAINT UK_rdxh7tq2xs66r485cc8dkxt77 UNIQUE (name);
ALTER TABLE
    humidifier
    ADD
        CONSTRAINT UK_gxtwa5nd076fbuskgmdthkrgp UNIQUE (article_number);
ALTER TABLE
    humidifier_component
    ADD
        CONSTRAINT UK_1svj44k3n0r4te4hl7l8ygluo UNIQUE (article_number);
ALTER TABLE
    role
    ADD
        CONSTRAINT UK_iubw515ff0ugtm28p8g3myt0h UNIQUE (role_name);
ALTER TABLE
    user
    ADD
        CONSTRAINT UK_ob8kqyqqgmefl0aco34akdtpe UNIQUE (email);
ALTER TABLE
    vapor_distributor
    ADD
        CONSTRAINT UK_ikug7kwkc8vdlu35v7wjkvfj1 UNIQUE (article_number);
ALTER TABLE
    calculation
    ADD
        CONSTRAINT FKityg46p5u9nbtjhw26ks8kc0a FOREIGN KEY (estimate_id) REFERENCES estimate (id);
ALTER TABLE
    calculation
    ADD
        CONSTRAINT FKpyr9kr4f9ikocd7i8ji5tfo16 FOREIGN KEY (project_id) REFERENCES project (id);
ALTER TABLE
    estimate
    ADD
        CONSTRAINT FK11xpto1pn60g2cj457svv0xys FOREIGN KEY (humidifier_id) REFERENCES humidifier (id);
ALTER TABLE
    estimate
    ADD
        CONSTRAINT FK2j853k4xvnjb2uaubbri8u0m4 FOREIGN KEY (vapor_distributor_id) REFERENCES vapor_distributor (id);
ALTER TABLE
    estimate_humidifier_components
    ADD
        CONSTRAINT FKbccuk3u4oo9po5nxiva65f0h FOREIGN KEY (humidifier_components_id) REFERENCES humidifier_component (id);
ALTER TABLE
    estimate_humidifier_components
    ADD
        CONSTRAINT FKc2fhqj7i2sillxww8yvwslyxs FOREIGN KEY (estimate_id) REFERENCES estimate (id);
ALTER TABLE
    file
    ADD
        CONSTRAINT FK9xpbf8klk9il032sq5xupl1f FOREIGN KEY (project_id) REFERENCES project (id);
ALTER TABLE
    humidifier
    ADD
        CONSTRAINT FKn17xgu7j5nfehekv1g4bms9su FOREIGN KEY (brand_id) REFERENCES brand (id);
ALTER TABLE
    humidifier
    ADD
        CONSTRAINT FK2ffj72i7cokn76a2xw4b8x03s FOREIGN KEY (image_id) REFERENCES image (id);
ALTER TABLE
    humidifier_component
    ADD
        CONSTRAINT FKjrhsuwp76omamf1h31gv0vqqw FOREIGN KEY (brand_id) REFERENCES brand (id);
ALTER TABLE
    humidifier_component
    ADD
        CONSTRAINT FK497y4ka5f5ljnff2uwejo39n4 FOREIGN KEY (image_id) REFERENCES image (id);
ALTER TABLE
    humidifiers_humidifier_components
    ADD
        CONSTRAINT FKcij11l5dw4pkh97ut47nxmfud FOREIGN KEY (humidifier_component_id) REFERENCES humidifier_component (id);
ALTER TABLE
    humidifiers_humidifier_components
    ADD
        CONSTRAINT FK73h7prcgl36jhl1pwje2s6m6m FOREIGN KEY (humidifier_id) REFERENCES humidifier (id);
ALTER TABLE
    humidifiers_vapor_distributors
    ADD
        CONSTRAINT FK8lgukr40afftlfgn5qjcu1s4x FOREIGN KEY (vapor_distributor_id) REFERENCES vapor_distributor (id);
ALTER TABLE
    humidifiers_vapor_distributors
    ADD
        CONSTRAINT FKmk1dv97dsjce5xvd5el4r3cog FOREIGN KEY (humidifier_id) REFERENCES humidifier (id);
ALTER TABLE
    project
    ADD
        CONSTRAINT FKo06v2e9kuapcugnyhttqa1vpt FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE
    tech_data
    ADD
        CONSTRAINT FKgp7gkby6h742v2hnlkytujhvc FOREIGN KEY (calculation_id) REFERENCES calculation (id);
ALTER TABLE
    users_roles
    ADD
        CONSTRAINT FKt4v0rrweyk393bdgt107vdx0x FOREIGN KEY (role_id) REFERENCES role (id);
ALTER TABLE
    users_roles
    ADD
        CONSTRAINT FKgd3iendaoyh04b95ykqise6qh FOREIGN KEY (user_id) REFERENCES user (id);
ALTER TABLE
    vapor_distributor
    ADD
        CONSTRAINT FKf3amtwpirneroposqcbnrwjva FOREIGN KEY (brand_id) REFERENCES brand (id);
ALTER TABLE
    vapor_distributor
    ADD
        CONSTRAINT FKa7es9r8ceuqw66rsvm3kednhu FOREIGN KEY (image_id) REFERENCES image (id);


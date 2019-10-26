CREATE TABLE achievement (
    ach_name         VARCHAR2(30) NOT NULL,
    date_award       DATE NOT NULL,
    users_username   VARCHAR2(30 CHAR)
);

ALTER TABLE achievement ADD CONSTRAINT achievements_pk PRIMARY KEY ( ach_name );

CREATE TABLE category (
    name VARCHAR2(20) NOT NULL
);

ALTER TABLE category ADD CONSTRAINT category_pk PRIMARY KEY ( name );

CREATE TABLE comments (
    id_comment                VARCHAR2(15) NOT NULL,
    content                   VARCHAR2(100) NOT NULL,
    comments_id_comment       VARCHAR2(15),
    users_username            VARCHAR2(30 CHAR) NOT NULL,
    threads_id_thread         VARCHAR2(15) NOT NULL,
    comments_users_username   VARCHAR2(30 CHAR)
);

ALTER TABLE comments ADD CONSTRAINT comments_pk PRIMARY KEY ( id_comment,
                                                              users_username );

CREATE TABLE purchase (
    id               VARCHAR2(15) NOT NULL,
    concept          VARCHAR2(30) NOT NULL,
    value            NUMBER(3) NOT NULL,
    users_username   VARCHAR2(30 CHAR) NOT NULL
);

ALTER TABLE purchase ADD CONSTRAINT purchases_pk PRIMARY KEY ( id );

CREATE TABLE thread (
    id_thread        VARCHAR2(15) NOT NULL,
    title            VARCHAR2(50) NOT NULL,
    content          VARCHAR2(5000 CHAR) NOT NULL,
    image_url        VARCHAR2(50),
    is_private       CHAR(1) ,
    users_username   VARCHAR2(30 CHAR),
    category_name    VARCHAR2(20)
);

ALTER TABLE thread ADD CONSTRAINT threads_pk PRIMARY KEY ( id_thread );

CREATE TABLE "user" (
    username         VARCHAR2(30 CHAR) NOT NULL,
    password         VARCHAR2(60) NOT NULL,
    email            VARCHAR2(100) NOT NULL,
    date_start       DATE NOT NULL,
    is_private       CHAR(1) NOT NULL,
    date_sus_start   DATE
);

ALTER TABLE "user" ADD CONSTRAINT users_pk PRIMARY KEY ( username );

CREATE TABLE vote (
    positive            CHAR(1) NOT NULL,
    negative            CHAR(1),
    users_username      VARCHAR2(30 CHAR) NOT NULL,
    threads_id_thread   VARCHAR2(15) NOT NULL
);

ALTER TABLE vote ADD CONSTRAINT votes_pk PRIMARY KEY ( users_username,
                                                       threads_id_thread );

ALTER TABLE achievement
    ADD CONSTRAINT achievements_users_fk FOREIGN KEY ( users_username )
        REFERENCES "user" ( username );

ALTER TABLE comments
    ADD CONSTRAINT comments_comments_fk FOREIGN KEY ( comments_id_comment,
                                                      comments_users_username )
        REFERENCES comments ( id_comment,
                              users_username );

ALTER TABLE comments
    ADD CONSTRAINT comments_threads_fk FOREIGN KEY ( threads_id_thread )
        REFERENCES thread ( id_thread );

ALTER TABLE comments
    ADD CONSTRAINT comments_users_fk FOREIGN KEY ( users_username )
        REFERENCES "user" ( username );

ALTER TABLE purchase
    ADD CONSTRAINT purchases_users_fk FOREIGN KEY ( users_username )
        REFERENCES "user" ( username )
            ON DELETE CASCADE;

ALTER TABLE thread
    ADD CONSTRAINT threads_category_fk FOREIGN KEY ( category_name )
        REFERENCES category ( name );

ALTER TABLE thread
    ADD CONSTRAINT threads_users_fk FOREIGN KEY ( users_username )
        REFERENCES "user" ( username )
            ON DELETE CASCADE;

ALTER TABLE vote
    ADD CONSTRAINT votes_threads_fk FOREIGN KEY ( threads_id_thread )
        REFERENCES thread ( id_thread );

ALTER TABLE vote
    ADD CONSTRAINT votes_users_fk FOREIGN KEY ( users_username )
        REFERENCES "user" ( username );

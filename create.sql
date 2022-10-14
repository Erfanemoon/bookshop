
    create table books (
       id bigint not null auto_increment,
        author varchar(100),
        barcode varchar(100),
        name varchar(100),
        price integer,
        quantity integer,
        primary key (id)
    ) engine=InnoDB;

    create table user_book (
       user_id bigint not null,
        book_id bigint not null,
        primary key (book_id, user_id)
    ) engine=InnoDB;

    create table users (
       id bigint not null auto_increment,
        address longtext,
        firstname varchar(100),
        lastname varchar(100),
        mailid varchar(100),
        password varchar(100),
        phone varchar(100),
        username varchar(100),
        usertype varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table user_book 
       add constraint FKpdj1sbs1l1n6mxv71humguylo 
       foreign key (book_id) 
       references books (id);

    alter table user_book 
       add constraint FKhon6i1tqj4tp43386dq6uo9ch 
       foreign key (user_id) 
       references users (id);

    create table books (
       id bigint not null auto_increment,
        author varchar(100),
        barcode varchar(100),
        name varchar(100),
        price integer,
        quantity integer,
        primary key (id)
    ) engine=InnoDB;

    create table user_book (
       user_id bigint not null,
        book_id bigint not null,
        primary key (book_id, user_id)
    ) engine=InnoDB;

    create table users (
       id bigint not null auto_increment,
        address longtext,
        firstname varchar(100),
        lastname varchar(100),
        mailid varchar(100),
        password varchar(100),
        phone varchar(100),
        username varchar(100),
        usertype varchar(255),
        primary key (id)
    ) engine=InnoDB;

    alter table user_book 
       add constraint FKpdj1sbs1l1n6mxv71humguylo 
       foreign key (book_id) 
       references books (id);

    alter table user_book 
       add constraint FKhon6i1tqj4tp43386dq6uo9ch 
       foreign key (user_id) 
       references users (id);

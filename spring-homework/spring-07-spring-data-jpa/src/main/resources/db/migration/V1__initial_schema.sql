create table author
(
    id   bigint not null,
    full_name varchar(50),
    primary key (id)
);
create sequence author_id_seq;
alter table author alter column id set default nextval('author_id_seq');

create table genre
(
    id   bigint not null,
    name varchar(50),
    primary key (id)
);
create sequence genre_id_seq;
alter table genre alter column id set default nextval('genre_id_seq');

create table book
(
    id       bigint not null,
    title    varchar(300),
    genre_id bigint references genre (id),
    primary key (id)
);
create sequence book_id_seq;
alter table book alter column id set default nextval('book_id_seq');

create table book_author
(
    book_id   bigint references book (id) on delete cascade,
    author_id bigint references author (id),
    primary key (book_id, author_id)
);

create table comment
(
    id bigint not null,
    comment varchar(4000),
    book_id bigint references book(id) on delete cascade,
    primary key (id)
);
create sequence comment_id_seq;
alter table comment alter column id set default nextval('comment_id_seq');
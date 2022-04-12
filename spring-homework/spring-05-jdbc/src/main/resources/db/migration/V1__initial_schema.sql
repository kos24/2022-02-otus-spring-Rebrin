create table author
(
    id   bigint,
    full_name varchar(50),
    primary key (id)
);

create table genre
(
    id   bigint,
    name varchar(50),
    primary key (id)
);

create table book
(
    id       bigint,
    title    varchar(300),
    genre_id bigint references genre (id),
    primary key (id)
);

create table book_author
(
    book_id   bigint references book (id) on delete cascade,
    author_id bigint references author (id),
    primary key (book_id, author_id)
);
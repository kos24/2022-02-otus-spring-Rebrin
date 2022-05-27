insert into genre(name)
values ('genre_name1'), ('genre_name2'), ('genre_name3');

insert into author(full_name)
values ('author_name_01'), ('author_name_02'), ('author_name_03');

insert into book(title, genre_id)
values ('book_01', 1), ('book_02', 2), ('book_03', 3);

insert into book_author(book_id, author_id)
values (1, 1),   (1, 2),   (1, 3),
       (2, 2),   (2, 3),
       (3, 1),   (3, 3);

insert into comment(comment, book_id)
values ('comment1', 1), ('comment2', 1),
       ('comment3', 2), ('comment4', 2),
       ('comment5', 3), ('comment6', 3)

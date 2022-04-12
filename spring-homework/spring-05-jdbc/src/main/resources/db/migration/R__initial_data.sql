insert into genre(id, name)
values (1,'genre_name1'), (2,'genre_name2'), (3,'genre_name3');

insert into author(id, full_name)
values (1,'author_name_01'), (2,'author_name_02'), (3,'author_name_03');

insert into book(id, title, genre_id)
values (1,'book_01', 1), (2,'book_02', 2), (3,'book_03', 3);

insert into book_author(book_id, author_id)
values (1, 1),   (1, 2),   (1, 3),
       (2, 2),   (2, 3),
       (3, 1),   (3, 3);

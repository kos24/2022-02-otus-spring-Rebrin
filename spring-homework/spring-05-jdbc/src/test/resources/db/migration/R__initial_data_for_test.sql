insert into genre(id,name)
values (4,'genre_name4'), (5,'genre_name5'), (6,'genre_name6');

insert into author(id,full_name)
values (4,'author_name_04'), (5,'author_name_05'), (6,'author_name_06');

insert into book(id, title, genre_id)
values (4,'book_04', 4), (5,'book_05', 5), (6,'book_06', 6);

insert into book_author(book_id, author_id)
values (4, 4),   (4, 5),   (4, 6),
       (5, 4),   (5, 5),
       (6, 6),   (6, 5);

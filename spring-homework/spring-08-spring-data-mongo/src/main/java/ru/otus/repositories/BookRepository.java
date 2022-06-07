package ru.otus.repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.models.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findBookByTitle(String title);

}

package ru.otus.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Document(collection = "comment")
public class Comment {

    @Id
    private String id;

    @Field(name = "comment")
    private String comment;

    @Field(name = "book")
    private Book book;

    public Comment(String comment, Book book) {
        this.comment = comment;
        this.book = book;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment c = (Comment) o;
        return id.equals(c.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", comment='" + comment + '\'' +
                ", book=" + book.getId() +
                '}';
    }
}

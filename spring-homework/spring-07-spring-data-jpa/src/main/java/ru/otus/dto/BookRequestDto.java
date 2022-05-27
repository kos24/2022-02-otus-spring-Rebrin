package ru.otus.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BookRequestDto {

    Long id;
    String title;
    String genreName;
    String[] authors;
}

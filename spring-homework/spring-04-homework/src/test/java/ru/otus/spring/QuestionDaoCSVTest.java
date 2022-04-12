package ru.otus.spring;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест QuestionDaoCSV")
@SpringBootTest
class QuestionDaoCSVTest {

    @Autowired
    private QuestionDao questionDao;

    @DisplayName("должен загрузить вопросы из файла")
    @Test
    void shouldReadQuestionsFromFile() {
        List<Question> questions = questionDao.readQuestions();

        assertThat(questions).isNotEmpty().hasSize(5);
        questions.stream()
                .map(Question::getQuestionText)
                .forEach(text -> assertThat(text).isNotNull());
    }

    @DisplayName("должен загрузить правильные ответы из файла")
    @Test
    void shouldLoadCorrectAnswersFromFile() {
        List<Question> questions = questionDao.readQuestions();

        assertThat(questions.stream()
                .map(Question::getCorrectAnswer)
                .collect(Collectors.toList()))
                .containsAnyOf("London", "France");
    }
}

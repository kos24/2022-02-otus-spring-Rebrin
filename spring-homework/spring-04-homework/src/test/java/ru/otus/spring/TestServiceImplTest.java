package ru.otus.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.service.IOService;
import ru.otus.spring.service.TestService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@DisplayName("Тестирование класса TestService")
@SpringBootTest(properties = {"pass.threshold=1", "lang.locale=en-EN"})
class TestServiceImplTest {

    private static final String NAME = "Ivan Ivanov";
    private static final String ANSWER1 = "London";
    private static final String ANSWER2 = "Atlantic";
    @MockBean
    private IOService ioService;
    @Autowired
    private TestService testService;
    @MockBean
    private QuestionDao questionDao;
    private final List<Question> questions = new ArrayList<>();

    @BeforeEach
    void setUp() {

        var answer1 = new Answer("Barcelona", false);
        var answer2 = new Answer("London", true);
        var question1 = new Question("What is the capital of Great Britain?", List.of(answer1, answer2));

        var answer3 = new Answer("Atlantic", false);
        var answer4 = new Answer("Arctic", true);
        var question2 = new Question("Which one is the smallest ocean in the World?", List.of(answer3, answer4));

        questions.add(question1);
        questions.add(question2);
    }

    @DisplayName("должен пройти тест, если даны правильные ответы")
    @Test
    void shouldPassTestWhenCorrectAnswers() {
        when(questionDao.readQuestions()).thenReturn(questions);
        when(ioService.read()).thenReturn(NAME, ANSWER1, ANSWER2);

        testService.doTest();

        verify(ioService, times(1))
                .print(String.format("Congratulations, %s! You've passed the test.", NAME));
    }

    @DisplayName("должен завалить тест, если даны неправильные ответы")
    @Test
    void shouldFailTestWhenWrongAnswers() {
        when(questionDao.readQuestions()).thenReturn(questions);
        when(ioService.read()).thenReturn(NAME, ANSWER2, ANSWER1);

        testService.doTest();

        verify(ioService, times(1))
                .print(String.format("Sorry, %s. You failed the test. Please try again later.", NAME));
    }
}
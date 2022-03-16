package ru.otus.spring.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

class TestServiceImplTest {

    private static final String NAME = "Ivan Ivanov";
    private static final String ANSWER1 = "London";
    private static final String ANSWER2 = "Atlantic";
    private IOService ioService;
    private TestService testService;
    private QuestionDao questionDao;
    private final List<Question> questions = new ArrayList<>();

    @BeforeEach
    void setUp() {

        ioService = mock(IOService.class);
        questionDao = mock(QuestionDao.class);
        testService = new TestServiceImpl(questionDao, ioService, 1);

        var answer1 = new Answer("Barcelona", false);
        var answer2 = new Answer("London", true);
        var question1 = new Question("What is the capital of Great Britain?", List.of(answer1, answer2));

        var answer3 = new Answer("Atlantic", false);
        var answer4 = new Answer("Arctic", true);
        var question2 = new Question("Which one is the smallest ocean in the World?", List.of(answer3, answer4));

        questions.add(question1);
        questions.add(question2);
    }

    @Test
    void shouldPassTestWhenCorrectAnswers() {
        when(questionDao.readQuestions()).thenReturn(questions);
        when(ioService.read()).thenReturn(NAME, ANSWER1, ANSWER2);

        testService.doTest();

        verify(ioService, times(1))
                .print(String.format("Congratulations, %s! You've passed the test.", NAME));
    }

    @Test
    void shouldFailTestWhenWrongAnswers() {
        when(questionDao.readQuestions()).thenReturn(questions);
        when(ioService.read()).thenReturn(NAME, ANSWER2, ANSWER1);

        testService.doTest();

        verify(ioService, times(1))
                .print(String.format("Sorry, %s. You failed the test. Please try again later.", NAME));
    }
}
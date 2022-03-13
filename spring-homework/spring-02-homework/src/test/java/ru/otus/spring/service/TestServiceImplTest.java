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
    private List<Question> questions = new ArrayList<>();
    private Question question1;
    private Question question2;
    private Answer answer1;
    private Answer answer2;
    private Answer answer3;
    private Answer answer4;

    @BeforeEach
    void setUp() {

        ioService = mock(IOService.class);
        questionDao = mock(QuestionDao.class);
        testService = new TestServiceImpl(questionDao, ioService, 1);
        answer1 = new Answer();
        answer1.setVariant("Barcelona");
        answer1.setCorrect(false);
        answer2 = new Answer();
        answer2.setVariant("London");
        answer2.setCorrect(true);

        answer3 = new Answer();
        answer3.setVariant("Atlantic");
        answer3.setCorrect(false);
        answer4 = new Answer();
        answer4.setVariant("Arctic");
        answer4.setCorrect(true);

        question1 = Question.builder()
                .questionText("What is the capital of Great Britain?")
                .possibleAnswers(answer1)
                .possibleAnswers(answer2)
                .build();
        question2 = Question.builder()
                .questionText("Which one is the smallest ocean in the World?")
                .possibleAnswers(answer3)
                .possibleAnswers(answer4)
                .build();
        questions.add(question1);
        questions.add(question2);

    }

    @Test
    void shouldPassTestWhenCorrectAnswers() {
        when(questionDao.readQuestions()).thenReturn(questions);
        when(ioService.read()).thenReturn(NAME,ANSWER1,ANSWER2);

        testService.doTest();

        verify(ioService,times(1))
                .print(String.format("Congratulations, %s! You've passed the test.", NAME));
    }

    @Test
    void shouldFailTestWhenWrongAnswers() {
        when(questionDao.readQuestions()).thenReturn(questions);
        when(ioService.read()).thenReturn(NAME,ANSWER2,ANSWER1);

        testService.doTest();

        verify(ioService,times(1))
                .print(String.format("Sorry, %s. You failed the test. Please try again later.", NAME));
    }
}
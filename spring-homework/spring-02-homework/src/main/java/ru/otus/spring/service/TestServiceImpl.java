package ru.otus.spring.service;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;

@Service
public class TestServiceImpl implements TestService {

    private final QuestionDao questionDao;
    private final IOService ioService;
    private final int passThreshold;

    public TestServiceImpl(QuestionDao questionDao,
                           IOService ioService,
                           @Value("${pass.threshold:3}") int passThreshold) {
        this.questionDao = questionDao;
        this.ioService = ioService;
        this.passThreshold = passThreshold;
    }

    public void doTest() {
        var correctAnswersCount = new AtomicInteger();
        String name = inputName();
        ioService.print(String.format("You need to answer %d questions in order to pass the test", this.passThreshold));
        processQuestions(correctAnswersCount);
        outputResults(correctAnswersCount, name);


    }

    private String inputName() {
        ioService.print("Please enter your name and lastname:");
        return ioService.read();
    }

    private void processQuestions(AtomicInteger correctAnswersCount) {

        questionDao.readQuestions().forEach(question -> {
            ioService.print(question.getQuestionText());
            question.getPossibleAnswers().forEach(answer -> ioService.print(answer.getVariant()));
            ioService.print("Please enter your answer:");
            String answer = ioService.read();
            if (answer.equals(question.getCorrectAnswer())) {
                correctAnswersCount.getAndIncrement();
            }
        });
    }

    private void outputResults(AtomicInteger correctAnswersCount, String name) {
        if (correctAnswersCount.get() >= this.passThreshold) {
            ioService.print(String.format("Congratulations, %s! You've passed the test.", name));
        } else {
            ioService.print(String.format("Sorry, %s. You failed the test. Please try again later.", name));
        }
        ioService.print(String.format("Correct answers: %d/%d", correctAnswersCount.get(), this.questionDao.readQuestions().size()));
    }
}
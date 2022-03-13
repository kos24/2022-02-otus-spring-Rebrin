package ru.otus.spring.service;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;

@Service
public class TestServiceImpl implements TestService {

    private final QuestionDao questionDao;
    private final IOService ioService = new IOServiceConsole(System.out, System.in);

    @Value("${pass.threshold:3}")
    private int passThreshold;

    public TestServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void doTest() {
        AtomicInteger correctAnswersCount = new AtomicInteger();
        ioService.print("Please enter your name and lastname:");
        String name = ioService.read();
        ioService.print(String.format("You need to answer %d questions in order to pass the test", this.passThreshold));
        this.questionDao.readQuestions().forEach(question -> {
            ioService.print(question.getQuestionAsked());
            ioService.print(question.getPossibleAnswers());
            ioService.print("Please enter your answer: (type only the number of correct answer)");
            int answer = ioService.readInt();
            if (answer == Integer.parseInt(question.getAnswer())) {
                correctAnswersCount.getAndIncrement();
            }
        });
        if (correctAnswersCount.get() >= this.passThreshold) {
            ioService.print(String.format("Congratulations, %s! You've passed the test.", name));
            ioService.print(String.format("Correct answers: %d/%d", correctAnswersCount.get(), this.questionDao.readQuestions().size()));
        } else {
            ioService.print(String.format("Sorry, %s. You failed the test. Please try again later.", name));
            ioService.print(String.format("Correct answers: %d/%d", correctAnswersCount.get(), this.questionDao.readQuestions().size()));
        }

    }
}
package ru.otus.spring.service;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;

@Service
public class TestServiceImpl implements TestService {

    private final QuestionDao questionDao;

    @Value("${pass.threshold:3}")
    private int passThreshold;

    public TestServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void doTest() {
        Scanner scanner = new Scanner(System.in);
        AtomicInteger correctAnswersCount = new AtomicInteger();
        System.out.println("Please enter your name and lastname:");
        String name = scanner.nextLine();
        System.out.printf("You need to answer %d questions in order to pass the test%n", this.passThreshold);
        this.questionDao.readQuestions().forEach(question -> {
            System.out.println(question.getQuestionAsked());
            System.out.println(question.getPossibleAnswers());
            System.out.println("Please enter your answer: (type only the number of correct answer)");
            int answer = scanner.nextInt();
            if (answer == Integer.parseInt(question.getAnswer())) {
                correctAnswersCount.getAndIncrement();
            }

        });
        if (correctAnswersCount.get() >= this.passThreshold) {
            System.out.printf("Congratulations, %s! You've passed the test.%n", name);
            System.out.printf("Correct answers: %d/%d", correctAnswersCount.get(), this.questionDao.readQuestions().size());
        } else {
            System.out.printf("Sorry, %s. You failed the test. Please try again later.%n", name);
            System.out.printf("Correct answers: %d/%d", correctAnswersCount.get(), this.questionDao.readQuestions().size());
        }

    }
}
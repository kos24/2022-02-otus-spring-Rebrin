package ru.otus.spring.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestResult;

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
        String name = inputName();
        ioService.print(String.format("You need to answer %d questions in order to pass the test", this.passThreshold));
        List<Question> questions = questionDao.readQuestions();
        TestResult testResult = askQuestions(questions);
        outputResults(testResult, name);
    }

    private String inputName() {
        ioService.print("Please enter your name and lastname:");
        return ioService.read();
    }

    private TestResult askQuestions(List<Question> questions) {

        TestResult testResult = new TestResult(questions.size());

        questions.forEach(question -> {
            outputQuestion(question);
            ioService.print("Please enter your answer:");
            String answer = ioService.read();
            if (answer.equals(question.getCorrectAnswer())) {
                testResult.incrementCorrectAnswersCount();
            }
        });
        return testResult;
    }

    private void outputQuestion(Question question) {
        ioService.print(question.getQuestionText());
        question.getPossibleAnswers().forEach(answer -> ioService.print(answer.getVariant()));
    }

    private void outputResults(TestResult testResult, String name) {
        if (testResult.getCorrectAnswersCount() >= this.passThreshold) {
            ioService.print(String.format("Congratulations, %s! You've passed the test.", name));
        } else {
            ioService.print(String.format("Sorry, %s. You failed the test. Please try again later.", name));
        }
        ioService.print(String.format("Correct answers: %d/%d", testResult.getCorrectAnswersCount(), testResult.getMaxPoints()));
    }
}
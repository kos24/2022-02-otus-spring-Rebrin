package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestResult;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final PrintWithLocaleService printer;
    private final IOService ioService;
    private final QuestionDao questionDao;
    private final int passThreshold;

    public TestServiceImpl(PrintWithLocaleService printer,
                           IOService ioService,
                           QuestionDao questionDao,
                           @Value("${pass.threshold:3}") int passThreshold) {

        this.printer = printer;
        this.ioService = ioService;
        this.questionDao = questionDao;
        this.passThreshold = passThreshold;
    }

    public void doTest() {
        String name = inputName();
        printer.printLocalised("lang.test.threshold", String.valueOf(passThreshold));
        List<Question> questions = questionDao.readQuestions();
        TestResult testResult = askQuestions(questions);
        outputResults(testResult, name);
    }

    private String inputName() {
        printer.printLocalised("lang.intro");
        return ioService.read();
    }

    private TestResult askQuestions(List<Question> questions) {

        TestResult testResult = new TestResult(questions.size());

        questions.forEach(question -> {
            outputQuestion(question);
            printer.printLocalised("lang.answer.prompt");
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
            printer.printLocalised("lang.result.congrats", name);
        } else {
            printer.printLocalised("lang.result.failure", name);
        }
        printer.printLocalised("lang.result", String.valueOf(testResult.getCorrectAnswersCount())
                , String.valueOf(testResult.getMaxPoints()));
    }
}
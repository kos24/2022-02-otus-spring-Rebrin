package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestResult;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final QuestionDao questionDao;
    private final IOService ioService;
    private final int passThreshold;
    private final LocaleService localeService;

    public TestServiceImpl(QuestionDao questionDao,
                           IOService ioService,
                           @Value("${pass.threshold:3}") int passThreshold,
                           LocaleService localeService) {
        this.questionDao = questionDao;
        this.ioService = ioService;
        this.passThreshold = passThreshold;
        this.localeService = localeService;
    }

    public void doTest() {
        String name = inputName();
        ioService.print(localeService.translateMessage("lang.test.threshold", String.valueOf(passThreshold)));
        List<Question> questions = questionDao.readQuestions();
        TestResult testResult = askQuestions(questions);
        outputResults(testResult, name);
    }

    private String inputName() {
        ioService.print(localeService.translateMessage("lang.intro"));
        return ioService.read();
    }

    private TestResult askQuestions(List<Question> questions) {

        TestResult testResult = new TestResult(questions.size());

        questions.forEach(question -> {
            outputQuestion(question);
            ioService.print(localeService.translateMessage("lang.answer.prompt"));
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
            ioService.print(localeService.translateMessage("lang.result.congrats",name));
        } else {
            ioService.print(localeService.translateMessage("lang.result.failure", name));
        }
        ioService.print(localeService.translateMessage("lang.result", String.valueOf(testResult.getCorrectAnswersCount())
                    ,String.valueOf(testResult.getMaxPoints())));
    }
}
package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.domain.Question;
import ru.otus.spring.domain.TestResult;

import java.util.List;
import java.util.Locale;

@Service
public class TestServiceImpl implements TestService {

    private final QuestionDao questionDao;
    private final IOService ioService;
    private final int passThreshold;
    private final MessageSource messageSource;
    private final String region;

    public TestServiceImpl(QuestionDao questionDao,
                           IOService ioService,
                           @Value("${pass.threshold:3}") int passThreshold,
                           MessageSource messageSource,
                           @Value("${lang.locale:en-EN}")
                                   String region) {
        this.questionDao = questionDao;
        this.ioService = ioService;
        this.passThreshold = passThreshold;
        this.messageSource = messageSource;
        this.region = region;
    }

    public void doTest() {
        String name = inputName();
        ioService.print(messageSource.getMessage("lang.test.threshold", new String[]{String.valueOf(passThreshold)}, Locale.forLanguageTag(region)));
        List<Question> questions = questionDao.readQuestions();
        TestResult testResult = askQuestions(questions);
        outputResults(testResult, name);
    }

    private String inputName() {
        ioService.print(messageSource.getMessage("lang.intro", new String[]{}, Locale.forLanguageTag(region)));
        return ioService.read();
    }

    private TestResult askQuestions(List<Question> questions) {

        TestResult testResult = new TestResult(questions.size());

        questions.forEach(question -> {
            outputQuestion(question);
            ioService.print(messageSource.getMessage("lang.answer.prompt", new String[]{}, Locale.forLanguageTag(region)));
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
            ioService.print(messageSource.getMessage("lang.result.congrats", new String[]{name}, Locale.forLanguageTag(region)));
        } else {
            ioService.print(messageSource.getMessage("lang.result.failure", new String[]{name}, Locale.forLanguageTag(region)));
        }
        ioService.print(messageSource.getMessage("lang.result", new String[]{String.valueOf(testResult.getCorrectAnswersCount())
                , String.valueOf(testResult.getMaxPoints())}, Locale.forLanguageTag(region)));
    }
}
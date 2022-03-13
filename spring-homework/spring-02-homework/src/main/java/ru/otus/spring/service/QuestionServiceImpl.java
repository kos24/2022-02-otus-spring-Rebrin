package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionDao questionDao;
    private final IOService ioService = new IOServiceConsole(System.out, System.in);

    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    public void printQuestions() {
        questionDao.readQuestions().forEach(question ->
                ioService.print(String.format("Question: %s%nPossible answers: %s",
                        question.getQuestionAsked(), question.getPossibleAnswers())));
    }

}
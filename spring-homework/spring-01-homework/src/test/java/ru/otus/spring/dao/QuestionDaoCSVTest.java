package ru.otus.spring.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.otus.spring.domain.Question;

import java.util.List;

class QuestionDaoCSVTest {

    private QuestionDao questionDao;

    @BeforeEach
    void setUp(){
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("/spring-context.xml");
        questionDao = context.getBean(QuestionDao.class);
    }

    @Test
    void readQuestions() {

        List<Question> questions = questionDao.readQuestions();

        Assertions.assertEquals(5, questions.size());
    }
}
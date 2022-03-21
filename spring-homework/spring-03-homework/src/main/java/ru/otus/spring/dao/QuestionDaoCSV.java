package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exception.QuestionLoadingException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Repository
public class QuestionDaoCSV implements QuestionDao {

    private final String path;

    public QuestionDaoCSV(@Value("questions_${lang.locale}.csv") String path) {
        this.path = path;
    }

    @Override
    public List<Question> readQuestions() {
        String line;
        String splitter = ",";
        List<Question> questions = new ArrayList<>();
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(path);
            if (Objects.nonNull(inputStream)) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
                    while ((line = br.readLine()) != null) {
                        List<Answer> answers = new ArrayList<>();
                        String[] questionSplitted = line.split(splitter);
                        String[] possibleAnswers = questionSplitted[1].split("\\$");
                        String correctAnswer = questionSplitted[2];
                        Arrays.stream(possibleAnswers).forEach(a -> {
                            Answer answer;
                            if (correctAnswer.equals(a)) {
                                answer = new Answer(a, true);
                            } else {
                                answer = new Answer(a, false);
                            }
                            answers.add(answer);
                        });
                        Question question = new Question(questionSplitted[0], answers);
                        questions.add(question);
                    }
                }
            }
        } catch (Exception e) {
            throw new QuestionLoadingException(e);
        }
        return questions;
    }

}
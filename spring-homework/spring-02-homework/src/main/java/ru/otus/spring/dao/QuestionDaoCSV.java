package ru.otus.spring.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Repository;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exception.QuestionLoadingException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class QuestionDaoCSV implements QuestionDao {

    private final Resource csvFile;

    public QuestionDaoCSV(@Value("classpath:${path.to.csv:questions.csv}") Resource csvFile) {
        this.csvFile = csvFile;
    }

    @Override
    public List<Question> readQuestions() {
        URL url;
        String line;
        String splitter = ",";
        List<Question> questions = new ArrayList<>();
        try {
            url = csvFile.getURL();

            try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
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
        } catch (Exception e) {
            throw new QuestionLoadingException(e);
        }
        return questions;
    }

}
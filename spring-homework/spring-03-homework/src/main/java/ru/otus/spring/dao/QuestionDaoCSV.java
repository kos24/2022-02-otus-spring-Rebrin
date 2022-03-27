package ru.otus.spring.dao;

import org.springframework.stereotype.Repository;
import ru.otus.spring.config.FileProvider;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.exception.QuestionLoadingException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

@Repository
public class QuestionDaoCSV implements QuestionDao {

    private final FileProvider fileProvider;

    public QuestionDaoCSV(FileProvider fileProvider) {
        this.fileProvider = fileProvider;
    }

    @Override
    public List<Question> readQuestions() {
        String line;
        String splitter = ",";
        List<Question> questions = new ArrayList<>();
        try {
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileProvider.getFileName());
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
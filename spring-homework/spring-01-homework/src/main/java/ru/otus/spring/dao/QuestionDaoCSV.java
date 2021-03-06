package ru.otus.spring.dao;

import org.springframework.core.io.Resource;
import ru.otus.spring.domain.Question;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class QuestionDaoCSV implements QuestionDao {

    private final Resource csvFile;

    public QuestionDaoCSV(Resource csvFile) {
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
                    String[] questionSplitted = line.split(splitter);
                    Question question = new Question(questionSplitted[0], questionSplitted[1]);
                    questions.add(question);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questions;
    }

}
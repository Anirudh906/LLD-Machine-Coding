package com.example.conditionalgoogleform.model;

import java.util.List;
import java.util.Optional;

public class Form {
  List<Question> questions;
  String formId;

  public Form(List<Question> questions, String formId) {
    this.questions = questions;
    this.formId = formId;
  }

  public List<Question> getAllQuestions() {
    return questions;
  }

  public Optional<Question> getQuestion(String questionId) {
    return questions.stream()
        .filter(question -> question.getQuestionId().equals(questionId))
        .findFirst();
  }
}

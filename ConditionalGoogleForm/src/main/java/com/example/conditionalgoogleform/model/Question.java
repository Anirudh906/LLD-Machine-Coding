package com.example.conditionalgoogleform.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Question {
  String description;
  List<String> options;
  String questionId;
  List<Condition> visibilityCondition;

  public String getQuestionId() {
    return questionId;
  }

  public Question(String description, String questionId, List<String> options) {
    this.description = description;
    this.options = options;
    this.questionId = questionId;
    this.visibilityCondition = new ArrayList<>();
  }

  public void addCondition(Condition condition) {
    visibilityCondition.add(condition);
  }

  public Boolean getVisibility(Map<String, String> userAnswers) {
    for (Condition currCondition : visibilityCondition) {
      String dependentQuestion = currCondition.getDependentQuestionId();
      String userAnswer = userAnswers.get(dependentQuestion);
      if (Objects.isNull(userAnswer)
          || !userAnswer.equalsIgnoreCase(currCondition.getDependentOption())) {
        return false;
      }
    }
    return true;
  }
}

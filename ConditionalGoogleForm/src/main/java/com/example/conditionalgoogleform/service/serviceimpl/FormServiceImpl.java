package com.example.conditionalgoogleform.service.serviceimpl;

import com.example.conditionalgoogleform.model.Condition;
import com.example.conditionalgoogleform.model.Form;
import com.example.conditionalgoogleform.model.Question;
import com.example.conditionalgoogleform.service.FormService;
import java.util.*;

public class FormServiceImpl implements FormService {
  Form form;

  @Override
  public void createFormTemplate(Map<String, List<String>> questions, String formId) {
    if (questions.isEmpty()) {
      throw new IllegalArgumentException("At least 1 question should be present!");
    }
    List<Question> updatedQuestions = new ArrayList<>();
    int questionIndex = 1;
    for (Map.Entry<String, List<String>> question : questions.entrySet()) {
      String questionId = getQuestionId(questionIndex);
      String description = question.getKey();
      List<String> options = question.getValue();
      Question newQuestion = new Question(description, questionId, options);
      updatedQuestions.add(newQuestion);
      questionIndex++;
    }
    form = new Form(updatedQuestions, formId);
  }

  @Override
  public void addCondition(Map<String, List<String>> conditions) {
    if (conditions.isEmpty()) {
      throw new IllegalArgumentException("At least 1 condition should be present!");
    }
    for (Map.Entry<String, List<String>> condition : conditions.entrySet()) {
      Optional<Question> question = form.getQuestion(condition.getKey());
      if (question.isEmpty()) {
        throw new IllegalArgumentException("Question with given condition doesnt exist");
      }
      List<String> conditionRule = condition.getValue();
      Condition conditionEntity =
          new Condition(conditionRule.get(0), conditionRule.get(conditionRule.size() - 1));
      question.get().addCondition(conditionEntity);
    }
  }

  @Override
  public Map<String, Boolean> getQuestionVisibility(Map<String, String> userResponses) {
    Map<String, Boolean> questionVisibility = new HashMap<>();
    List<Question> questions = form.getAllQuestions();
    for (Question question : questions) {
      questionVisibility.put(question.getQuestionId(), question.getVisibility(userResponses));
    }
    return questionVisibility;
  }

  @Override
  public Map<String, String> getFinalResponses(Map<String, String> submittedUserResponses) {
    Map<String, String> finalResponses = new HashMap<>();
    Map<String, Boolean> visibility = getQuestionVisibility(submittedUserResponses);
    List<Question> questions = form.getAllQuestions();
    for (Question question : questions) {
      String id = question.getQuestionId();
      if (visibility.get(id) && submittedUserResponses.containsKey(id)) {
        finalResponses.put(id, submittedUserResponses.get(id));
      }
    }
    return finalResponses;
  }

  private String getQuestionId(int index) {
    return "Q" + index;
  }
}

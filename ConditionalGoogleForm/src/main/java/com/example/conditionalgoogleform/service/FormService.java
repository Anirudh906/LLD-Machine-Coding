package com.example.conditionalgoogleform.service;

import java.util.List;
import java.util.Map;

public interface FormService {
  // createFormTemplate, addQuestions, addCondition, getQuestionVisibility, submitForm

  void createFormTemplate(Map<String, List<String>> questions, String formId);

  void addCondition(Map<String, List<String>> condition);

  Map<String, Boolean> getQuestionVisibility(Map<String, String> userResponses);

  Map<String, String> getFinalResponses(Map<String, String> userResponses);
}

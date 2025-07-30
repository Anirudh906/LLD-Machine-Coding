package com.example.conditionalgoogleform;

import com.example.conditionalgoogleform.service.FormService;
import com.example.conditionalgoogleform.service.serviceimpl.FormServiceImpl;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConditionalGoogleForm {

  public static void main(String[] args) {
    FormService formService = new FormServiceImpl();

    // Create form
    Map<String, List<String>> questions = new HashMap<>();
    questions.put("Select a fruit", Arrays.asList("Apple", "Banana", "Guava", "Papaya"));
    questions.put("Select type of apple", Arrays.asList("Red apple", "Green apple"));
    questions.put("Select type of banana", Arrays.asList("Big banana", "Small banana"));
    questions.put("Select type of Guava", Arrays.asList("Green guava", "Pink guava"));
    questions.put(
        "Select type of papaya",
        Arrays.asList("Red papaya", "Green papaya", "Yellow papaya", "Orange papaya"));
    formService.createFormTemplate(questions, "FormTemplateFruits");

    // Add condition for question
    Map<String, List<String>> conditions = new HashMap<>();
    conditions.put("Q2", Arrays.asList("Q1", "Apple"));
    conditions.put("Q3", Arrays.asList("Q1", "Banana"));
    conditions.put("Q4", Arrays.asList("Q1", "Guava"));
    conditions.put("Q5", Arrays.asList("Q1", "Papaya"));
    formService.addCondition(conditions);

    Map<String, String> partialResponses = Map.of("Q1", "Apple");
    Map<String, Boolean> visibility = formService.getQuestionVisibility(partialResponses);
    for (Map.Entry<String, Boolean> visibleQuestion : visibility.entrySet()) {
      if (visibleQuestion.getValue()) {
        System.out.println(visibleQuestion.getKey());
      }
    }

    // Final responses
    Map<String, String> userResponses = new HashMap<>();
    userResponses.put("Q1", "Papaya");
    userResponses.put("Q2", "Red apple");
    userResponses.put("Q3", "Big banana");
    userResponses.put("Q4", "Green guava");
    userResponses.put("Q5", "Red papaya");
    Map<String, String> responses = formService.getFinalResponses(userResponses);

    for (Map.Entry<String, String> response : responses.entrySet()) {
      System.out.println(response.getKey() + " :: " + response.getValue());
    }
  }
}

package com.example.conditionalgoogleform.model;

public class Condition {
  String dependentQuestionId;
  String dependentOption;

  public Condition(String dependentQuestionId, String dependentOption) {
    this.dependentQuestionId = dependentQuestionId;
    this.dependentOption = dependentOption;
  }

  public String getDependentQuestionId() {
    return dependentQuestionId;
  }

  public void setDependentQuestionId(String dependentQuestionId) {
    this.dependentQuestionId = dependentQuestionId;
  }

  public String getDependentOption() {
    return dependentOption;
  }

  public void setDependentOption(String dependentOption) {
    this.dependentOption = dependentOption;
  }
}

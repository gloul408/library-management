package com.library.org.requests;

import java.util.List;

public class SubmitBookRequest {

  private List<Integer> bookIds;
  private String userEmailId;

  public List<Integer> getBookIds() {
    return bookIds;
  }

  public void setBookIds(List<Integer> bookIds) {
    this.bookIds = bookIds;
  }

  public String getUserEmailId() {
    return userEmailId;
  }

  public void setUserEmailId(String userEmailId) {
    this.userEmailId = userEmailId;
  }
}

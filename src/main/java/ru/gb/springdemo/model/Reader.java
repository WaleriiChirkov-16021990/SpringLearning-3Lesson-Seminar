package ru.gb.springdemo.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Reader {

  public static long sequence = 1L;

  private final long id;
  private final String name;
  private final List<Book> bookList = new ArrayList<Book>();
  private final List<Issue> issueList = new ArrayList<Issue>();

  public Reader(String name) {
    this(sequence++, name);
  }

}

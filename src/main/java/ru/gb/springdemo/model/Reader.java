package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "Reader")
public class Reader {

  public static long sequence = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  @OneToMany
  private List<Book> bookList = new ArrayList<Book>();

  @OneToMany
  private List<Issue> issueList = new ArrayList<Issue>();

  public Reader(String name) {
    this(sequence++, name);
  }

  public Reader(long l, String name) {
  }
}

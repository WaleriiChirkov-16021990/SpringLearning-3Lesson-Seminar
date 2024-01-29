package ru.gb.springdemo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "Book")
public class Book {

  public static long sequence = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  long id;
  private String name;

  public Book(String name) {
    this(sequence++, name);
  }

  public Book() {

  }

  public Book(long l, String name) {
  }
}

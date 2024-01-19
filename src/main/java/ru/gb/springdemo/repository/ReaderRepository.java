package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Reader;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@Repository
public class ReaderRepository {
    private final List<Reader> readers;

    public ReaderRepository() {
        this.readers = new ArrayList<>();
    }

    @PostConstruct
    public void generateData() {
        readers.addAll(List.of(
                new ru.gb.springdemo.model.Reader("Игорь"),
                new ru.gb.springdemo.model.Reader("Валерий"),
                new ru.gb.springdemo.model.Reader("Михаил"),
                new ru.gb.springdemo.model.Reader("Димон")
        ));
    }

    public Reader getReaderById(long id) {
        return readers.stream().filter(it -> Objects.equals(it.getId(), id))
                .findFirst()
                .orElse(null);
    }

    public void saveReader(Reader reader) {
        readers.add(reader);
    }

    public List<Reader> getReaders() {
        return readers;
    }

    public Reader deleteById(long id) {
        Reader reader = null;
        for (Iterator<Reader> it = readers.iterator();
             it.hasNext(); ) {
            Reader tempReader = it.next();
            if (tempReader.getId() == id) {
                reader = tempReader;
                it.remove();
                break;
            }
        }
        return reader;
    }

}

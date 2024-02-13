package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReaderService {

    // спринг это все заинжектит
    private final ReaderRepository readerRepository;

    public Reader save(Reader reader) {
        if (reader == null) {
            throw new IllegalArgumentException("reader must not be null");
        }
        if (readerRepository.getReaderById(reader.getId()) != null) {
            throw new RuntimeException("This reader already exist");
        }
        readerRepository.saveReader(reader);
        return reader;
    }

    public Reader getReaderById(long id) {
        return readerRepository.getReaderById(id);
    }

    public Reader deleteReader(long id) {
        return readerRepository.deleteById(id);
    }

    public List<Reader> getReaders() {
        return readerRepository.getReaders();
    }

}

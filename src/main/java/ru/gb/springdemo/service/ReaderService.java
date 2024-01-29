package ru.gb.springdemo.service;

import lombok.RequiredArgsConstructor;
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
        if (readerRepository.findById(reader.getId()).isPresent()) {
            throw new RuntimeException("This reader already exist");
        }
        return readerRepository.save(reader);
    }

    public Reader getReaderById(long id) {
        return readerRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    public Reader deleteReader(long id) {
        Reader reader = getReaderById(id);
        readerRepository.deleteById(id);
        return reader;
    }

    public List<Reader> getReaders() {
        return readerRepository.findAll();
    }

}

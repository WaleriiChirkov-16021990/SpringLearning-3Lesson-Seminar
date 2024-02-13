package ru.gb.springdemo.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.ReaderRepository;

@ContextConfiguration(classes = {ReaderService.class})
@ExtendWith(SpringExtension.class)
class ReaderServiceDiffblueTest {
    @MockBean
    private ReaderRepository readerRepository;

    @Autowired
    private ReaderService readerService;

    /**
     * Method under test: {@link ReaderService#save(Reader)}
     */
    @Test
    void testSave() {
        // Arrange
        when(readerRepository.getReaderById(anyLong())).thenReturn(new Reader("Name"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> readerService.save(new Reader("Name")));
        verify(readerRepository).getReaderById(anyLong());
    }

    /**
     * Method under test: {@link ReaderService#save(Reader)}
     */
    @Test
    void testSave2() {
        // Arrange
        when(readerRepository.getReaderById(anyLong())).thenReturn(null);
        doNothing().when(readerRepository).saveReader(Mockito.<Reader>any());
        Reader reader = new Reader("Name");

        // Act
        Reader actualSaveResult = readerService.save(reader);

        // Assert
        verify(readerRepository).getReaderById(anyLong());
        verify(readerRepository).saveReader(Mockito.<Reader>any());
        assertSame(reader, actualSaveResult);
    }

    /**
     * Method under test: {@link ReaderService#save(Reader)}
     */
    @Test
    void testSave3() {
        // Arrange, Act and Assert
        assertThrows(IllegalArgumentException.class, () -> readerService.save(null));
    }

    /**
     * Method under test: {@link ReaderService#save(Reader)}
     */
    @Test
    void testSave4() {
        // Arrange
        when(readerRepository.getReaderById(anyLong())).thenReturn(new Reader("Name"));
        Reader reader = mock(Reader.class);
        when(reader.getId()).thenReturn(1L);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> readerService.save(reader));
        verify(reader).getId();
        verify(readerRepository).getReaderById(anyLong());
    }

    /**
     * Method under test: {@link ReaderService#save(Reader)}
     */
    @Test
    void testSave5() {
        // Arrange
        Reader reader = mock(Reader.class);
        when(reader.getId()).thenThrow(new IllegalArgumentException("This reader already exist"));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> readerService.save(reader));
        verify(reader).getId();
    }

    /**
     * Method under test: {@link ReaderService#getReaderById(long)}
     */
    @Test
    void testGetReaderById() {
        // Arrange
        Reader reader = new Reader("Name");
        when(readerRepository.getReaderById(anyLong())).thenReturn(reader);

        // Act
        Reader actualReaderById = readerService.getReaderById(1L);

        // Assert
        verify(readerRepository).getReaderById(anyLong());
        assertSame(reader, actualReaderById);
    }

    /**
     * Method under test: {@link ReaderService#getReaderById(long)}
     */
    @Test
    void testGetReaderById2() {
        // Arrange
        when(readerRepository.getReaderById(anyLong())).thenThrow(new IllegalArgumentException("foo"));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> readerService.getReaderById(1L));
        verify(readerRepository).getReaderById(anyLong());
    }

    /**
     * Method under test: {@link ReaderService#deleteReader(long)}
     */
    @Test
    void testDeleteReader() {
        // Arrange
        Reader reader = new Reader("Name");
        when(readerRepository.deleteById(anyLong())).thenReturn(reader);

        // Act
        Reader actualDeleteReaderResult = readerService.deleteReader(1L);

        // Assert
        verify(readerRepository).deleteById(anyLong());
        assertSame(reader, actualDeleteReaderResult);
    }

    /**
     * Method under test: {@link ReaderService#deleteReader(long)}
     */
    @Test
    void testDeleteReader2() {
        // Arrange
        when(readerRepository.deleteById(anyLong())).thenThrow(new IllegalArgumentException("foo"));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> readerService.deleteReader(1L));
        verify(readerRepository).deleteById(anyLong());
    }

    /**
     * Method under test: {@link ReaderService#getReaders()}
     */
    @Test
    void testGetReaders() {
        // Arrange
        ArrayList<Reader> readerList = new ArrayList<>();
        when(readerRepository.getReaders()).thenReturn(readerList);

        // Act
        List<Reader> actualReaders = readerService.getReaders();

        // Assert
        verify(readerRepository).getReaders();
        assertTrue(actualReaders.isEmpty());
        assertSame(readerList, actualReaders);
    }

    /**
     * Method under test: {@link ReaderService#getReaders()}
     */
    @Test
    void testGetReaders2() {
        // Arrange
        when(readerRepository.getReaders()).thenThrow(new IllegalArgumentException("foo"));

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> readerService.getReaders());
        verify(readerRepository).getReaders();
    }
}

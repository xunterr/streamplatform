package com.xunterr.stream.service;

import com.xunterr.stream.model.Stream;
import com.xunterr.stream.repository.StreamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StreamServiceTest {

    @Mock
    private StreamRepository repository;
    private StreamService underTest;

    @BeforeEach
    void beforeEach() {
        this.underTest = new StreamService(repository);
    }

    @Test
    void canGetAll() {
        underTest.getAll();
        verify(repository).findAll();
    }

    @Test
    void canGetById() {
        UUID id = UUID.randomUUID();
        Stream stream = Stream.builder()
                .title("Test")
                .description("Test")
                .userID(UUID.randomUUID())
                .build();

        given(repository.findById(id)).willReturn(Optional.of(stream));
        Stream actual = underTest.getById(id);

        verify(repository).findById(id);
        assertThat(actual).isEqualTo(stream);
    }

    @Test
    void canCreate() {
        Stream stream = Stream.builder()
                .title("Test")
                .description("Test")
                .userID(UUID.randomUUID())
                .build();

        underTest.create(stream);

        ArgumentCaptor<Stream> argumentCaptor = ArgumentCaptor.forClass(Stream.class);
        verify(repository).saveAndFlush(argumentCaptor.capture());

        Stream captured = argumentCaptor.getValue();
        assertThat(captured).isEqualTo(stream);
    }
}
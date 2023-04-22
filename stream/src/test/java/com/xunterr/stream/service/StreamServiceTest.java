package com.xunterr.stream.service;

import com.xunterr.stream.dto.CreateStreamRequest;
import com.xunterr.stream.key.AESStreamKeyGenerator;
import com.xunterr.stream.model.Stream;
import com.xunterr.stream.repository.StreamRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
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

    @Mock
    private AESStreamKeyGenerator keyGenerator;


    @InjectMocks
    private StreamService underTest;

    @Test
    void canGetAll() {
        underTest.getAll();
        verify(repository).findAll();
    }

    @Test
    void canGetById() {
        UUID id = UUID.randomUUID();
        Stream stream = new Stream(
                UUID.randomUUID(),
                "Test",
                "Test",
                false,
                new AESStreamKeyGenerator()
        );

        given(repository.findById(id)).willReturn(Optional.of(stream));
        Stream actual = underTest.getById(id);

        verify(repository).findById(id);
        assertThat(actual).isEqualTo(stream);
    }

    @Test
    void canCreate() {
        CreateStreamRequest request = new CreateStreamRequest(
                UUID.randomUUID(),
                "Test",
                "Test",
                false);

        underTest.create(request);

        ArgumentCaptor<Stream> argumentCaptor = ArgumentCaptor.forClass(Stream.class);
        verify(repository).saveAndFlush(argumentCaptor.capture());

        Stream captured = argumentCaptor.getValue();
        assertThat(captured.getUserID()).isEqualTo(request.getUserID());
    }
}
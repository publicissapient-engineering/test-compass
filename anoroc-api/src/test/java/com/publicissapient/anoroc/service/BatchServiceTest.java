package com.publicissapient.anoroc.service;

import com.publicissapient.anoroc.factory.BatchFactory;
import com.publicissapient.anoroc.model.Batch;
import com.publicissapient.anoroc.payload.request.PaginationRequest;
import com.publicissapient.anoroc.repository.BatchRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static com.publicissapient.anoroc.factory.BatchFactory.createEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

@SpringBootTest(webEnvironment = NONE)
public class BatchServiceTest {

    @Autowired
    BatchService service;

    @MockBean
    BatchRepository repository;

    @Test
    void should_save_batch_with_multiple_buisniness_scenarios() {
        when(repository.save(any())).thenReturn(createEntity());
        assertThat(service.save(createEntity()).getId()).isEqualTo(1L);
    }

    @Test
    void should_return_batch_count() {
        when(repository.count()).thenReturn(10l);
        assertThat(service.count()).isEqualTo(10l);
    }

    @Test
    void should_return_batch_list() {
        when(repository.findAll(any(Pageable.class))).thenReturn(mock(Page.class));
        assertThat(service.list(PaginationRequest.builder().page(0).size(5).build()).size()).isEqualTo(0);
    }

    @Test
    void should_return_batch_by_id() {
        when(repository.findById(1L)).thenReturn(Optional.of(BatchFactory.createEntity()));
        Batch batch = service.get(1L);
        assertThat(batch.getId()).isEqualTo(BatchFactory.createEntity().getId());
        assertThat(batch.getApplication().getName()).isEqualTo(BatchFactory.createEntity().getApplication().getName());

    }
}

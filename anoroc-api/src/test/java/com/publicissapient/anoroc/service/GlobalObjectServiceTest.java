package com.publicissapient.anoroc.service;


import com.publicissapient.anoroc.exception.GlobalObjectNotFoundException;
import com.publicissapient.anoroc.factory.ApplicationFactoryTest;
import com.publicissapient.anoroc.factory.FeatureFactory;
import com.publicissapient.anoroc.model.GlobalObject;
import com.publicissapient.anoroc.repository.GlobalObjectRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.publicissapient.anoroc.factory.GlobalObjectFactoryTest.getGlobalObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class GlobalObjectServiceTest {

    @Autowired
    private GlobalObjectService service;

    @MockBean
    private GlobalObjectRepository repository;

    @MockBean
    private ApplicationService applicationService;

    @Test
    void should_be_able_to_get_global_object_for_an_id() {
        when(repository.findById(anyLong())).thenReturn(Optional.of(getGlobalObject(1l)));
        GlobalObject globalObject = service.getById(1l);
        assertThat(globalObject).isNotNull();
    }

    @Test
    void should_be_able_to_get_global_object_list() {
        when(repository.findAll(any(Pageable.class))).thenReturn(mock(Page.class));
        List<GlobalObject> globalObjectList = service.getList(0, 5);
        assertThat(globalObjectList).isEmpty();
    }

    @Test
    void should_be_able_to_get_count() {
        when(repository.count()).thenReturn(1l);
        long count = service.getCount();
        assertThat(count).isGreaterThan(0);
    }

    @Test
    void should_be_able_to_get_all_global_object_list() {
        when(repository.findAll()).thenReturn(Arrays.asList(getGlobalObject(1l)));
        List<GlobalObject> globalObjectList = service.getAll();
        assertThat(globalObjectList).isNotEmpty();
    }

    @Test
    void should_be_able_to_save_global_object() {
        when(repository.save(any(GlobalObject.class))).thenReturn(getGlobalObject(1l));
        when(applicationService.getById(anyLong())).thenReturn(ApplicationFactoryTest.getApplication(1l));
        GlobalObject globalObject = service.save(getGlobalObject(), 1l);
        assertThat(globalObject).isNotNull();
    }

    @Test
    void should_throw_global_object_not_found_exception_for_an_invalid_id() {
        when(repository.findById(anyLong())).thenThrow(new GlobalObjectNotFoundException("not found"));
        Assertions.assertThrows(GlobalObjectNotFoundException.class, ()-> service.getById(0l));
    }

    @Test
    void should_return_empty_global_object_set_when_args_is_null() {
        Set<GlobalObject> globalObjectSet =  service.getGlobalObjectsAssociatedWithApplication(null, null);
        assertThat(globalObjectSet).isEmpty();
    }

    @Test
    void should_return_set_of_global_object_for_an_application_associated_With_feature() {
        when(repository.findByApplicationId(anyLong())).thenReturn(Arrays.asList(getGlobalObject(1l)));
        Set<GlobalObject> globalObjectSet =  service.getGlobalObjectsAssociatedWithApplication(Arrays.asList(FeatureFactory.feature(2l)), FeatureFactory.feature(1l));
        assertThat(globalObjectSet).isNotEmpty();
        assertThat(globalObjectSet).hasSize(1);
    }
}

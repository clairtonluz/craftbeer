package com.beerhouse.infra.db.beer;

import com.beerhouse.domain.beer.Beer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BeerRepositoryImplTest {

    @Mock
    private BeerJpaRepository beerJpaRepository;
    @InjectMocks
    private BeerRepositoryImpl beerRepository;

    @Test
    void findAll() {
        BeerEntity beerEntity = BeerEntity.builder()
                .id(123L)
                .name("Name")
                .alcoholContent("4.5%")
                .category("Category 1")
                .ingredients("ingredients")
                .price(5.44)
                .build();

        when(beerJpaRepository.findAll()).thenReturn(Collections.singletonList(beerEntity));

        List<Beer> beerList = beerRepository.findAll();

        assertEquals(1, beerList.size());
        assertEquals(beerEntity.getId(), beerList.get(0).getId());
        verify(beerJpaRepository).findAll();
    }

    @Test
    void findById() {
        long id = 123L;
        BeerEntity beerEntity = BeerEntity.builder()
                .id(id)
                .name("Name")
                .alcoholContent("4.5%")
                .category("Category 1")
                .ingredients("ingredients")
                .price(5.44)
                .build();

        when(beerJpaRepository.findById(id)).thenReturn(Optional.of(beerEntity));

        Optional<Beer> beerOptional = beerRepository.findById(id);

        assertTrue(beerOptional.isPresent());
        assertEquals(id, beerOptional.get().getId());
        verify(beerJpaRepository).findById(id);
    }

    @Test
    void create() {
        long id = 123L;
        Beer beerInput = BeerEntity.builder()
                .name("Name")
                .alcoholContent("4.5%")
                .category("Category 1")
                .ingredients("ingredients")
                .price(5.44)
                .build();

        BeerEntity beerEntity = BeerEntity.from(beerInput);
        beerEntity.setId(id);
        when(beerJpaRepository.save(any(BeerEntity.class))).thenReturn(beerEntity);

        Beer beerOutput = beerRepository.create(beerInput);

        assertNotNull(beerOutput);
        assertEquals(id, beerOutput.getId());
        verify(beerJpaRepository).save(any(BeerEntity.class));
    }

    @Test
    void update() {
        long id = 123L;
        Beer beerInput = BeerEntity.builder()
                .name("Name")
                .alcoholContent("4.5%")
                .category("Category 1")
                .ingredients("ingredients")
                .price(5.44)
                .build().toBeer();

        BeerEntity beerEntity = BeerEntity.from(beerInput);
        beerEntity.setId(id);
        when(beerJpaRepository.save(any(BeerEntity.class))).thenReturn(beerEntity);

        Beer beerOutput = beerRepository.update(beerInput);

        assertNotNull(beerOutput);
        assertEquals(id, beerOutput.getId());
        verify(beerJpaRepository).save(any(BeerEntity.class));
    }


    @Test
    void delete() {
        Long id = 123L;
        beerRepository.deleteById(id);
        verify(beerJpaRepository).deleteById(id);
    }

    @Test
    void shouldReturnTrueWhenBeerExists() {
        Long id = 123L;
        when(beerJpaRepository.existsById(id)).thenReturn(true);
        assertTrue(beerRepository.existsById(id));
        verify(beerJpaRepository).existsById(id);
    }

    @Test
    void shouldReturnFalseWhenBeerNotExists() {
        Long id = 123L;
        when(beerJpaRepository.existsById(id)).thenReturn(false);
        assertTrue(beerRepository.existsById(id));
        verify(beerJpaRepository).existsById(id);
    }
}
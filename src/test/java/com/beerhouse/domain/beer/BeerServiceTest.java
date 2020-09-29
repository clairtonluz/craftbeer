package com.beerhouse.domain.beer;

import com.beerhouse.domain.core.exceptions.NotFoundException;
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
class BeerServiceTest {

    @Mock
    private BeerRepository beerRepository;
    @InjectMocks
    private BeerService beerService;

    @Test
    void findAll() {
        Beer beer = new Beer();
        beer.setId(123L);
        beer.setName("Name");
        beer.setAlcoholContent("4.5%");
        beer.setCategory("Category 1");
        beer.setIngredients("ingredients");
        beer.setPrice(5.44);

        when(beerRepository.findAll()).thenReturn(Collections.singletonList(beer));

        List<Beer> beerList = beerService.findAll();

        assertEquals(1, beerList.size());
        assertEquals(beer.getId(), beerList.get(0).getId());
        verify(beerRepository).findAll();
    }

    @Test
    void findById() {
        long id = 123L;
        Beer beer = new Beer();
        beer.setId(id);
        beer.setName("Name");
        beer.setAlcoholContent("4.5%");
        beer.setCategory("Category 1");
        beer.setIngredients("ingredients");
        beer.setPrice(5.44);

        when(beerRepository.findById(id)).thenReturn(Optional.of(beer));

        Beer beerOutput = beerService.findById(id);

        assertEquals(beer.getId(), beerOutput.getId());
        verify(beerRepository).findById(id);
    }


    @Test
    void findByIdShouldThrowANotFoundExceptionWhenBeerNotExists() {
        long id = 123L;
        when(beerRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(NotFoundException.class, () -> beerService.findById(id));
        assertEquals("Beer " + id + " not found", e.getMessage());
        verify(beerRepository).findById(id);
    }

    @Test
    void create() {
        Beer beer = new Beer();
        beer.setName("Name");
        beer.setAlcoholContent("4.5%");
        beer.setCategory("Category 1");
        beer.setIngredients("ingredients");
        beer.setPrice(5.44);

        Beer beerWithId = new Beer();
        beerWithId.setId(123L);
        beerWithId.setName(beer.getName());
        beerWithId.setAlcoholContent(beer.getAlcoholContent());
        beerWithId.setCategory(beer.getCategory());
        beerWithId.setIngredients(beer.getIngredients());
        beerWithId.setPrice(beer.getPrice());


        when(beerRepository.create(any(Beer.class))).thenReturn(beerWithId);

        Beer beerOutput = beerService.create(beer);

        assertNotNull(beerOutput);
        assertEquals(beerWithId.getId(), beerOutput.getId());
        verify(beerRepository).create(any(Beer.class));
    }

    @Test
    void update() {
        long id = 123L;
        Beer beer = new Beer();
        beer.setName("Name");
        beer.setAlcoholContent("4.5%");
        beer.setCategory("Category 1");
        beer.setIngredients("ingredients");
        beer.setPrice(5.44);

        when(beerRepository.existsById(id)).thenReturn(true);
        when(beerRepository.update(any(Beer.class))).thenReturn(beer);

        Beer beerOutput = beerService.update(id, beer);

        assertNotNull(beerOutput);
        assertEquals(id, beerOutput.getId());
        verify(beerRepository).update(any(Beer.class));
    }

    @Test
    void updateShouldThrowErrorWhenBeerNotExists() {
        long id = 123L;
        Beer beer = new Beer();

        when(beerRepository.existsById(id)).thenReturn(false);

        Exception e = assertThrows(NotFoundException.class, () -> beerService.update(id, beer));
        assertEquals("Beer " + id + " not found", e.getMessage());

        verify(beerRepository).existsById(id);
    }

    @Test
    void partialUpdate() {
        long id = 123L;
        Beer beer = new Beer();
        beer.setId(id);
        beer.setName("Name");
        beer.setAlcoholContent("4.5%");
        beer.setCategory("Category 1");
        beer.setIngredients("ingredients");
        beer.setPrice(5.44);

        Beer beerInput = new Beer();
        beerInput.setName("name partial Update");
        beerInput.setAlcoholContent("AlcoholContent partial Update");
        beerInput.setCategory("category partial Update");
        beerInput.setIngredients("Ingredients partial Update");
        beerInput.setPrice(1.23);

        when(beerRepository.findById(id)).thenReturn(Optional.of(beer));
        when(beerRepository.update(any(Beer.class))).thenReturn(beer);

        Beer beerOutput = beerService.partialUpdate(id, beerInput);

        assertNotNull(beerOutput);
        assertEquals(beerInput.getName(), beerOutput.getName());
        assertEquals(beerInput.getAlcoholContent(), beerOutput.getAlcoholContent());
        assertEquals(beerInput.getCategory(), beerOutput.getCategory());
        assertEquals(beerInput.getIngredients(), beerOutput.getIngredients());
        assertEquals(beerInput.getPrice(), beerOutput.getPrice());

        verify(beerRepository).findById(id);
        verify(beerRepository).update(any(Beer.class));
    }


    @Test
    void partialUpdateShouldNotUpdateAnyThing() {
        long id = 123L;
        Beer beer = new Beer();
        beer.setId(id);
        beer.setName("Name");
        beer.setAlcoholContent("4.5%");
        beer.setCategory("Category 1");
        beer.setIngredients("ingredients");
        beer.setPrice(5.44);

        Beer beerInput = new Beer();
        when(beerRepository.findById(id)).thenReturn(Optional.of(beer));
        when(beerRepository.update(any(Beer.class))).thenReturn(beer);

        Beer beerOutput = beerService.partialUpdate(id, beerInput);

        assertNotNull(beerOutput);
        assertNotEquals(beerInput.getName(), beerOutput.getName());
        assertNotEquals(beerInput.getAlcoholContent(), beerOutput.getAlcoholContent());
        assertNotEquals(beerInput.getCategory(), beerOutput.getCategory());
        assertNotEquals(beerInput.getIngredients(), beerOutput.getIngredients());
        assertNotEquals(beerInput.getPrice(), beerOutput.getPrice());

        verify(beerRepository).findById(id);
        verify(beerRepository).update(any(Beer.class));
    }

    @Test
    void partialUpdateShouldThrowNotFoundExceptionWhenBeerNotExists() {
        long id = 123L;
        Beer beer = new Beer();

        when(beerRepository.findById(id)).thenReturn(Optional.empty());

        Exception e = assertThrows(NotFoundException.class, () -> beerService.partialUpdate(id, beer));

        assertEquals("Beer " + id + " not found", e.getMessage());
        verify(beerRepository).findById(id);
    }

    @Test
    void shouldDeleteBeerById() {
        long id = 123L;
        when(beerRepository.existsById(id)).thenReturn(true);
        beerService.deleteById(id);
        verify(beerRepository).deleteById(id);
    }


    @Test
    void deleteByIdShouldThrowNotFoundExceptionWhenBeerNotExists() {
        long id = 123L;
        when(beerRepository.existsById(id)).thenReturn(false);
        Exception e = assertThrows(NotFoundException.class, () -> beerService.deleteById(id));
        assertEquals("Beer " + id + " not found", e.getMessage());
    }
}
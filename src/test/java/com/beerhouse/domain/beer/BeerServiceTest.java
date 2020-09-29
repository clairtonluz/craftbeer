package com.beerhouse.domain.beer;

import com.beerhouse.domain.core.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    }

    @Test
    void findById() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
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
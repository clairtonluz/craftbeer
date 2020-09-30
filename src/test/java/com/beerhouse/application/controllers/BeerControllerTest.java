package com.beerhouse.application.controllers;

import com.beerhouse.application.config.handlers.error.GlobalExceptionHandler;
import com.beerhouse.domain.beer.Beer;
import com.beerhouse.domain.beer.BeerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(MockitoExtension.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BeerService beerService;

    @InjectMocks
    private BeerController beerController;
    private JacksonTester<Beer> jsonBeer;
    private JacksonTester<List<Beer>> jsonBeerList;
    private List<Beer> beerList;

    @BeforeEach
    void setUp() {
        beerList = Stream.of(1, 2, 3, 4).map(id -> {
            Beer beer = new Beer();
            beer.setId(Long.valueOf(id));
            beer.setName("Name " + id);
            beer.setIngredients("Ingredients " + id);
            beer.setCategory("Category " + id);
            beer.setAlcoholContent((4 + id) + "%");
            beer.setPrice(new Random().nextDouble() * 10 + id);
            return beer;
        }).collect(Collectors.toList());

        JacksonTester.initFields(this, new ObjectMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(beerController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void shouldListAllBeers() throws Exception {
        when(beerService.findAll()).thenReturn(beerList);
        MockHttpServletResponse response = mockMvc.perform(get("/beers")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals(jsonBeerList.write(beerList).getJson(), response.getContentAsString());
    }

    @Test
    void shouldCreateABeer() throws Exception {
        Beer beer = new Beer();
        beer.setName("Name Create");
        beer.setIngredients("Ingredients Create");
        beer.setCategory("Category Create");
        beer.setAlcoholContent("5.5%");
        beer.setPrice(new Random().nextDouble() * 10);

        Beer beerWithId = new Beer();
        beerWithId.setId(new Random().nextLong());
        beerWithId.setName(beer.getName());
        beerWithId.setIngredients(beer.getIngredients());
        beerWithId.setCategory(beer.getCategory());
        beerWithId.setAlcoholContent(beer.getAlcoholContent());
        beerWithId.setPrice(beer.getPrice());

        when(beerService.create(beer)).thenReturn(beerWithId);
        MockHttpServletResponse response = mockMvc.perform(
                post("/beers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBeer.write(beer).getJson())
        ).andReturn().getResponse();

        assertEquals(HttpStatus.CREATED.value(), response.getStatus());
        assertEquals("http://localhost/beers/" + beerWithId.getId(), response.getHeader("Location"));
    }

    @Test
    void getById() throws Exception {
        long id = new Random().nextLong();
        Beer beer = new Beer();
        beer.setId(id);
        beer.setName("Name");
        beer.setIngredients("Ingredients");
        beer.setCategory("Category");
        beer.setAlcoholContent("5.5%");
        beer.setPrice(new Random().nextDouble() * 10);

        when(beerService.findById(id)).thenReturn(beer);
        MockHttpServletResponse response = mockMvc.perform(
                get("/beers/" + id)
                        .accept(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals(jsonBeer.write(beer).getJson(), response.getContentAsString());
    }

    @Test
    void update() throws Exception {

        Beer beer = new Beer();
        beer.setId(new Random().nextLong());
        beer.setName("Name Update");
        beer.setIngredients("Ingredients Update");
        beer.setCategory("Category Update");
        beer.setAlcoholContent("5.5%");
        beer.setPrice(new Random().nextDouble() * 10);

        when(beerService.update(beer.getId(), beer)).thenReturn(beer);
        MockHttpServletResponse response = mockMvc.perform(
                put("/beers/" + beer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBeer.write(beer).getJson())
        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void partialUpdate() throws Exception {
        Long id = new Random().nextLong();

        Beer beer = new Beer();
        beer.setName("Name Partial Update");
        beer.setIngredients("Ingredients Partial Update");
        beer.setCategory("Category Partial Update");

        Beer beerUpdated = new Beer();
        beerUpdated.setId(id);
        beerUpdated.setName(beer.getName());
        beerUpdated.setIngredients(beer.getIngredients());
        beerUpdated.setCategory(beer.getCategory());
        beerUpdated.setAlcoholContent("5.5%");
        beerUpdated.setPrice(new Random().nextDouble() * 10);

        when(beerService.partialUpdate(id, beer)).thenReturn(beerUpdated);
        MockHttpServletResponse response = mockMvc.perform(
                patch("/beers/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBeer.write(beer).getJson())
        ).andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    void shouldDeleteBeer() throws Exception {
        long id = new Random().nextLong();

        MockHttpServletResponse response = mockMvc.perform(
                delete("/beers/{id}", id)
        ).andReturn().getResponse();

        assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
    }
}
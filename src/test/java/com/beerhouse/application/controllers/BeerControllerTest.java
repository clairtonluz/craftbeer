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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
    void list() throws Exception {
        when(beerService.findAll()).thenReturn(beerList);
        MockHttpServletResponse response = mockMvc.perform(get("/beers")
                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals(MediaType.APPLICATION_JSON_VALUE, response.getContentType());
        assertEquals(jsonBeerList.write(beerList).getJson(), response.getContentAsString());
    }

    @Test
    void create() {
    }

    @Test
    void getById() {
    }

    @Test
    void update() {
    }

    @Test
    void partialUpdate() {
    }

    @Test
    void delete() {
    }

    @Test
    void getLocation() {
    }
}
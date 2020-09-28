package com.beerhouse.domain.beer;

import lombok.Data;

@Data
public class Beer {
    private Long id;
    private String name;
    private String ingredients;
    private String alcoholContent;
    private Double price;
    private String category;
}

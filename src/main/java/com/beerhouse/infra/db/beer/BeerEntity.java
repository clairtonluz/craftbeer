package com.beerhouse.infra.db.beer;

import com.beerhouse.domain.beer.Beer;
import lombok.*;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name="beers")
@Builder
@AllArgsConstructor
@NoArgsConstructor
class BeerEntity extends Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String ingredients;
    @Column(name="alcohol_content")
    private String alcoholContent;
    private Double price;
    private String category;

    public static BeerEntity from(Beer beer) {
        BeerEntity entity = new BeerEntity();
        entity.setId(beer.getId());
        entity.setName(beer.getName());
        entity.setIngredients(beer.getIngredients());
        entity.setAlcoholContent(beer.getAlcoholContent());
        entity.setPrice(beer.getPrice());
        entity.setCategory(beer.getCategory());
        return entity;
    }

    public Beer toBeer() {
        return this;
    }
}

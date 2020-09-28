package com.beerhouse.domain.beer;

import java.util.List;
import java.util.Optional;

public interface BeerRepositoy {
    List<Beer> findAll();

    Optional<Beer> findById(Long id);

    Beer create(Beer beer);

    Beer update(Beer beer);

    Beer partialUpdate(Long id, Beer beer);

    void delete(Long id);
}

package com.beerhouse.domain.beer;

import java.util.List;
import java.util.Optional;

public interface BeerRepository {
    List<Beer> findAll();

    Optional<Beer> findById(Long id);

    boolean existsById(Long id);

    Beer create(Beer beer);

    Beer update(Beer beer);

    void deleteById(Long id);
}

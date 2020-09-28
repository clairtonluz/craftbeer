package com.beerhouse.infra.db.beer;

import com.beerhouse.domain.beer.Beer;
import com.beerhouse.domain.beer.BeerRepositoy;
import com.beerhouse.domain.core.exceptions.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BeerRepositoryImpl implements BeerRepositoy {

    private final BeerJpaRepository beerJpaRepository;

    public BeerRepositoryImpl(BeerJpaRepository beerJpaRepository) {
        this.beerJpaRepository = beerJpaRepository;
    }

    @Override
    public List<Beer> findAll() {
        return beerJpaRepository.findAll().stream()
                .map(BeerEntity::toBeer)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Beer> findById(Long id) {
        return beerJpaRepository.findById(id)
                .flatMap(entity -> Optional.of(entity.toBeer()));
    }

    @Override
    public Beer create(Beer beer) {
        BeerEntity entity = BeerEntity.from(beer);
        return beerJpaRepository.save(entity);
    }

    @Override
    public Beer update(Beer beer) {
        return beerJpaRepository.save(BeerEntity.from(beer));
    }

    @Override
    public Beer partialUpdate(Long id, Beer beer) {
        BeerEntity entity = beerJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(Locale.getDefault(), "Beer %d not found", id)
                ));

        if (beer.getName() != null) entity.setName(beer.getName());
        if (beer.getIngredients() != null) entity.setIngredients(beer.getIngredients());
        if (beer.getAlcoholContent() != null) entity.setAlcoholContent(beer.getAlcoholContent());
        if (beer.getPrice() != null) entity.setPrice(beer.getPrice());
        if (beer.getCategory() != null) entity.setCategory(beer.getCategory());

        return beerJpaRepository.save(entity);
    }

    @Override
    public void delete(Long id) {
        beerJpaRepository.deleteById(id);
    }
}

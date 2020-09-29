package com.beerhouse.infra.db.beer;

import com.beerhouse.domain.beer.Beer;
import com.beerhouse.domain.beer.BeerRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class BeerRepositoryImpl implements BeerRepository {

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
    public boolean existsById(Long id) {
        return beerJpaRepository.existsById(id);
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
    public void deleteById(Long id) {
        beerJpaRepository.deleteById(id);
    }
}

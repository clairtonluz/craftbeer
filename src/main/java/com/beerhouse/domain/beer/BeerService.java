package com.beerhouse.domain.beer;

import com.beerhouse.domain.core.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;

@Service
public class BeerService {
    private static final String BEER_NOT_FOUND = "Beer %d not found";
    private final BeerRepository beerRepository;

    public BeerService(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    public List<Beer> findAll() {
        return beerRepository.findAll();
    }

    public Beer findById(Long id) {
        return beerRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format(Locale.getDefault(), BEER_NOT_FOUND, id)
        ));
    }

    public Beer create(Beer beer) {
        return beerRepository.create(beer);
    }

    public Beer update(Long id, Beer beer) {
        if (!beerRepository.existsById(id)) {
            throw new NotFoundException(
                    String.format(Locale.getDefault(), BEER_NOT_FOUND, id)
            );
        }

        beer.setId(id);
        return beerRepository.update(beer);
    }

    public Beer partialUpdate(Long id, Beer beer) {
        beer.setId(id);

        Beer actual = beerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(
                        String.format(Locale.getDefault(), BEER_NOT_FOUND, id)
                ));

        if (beer.getName() != null) actual.setName(beer.getName());
        if (beer.getIngredients() != null) actual.setIngredients(beer.getIngredients());
        if (beer.getAlcoholContent() != null) actual.setAlcoholContent(beer.getAlcoholContent());
        if (beer.getPrice() != null) actual.setPrice(beer.getPrice());
        if (beer.getCategory() != null) actual.setCategory(beer.getCategory());

        return beerRepository.update(actual);
    }

    public void deleteById(Long id) {
        if (!beerRepository.existsById(id)) {
            throw new NotFoundException(
                    String.format(Locale.getDefault(), BEER_NOT_FOUND, id)
            );
        }
        beerRepository.deleteById(id);
    }

}

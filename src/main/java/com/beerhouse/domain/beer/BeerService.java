package com.beerhouse.domain.beer;

import com.beerhouse.domain.core.exceptions.BadRequestException;
import com.beerhouse.domain.core.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Locale;

@Service
public class BeerService {
    private final BeerRepositoy beerRepositoy;

    public BeerService(BeerRepositoy beerRepositoy) {
        this.beerRepositoy = beerRepositoy;
    }

    public List<Beer> findAll() {
        return beerRepositoy.findAll();
    }

    public Beer findById(Long id) {
        return beerRepositoy.findById(id).orElseThrow(() -> new NotFoundException(
                String.format(Locale.getDefault(), "Beer %d not found", id)
        ));
    }

    public Beer create(Beer beer) {
        return beerRepositoy.create(beer);
    }

    public Beer update(Long id, Beer beer) {
        beer.setId(id);
        return beerRepositoy.update(beer);
    }

    public Beer partialUpdate(Long id, Beer beer) {
        if (beer.getId() != null && !beer.getId().equals(id)) {
            throw new BadRequestException("id change is not allowed");
        }
        return beerRepositoy.partialUpdate(id, beer);
    }

    public void delete(Long id) {
        beerRepositoy.delete(id);
    }

}

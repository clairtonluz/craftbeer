package com.beerhouse.application.controllers;

import com.beerhouse.domain.beer.Beer;
import com.beerhouse.domain.beer.BeerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("beers")
public class BeerController {
    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping
    public List<Beer> list() {
        return beerService.findAll();
    }

    @PostMapping
    public ResponseEntity<Void> create(HttpServletRequest req, @RequestBody Beer beer) {
        beer = beerService.create(beer);
        URI location = getLocation(req, beer);
        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public Beer getById(@PathVariable Long id) {
        return beerService.findById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Beer beer) {
        beerService.update(id, beer);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Void> partialUpdate(@PathVariable Long id, @RequestBody Beer beer) {
        beerService.partialUpdate(id, beer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        beerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    public URI getLocation(HttpServletRequest req, Beer beer) {
        return URI.create(
                String.format(
                        Locale.getDefault(),
                        "%s/%d",
                        req.getRequestURL().toString(),
                        beer.getId()
                )
        );
    }
}

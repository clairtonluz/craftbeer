package com.beerhouse.infra.db.beer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface BeerJpaRepository extends JpaRepository<BeerEntity, Long> {

}

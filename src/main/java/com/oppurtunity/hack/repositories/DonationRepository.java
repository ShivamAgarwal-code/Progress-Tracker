package com.oppurtunity.hack.repositories;

import com.oppurtunity.hack.entities.Donation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DonationRepository extends MongoRepository<Donation, Integer> {

}

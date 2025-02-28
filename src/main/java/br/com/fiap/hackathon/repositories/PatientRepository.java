package br.com.fiap.hackathon.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fiap.hackathon.models.Patient;

public interface PatientRepository extends MongoRepository <Patient, String> {

    Optional<Patient> findByUserIdentifier(String userIdentifier);

}

package br.com.fiap.hackathon.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import br.com.fiap.hackathon.models.Patient;

public interface PatientRepository extends MongoRepository <Patient, String> {

}

package br.com.fiap.hackathon.services;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import br.com.fiap.hackathon.models.Patient;
import br.com.fiap.hackathon.repositories.PatientRepository;

@Service
public class DumpService {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private PatientRepository patientRepository;

    public Set<String> getAllCollections() {
        return mongoTemplate.getCollectionNames();
    }
    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }
}

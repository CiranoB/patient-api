package br.com.fiap.hackathon.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fiap.hackathon.models.Patient;
import br.com.fiap.hackathon.models.PatientRecord;
import br.com.fiap.hackathon.repositories.PatientRepository;

@Service
public class PatientService {
    @Autowired
    public PatientRepository patientRepository;

    public Patient addRecortToPatient(String userName, PatientRecord newRecord){
        Optional<Patient> existingPatient = patientRepository.findAll()
                .stream()
                .filter(p -> p.getUserName().equals(userName))
                .findFirst();

        Patient patient;
        if (existingPatient.isPresent()) {
            patient = existingPatient.get();
        } else {
            patient = new Patient();
            patient.setUserName(userName);
            patient.setPatientRecords(new ArrayList<>());
        }

        patient.getPatientRecords().add(newRecord);
        return patientRepository.save(patient);
    }

    public Optional<Patient> getPatientByUserName(String userName) {
        return patientRepository.findAll()
                .stream()
                .filter(p -> p.getUserName().equals(userName))
                .findFirst();
    }

}

package br.com.fiap.hackathon.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.fiap.hackathon.configuration.security.LoggedUser;
import br.com.fiap.hackathon.models.Patient;
import br.com.fiap.hackathon.models.PatientRecord;
import br.com.fiap.hackathon.repositories.PatientRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public Patient addRecordToPatient(final PatientRecord newRecord) {
        final var identifier = LoggedUser.get().getUserIdentifier();

        final var patient = this.patientRepository
                .findByUserIdentifier(identifier)
                .orElseGet(() -> {
                    final var newPatient = new Patient();
                    newPatient.setPatientRecords(new ArrayList<>());
                    return newPatient;
                });

        patient.setUserIdentifier(identifier);
        patient.setUserName(LoggedUser.get().getName());
        patient.setPatientRecords(updatePatientRecords(patient.getPatientRecords()));

        return patientRepository.save(patient);
    }

    public Optional<Patient> getPatientByUserName(String userName) {
        return patientRepository.findAll()
                .stream()
                .filter(p -> p.getUserName().equals(userName))
                .findFirst();
    }

    private List<PatientRecord> updatePatientRecords(List<PatientRecord> patientRecords) {
        List<PatientRecord> newPatientRecords = new ArrayList<>();
        for (PatientRecord patientRecord : patientRecords) {
            newPatientRecords.add(patientRecord);
        }
        return patientRecords;
    }

}

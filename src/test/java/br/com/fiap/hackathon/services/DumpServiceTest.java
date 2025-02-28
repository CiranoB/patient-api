package br.com.fiap.hackathon.services;

import br.com.fiap.hackathon.models.Patient;
import br.com.fiap.hackathon.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DumpServiceTest {

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private DumpService dumpService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCollections() {
        Set<String> mockedCollections = new HashSet<>();
        mockedCollections.add("patients");
        mockedCollections.add("appointments");
        when(mongoTemplate.getCollectionNames()).thenReturn(mockedCollections);

        Set<String> result = dumpService.getAllCollections();

        assertEquals(mockedCollections, result);

        verify(mongoTemplate, times(1)).getCollectionNames();
    }

    @Test
    void testGetAllPatients() {
        Patient patient = new Patient();
        patient.setId("123");
        patient.setUserName("John Doe");
        List<Patient> mockedPatients = List.of(patient);
        when(patientRepository.findAll()).thenReturn(mockedPatients);

        List<Patient> result = dumpService.getAllPatients();

        assertEquals(mockedPatients, result);

        verify(patientRepository, times(1)).findAll();
    }
}
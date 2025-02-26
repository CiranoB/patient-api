package br.com.fiap.hackathon.services;

import br.com.fiap.hackathon.models.Patient;
import br.com.fiap.hackathon.models.PatientRecord;
import br.com.fiap.hackathon.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddRecortToPatient_existingPatient() {
        Patient existingPatient = new Patient();
        existingPatient.setUserName("john_doe");
        existingPatient.setPatientRecords(new ArrayList<>());
        when(patientRepository.findAll()).thenReturn(List.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(existingPatient);

        PatientRecord newRecord = new PatientRecord();
        Patient updatedPatient = patientService.addRecortToPatient("john_doe", newRecord);

        assertNotNull(updatedPatient);
        assertEquals(1, updatedPatient.getPatientRecords().size());
        verify(patientRepository, times(1)).save(updatedPatient);
    }

    @Test
    public void testAddRecortToPatient_newPatient() {
        when(patientRepository.findAll()).thenReturn(new ArrayList<>());
        when(patientRepository.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        PatientRecord newRecord = new PatientRecord();

        Patient newPatient = patientService.addRecortToPatient("john_doe", newRecord);

        assertNotNull(newPatient);
        assertEquals("john_doe", newPatient.getUserName());
        assertEquals(1, newPatient.getPatientRecords().size());
        verify(patientRepository, times(1)).save(newPatient);
    }

    @Test
    public void testGetPatientByUserName_found() {
        Patient existingPatient = new Patient();
        existingPatient.setUserName("john_doe");
        when(patientRepository.findAll()).thenReturn(List.of(existingPatient));

        Optional<Patient> patient = patientService.getPatientByUserName("john_doe");

        assertTrue(patient.isPresent());
        assertEquals("john_doe", patient.get().getUserName());
    }

    @Test
    public void testGetPatientByUserName_notFound() {
        when(patientRepository.findAll()).thenReturn(new ArrayList<>());

        Optional<Patient> patient = patientService.getPatientByUserName("john_doe");

        assertFalse(patient.isPresent());
    }
}
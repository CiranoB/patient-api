package br.com.fiap.hackathon.services;

import br.com.fiap.hackathon.configuration.security.JwtAuthenticationToken;
import br.com.fiap.hackathon.models.Patient;
import br.com.fiap.hackathon.models.PatientRecord;
import br.com.fiap.hackathon.repositories.PatientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PatientServiceTest {
    @Mock
    private PatientRepository patientRepository;

    @InjectMocks
    private PatientService patientService;

    @BeforeEach
    void setUp() {
        final var userdetails = new UserDetails() {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "password";
            }

            @Override
            public String getUsername() {
                return "username";
            }

        };
        MockitoAnnotations.openMocks(this);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(
                new JwtAuthenticationToken(userdetails, "token", userdetails.getAuthorities(), "userIdentifier"));
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void testAddRecortToPatient_existingPatient() {
        Patient existingPatient = new Patient();
        existingPatient.setUserName("username");
        existingPatient.setPatientRecords(List.of(new PatientRecord()));
        when(patientRepository.findByUserIdentifier(anyString())).thenReturn(Optional.of(existingPatient));
        when(patientRepository.save(any(Patient.class))).thenReturn(existingPatient);

        Patient updatedPatient = patientService.addRecordToPatient(new PatientRecord());

        assertNotNull(updatedPatient);
        assertEquals(1, updatedPatient.getPatientRecords().size());
        verify(patientRepository, times(1)).save(any());
    }

    @Test
    void testAddRecortToPatient_newPatient() {
        Patient existingPatient = new Patient();
        existingPatient.setUserName("username");
        existingPatient.setUserIdentifier("userIdentifier");
        existingPatient.setPatientRecords(List.of(new PatientRecord()));
        when(patientRepository.findByUserIdentifier(anyString())).thenReturn(Optional.empty());
        when(patientRepository.save(any(Patient.class))).thenReturn(existingPatient);

        PatientRecord newRecord = new PatientRecord();

        Patient newPatient = patientService.addRecordToPatient(newRecord);

        assertNotNull(newPatient);
        assertEquals("username", newPatient.getUserName());
        assertEquals(1, newPatient.getPatientRecords().size());
        verify(patientRepository, times(1)).save(any());
    }

    @Test
    void testGetPatientByUserName_found() {
        Patient existingPatient = new Patient();
        existingPatient.setUserName("username");
        when(patientRepository.findAll()).thenReturn(List.of(existingPatient));

        Optional<Patient> patient = patientService.getPatientByUserName("username");

        assertTrue(patient.isPresent());
        assertEquals("username", patient.get().getUserName());
    }

    @Test
    void testGetPatientByUserName_notFound() {
        when(patientRepository.findAll()).thenReturn(new ArrayList<>());

        Optional<Patient> patient = patientService.getPatientByUserName("username");

        assertFalse(patient.isPresent());
    }
}
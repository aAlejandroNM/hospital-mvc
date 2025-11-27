package com.solvd.hospital.service;

import com.solvd.hospital.model.Patient;
import com.solvd.hospital.service.impl.PatientServiceImpl;
import com.solvd.hospital.test.dao.InMemoryPatientDAO;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

public class PatientServiceImplTest {

    private PatientServiceImpl service;
    private InMemoryPatientDAO patientDAO;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        patientDAO = new InMemoryPatientDAO();
        service = new PatientServiceImpl(patientDAO);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (patientDAO != null) {
            patientDAO.clear();
        }
    }

    @Test(groups = {"service"})
    public void registerNewPatientShouldDelegateToDao() {
        Patient patient = new Patient(null, "Test Patient", 25, null, "INS-TEST");

        service.registerNewPatient(patient);

        assertEquals(patientDAO.getCreatedPatients().size(), 1, "Exactly one patient must have been created.");
        assertSame(patient, patientDAO.getCreatedPatients().get(0), "The patient transferred to the DAO must be the same object.");
    }

    @DataProvider(name = "patientIds")
    public Object[][] patientIdsProvider() {
        return new Object[][]{
                {1L, "Alice"},
                {2L, "Bob"},
                {3L, "Charlie"}
        };
    }

    @Test(dataProvider = "patientIds", groups = {"service", "parameterized"})
    public void findPatientByIdShouldReturnPatientFromDao(Long id, String name) {
        Patient stored = new Patient(id, name, 30, null, "INS-" + id);
        patientDAO.create(stored);

        Patient result = service.findPatientById(id);

        assertNotNull(result, "The patient must not be null.");
        assertEquals(result.getId(), id);
        assertEquals(result.getName(), name);
    }
}



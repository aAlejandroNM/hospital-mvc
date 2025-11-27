package com.solvd.hospital.integration;

import com.solvd.hospital.model.Disease;
import com.solvd.hospital.model.MedicalDocument;
import com.solvd.hospital.model.MedicalRecord;
import com.solvd.hospital.model.Symptom;
import com.solvd.hospital.service.impl.JsonServiceImpl;
import com.solvd.hospital.service.impl.SAXParserServiceImpl;
import com.solvd.hospital.service.impl.XMLServiceImpl;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

public class ParsingIntegrationTest {

    private JsonServiceImpl jsonService;
    private XMLServiceImpl xmlService;
    private SAXParserServiceImpl saxParserService;

    @BeforeClass(alwaysRun = true)
    public void init() {
        jsonService = new JsonServiceImpl();
        xmlService = new XMLServiceImpl();
        saxParserService = new SAXParserServiceImpl();
    }

    @Test(groups = {"integration", "json"})
    @Parameters({"jsonFilePath"})
    public void readMedicalDocumentsShouldLoadDocumentsFromJson(String jsonFilePath) {
        List<MedicalDocument> documents = jsonService.readMedicalDocuments(jsonFilePath);

        assertNotNull(documents, "The list of documents must not be empty.");
        assertFalse(documents.isEmpty(), "There must be at least one medical document in the initial JSON.");
    }

    @Test(groups = {"integration", "json"})
    public void readMedicalDocumentsWithInvalidPathShouldReturnEmptyList() {
        List<MedicalDocument> documents = jsonService.readMedicalDocuments("non-existing-file.json");

        assertNotNull(documents);
        assertTrue(documents.isEmpty(), "An empty list is expected for an invalid path.");
    }

    @Test(groups = {"integration", "xml"})
    public void parseMedicalRecordShouldReturnRecordWithDiseases() {
        String xmlPath = "src/main/resources/xml/medical-record.xml";
        String xsdPath = "src/main/resources/xml/medical-record.xsd";

        MedicalRecord record = xmlService.parseMedicalRecord(xmlPath, xsdPath);

        assertNotNull(record, "The medical record must not be invalid.");
        List<Disease> diseases = record.getDiseases();
        assertNotNull(diseases, "The list of diseases should not be empty.");
        assertFalse(diseases.isEmpty(), "There must be at least one disease in the test XML.");
    }

    @Test(groups = {"integration", "xml"})
    public void parseSymptomsShouldReturnNonEmptyList() {
        String symptomsPath = "src/main/resources/xml/symptoms.xml";

        List<Symptom> symptoms = saxParserService.parseSymptoms(symptomsPath);

        assertNotNull(symptoms);
        assertFalse(symptoms.isEmpty(), "Symptoms must be loaded from the catalog XML.");
    }
}



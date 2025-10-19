package com.solvd.hospital.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.solvd.hospital.model.MedicalDocument;
import com.solvd.hospital.service.interfaces.IJsonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JsonServiceImpl implements IJsonService {

    private static final Logger LOGGER = LogManager.getLogger(JsonServiceImpl.class);
    private final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

    @Override
    public List<MedicalDocument> readMedicalDocuments(String filePath) {
        try {
            return objectMapper.readValue(new File(filePath),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, MedicalDocument.class));
        } catch (IOException e) {
            LOGGER.error("Error reading medical documents from JSON file: {}", filePath, e);
            return Collections.emptyList();
        }
    }

    @Override
    public void writeMedicalDocuments(String filePath, List<MedicalDocument> documents) {
        try {
            objectMapper.writeValue(new File(filePath), documents);
        } catch (IOException e) {
            LOGGER.error("Error writing medical documents to JSON file: {}", filePath, e);
        }
    }
}

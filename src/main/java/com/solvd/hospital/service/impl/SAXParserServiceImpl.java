package com.solvd.hospital.service.impl;

import com.solvd.hospital.model.Disease;
import com.solvd.hospital.model.Symptom;
import com.solvd.hospital.service.interfaces.IParserService;
import com.solvd.hospital.util.SAXParser;
import com.solvd.hospital.util.DiseaseHandler;
import com.solvd.hospital.util.SymptomHandler;

import java.util.List;

public class SAXParserServiceImpl implements IParserService {

    @Override
    public List<Disease> parseDiseases(String filePath) {
        SAXParser parser = new SAXParser();
        DiseaseHandler handler = new DiseaseHandler();
        parser.parse(filePath, handler);
        return handler.getDiseases();
    }

    @Override
    public List<Symptom> parseSymptoms(String filePath) {
        SAXParser parser = new SAXParser();
        SymptomHandler handler = new SymptomHandler();
        parser.parse(filePath, handler);
        return handler.getSymptoms();
    }
}

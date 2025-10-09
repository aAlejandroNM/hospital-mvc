package com.solvd.hospital.util;

import com.solvd.hospital.model.Symptom;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class SymptomHandler extends DefaultHandler {
    private List<Symptom> symptoms;
    private Symptom currentSymptom;
    private StringBuilder elementValue;

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    @Override
    public void startDocument() {
        symptoms = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        elementValue = new StringBuilder();
        if (qName.equalsIgnoreCase("symptom")) {
            currentSymptom = new Symptom();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        elementValue.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (currentSymptom != null) {
            switch (qName.toLowerCase()) {
                case "id":
                    currentSymptom.setId(Long.parseLong(elementValue.toString()));
                    break;
                case "name":
                    currentSymptom.setName(elementValue.toString());
                    break;
                case "description":
                    currentSymptom.setDescription(elementValue.toString());
                    break;
                case "severity":
                    currentSymptom.setSeverity(elementValue.toString());
                    break;
                case "symptom":
                    symptoms.add(currentSymptom);
                    break;
            }
        }
    }
}

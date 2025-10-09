package com.solvd.hospital.util;

import com.solvd.hospital.model.Disease;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.ArrayList;
import java.util.List;

public class DiseaseHandler extends DefaultHandler {
    private List<Disease> diseases;
    private Disease currentDisease;
    private StringBuilder elementValue;

    public List<Disease> getDiseases() {
        return diseases;
    }

    @Override
    public void startDocument() {
        diseases = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        elementValue = new StringBuilder();
        if (qName.equalsIgnoreCase("disease")) {
            currentDisease = new Disease();
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        elementValue.append(ch, start, length);
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        if (currentDisease != null) {
            switch (qName.toLowerCase()) {
                case "id":
                    currentDisease.setId(Long.parseLong(elementValue.toString()));
                    break;
                case "name":
                    currentDisease.setName(elementValue.toString());
                    break;
                case "description":
                    currentDisease.setDescription(elementValue.toString());
                    break;
                case "disease":
                    diseases.add(currentDisease);
                    break;
            }
        }
    }
}


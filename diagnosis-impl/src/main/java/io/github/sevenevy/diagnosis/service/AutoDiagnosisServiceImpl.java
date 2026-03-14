package io.github.sevenevy.diagnosis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.github.sevenevy.diagnosis.infrastructure.ScreenAdapter;
import io.github.sevenevy.diagnosis.config.EnvironmentProperties;

public class AutoDiagnosisServiceImpl implements AutoDiagnosisService {

    static final String NO_PATHOLOGY_DETECTED = "No pathology detected";

    private final ScreenAdapter screen;
    private final EnvironmentProperties props;

    public AutoDiagnosisServiceImpl(ScreenAdapter screen, EnvironmentProperties props) {
        this.screen = screen;
        this.props = props;
    }

    public String diagnoseFromHealthIndex(final int healthIndex) {
        List<String> requiredMedicalUnits = new ArrayList<>();
        Map<Integer, String> healthIndexMapping = props.getMapping();
        for (Integer trigger : healthIndexMapping.keySet()) {
            if (healthIndex % trigger == 0) { // index divisible by one of our indicators
                requiredMedicalUnits.add(healthIndexMapping.get(trigger));
            }
        }
        String screenDisplay = requiredMedicalUnits.isEmpty()
            ? NO_PATHOLOGY_DETECTED
            : String.join(", ", requiredMedicalUnits);
        screen.display(screenDisplay);
        return screenDisplay;
    }
}

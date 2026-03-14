package io.github.sevenevy.diagnosis.service;

import io.github.sevenevy.diagnosis.infrastructure.ScreenAdapter;
import io.github.sevenevy.diagnosis.config.EnvironmentProperties;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AutoDiagnosisServiceImplTest {

    ScreenAdapter screen = mock();
    EnvironmentProperties props = mock();
    AutoDiagnosisServiceImpl service = new AutoDiagnosisServiceImpl(screen, props);

    @BeforeEach
    void setup() {
        when(props.getMapping()).thenReturn(Map.of(
            3, "Cardiologie",
            5, "Traumatologie"
        ));
    }
    
    @Test
    void diagnoseFromHealthIndex_withoutPathology_shouldDisplayDefaultMessage() {
        String result = service.diagnoseFromHealthIndex(1);

        verify(screen).display(result);
        assertThat(result).isEqualTo(AutoDiagnosisServiceImpl.NO_PATHOLOGY_DETECTED);
    }

    @Test
    void diagnoseFromHealthIndex_withOnePathology_shouldDisplayCorrespondingMedicalUnit() {
        String result = service.diagnoseFromHealthIndex(3);

        verify(screen).display(result);
        assertThat(result).isEqualTo("Cardiologie");
    }

    @Test
    void diagnoseFromHealthIndex_withSeveralPathologies_shouldDisplayAllCorrespondingMedicalUnits() {
        String result = service.diagnoseFromHealthIndex(15);

        verify(screen).display(result);
        assertThat(result).satisfiesAnyOf(
            r -> r.equals("Cardiologie, Traumatologie"),
            r -> r.equals("Traumatologie, Cardiologie")
        );
    }

    @Test
    void diagnoseFromHealthIndex_divisibleSeveralTimesBySameIndicator_shouldNotRepeatMedicalUnit() {
        String result = service.diagnoseFromHealthIndex(125);

        verify(screen).display(result);
        assertThat(result).isEqualTo("Traumatologie");
    }
}

package app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import app.entity.Package;
import app.repository.PackageRepository;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

public class PackageServiceTest {
    @Mock
    private PackageRepository packageRepository;
    private PackageService packageService;
    private Validator validator;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();

        packageService = new PackageService(packageRepository, validator);
    }

    @Test
    @DisplayName("Cena 01 - Validando campos obrigatorio")
    void cenario01() {
        Package pacote = new Package("", "");
        when(packageRepository.save(any(Package.class))).thenReturn(pacote);
        assertThrows(ConstraintViolationException.class, () -> packageService.postMapping(pacote));
    }

    @Test
    @DisplayName("Cena 02 - Cadastro de novo pacote")
    void cenario02() {
        Package pacote = new Package("adventure", "mto bom");
        when(packageRepository.save(any(Package.class))).thenReturn(pacote);

        Package saved = packageService.postMapping(pacote);
        assertEquals("adventure", saved.getTitle());
    }

    @Test
    @DisplayName("Cena 03 - Lista")
    void cenario03() {
        List<Package> packages = List.of(
                new Package("cataratas", "aventura uiui"));

        when(packageRepository.findAll()).thenReturn(packages);

        List<Package> result = packageService.findAll();
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("Cena 04 - Buscar por Id")
    void cenario04() {
        Package pacote = new Package("cataratas", "bastante agua");
        when(packageRepository.findById(1L)).thenReturn(Optional.of(pacote));

        Package result = packageService.findById(1L);
        assertEquals("cataratas", result.getTitle());
    }

    @Test
    @DisplayName("Cena 05 - Buscar por Id inexistente")
    void cenario05() {
        when(packageRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> packageService.findById(999L));
    }

    @Test
    @DisplayName("Cena 06 - Delete pacote")
    void cenario06() {
        when(packageRepository.existsById(1L)).thenReturn(true);

        ResponseEntity<Void> response = packageService.deleteMapping(1L);
        assertEquals(204, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Cena 07 - Delete pacote inexistente")
    void cenario07() {
        when(packageRepository.existsById(999L)).thenReturn(false);
        ResponseEntity<Void> response = packageService.deleteMapping(999L);
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("Cena 08 - Atualizar pacote")
    void cenario08(){
        Package pacote = new Package("cataratas", "bastante agua");
        when(packageRepository.save(any(Package.class))).thenReturn(pacote);

        Package result = packageService.putMapping(pacote);
        assertEquals("cataratas", result.getTitle());
    }
}

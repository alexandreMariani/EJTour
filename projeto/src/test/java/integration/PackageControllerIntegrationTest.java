package integration;

import app.controller.PackageController;
import app.entity.Package;
import app.service.PackageService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Integração – PackageController")
class PackageControllerIntegrationTest {

    private MockMvc mockMvc;

    @Mock
    private PackageService packageService;

    @InjectMocks
    private PackageController packageController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(packageController).build();
    }

    @Test
    @DisplayName("Integração – findById deve retornar Package existente")
    void testFindById() throws Exception {
        Package pack = new Package(1L, "Package 1", "Description 1", List.of("Activity 1"));
        when(packageService.findById(1L)).thenReturn(pack);

        mockMvc.perform(get("/package/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Package 1"))
                .andExpect(jsonPath("$.description").value("Description 1"));
    }

    @Test
    @DisplayName("Integração – save deve criar novo Package")
    void testSave() throws Exception {
        Package saved = new Package(2L, "New Package", "New Description", List.of("New Activity"));
        when(packageService.postMapping(any(Package.class))).thenReturn(saved);

        mockMvc.perform(post("/package")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "title":"New Package",
                      "description":"New Description",
                      "activides":["New Activity"]
                    }
                    """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.title").value("New Package"));
    }

    @Test
    @DisplayName("Integração – deleteMapping deve remover Package")
    void testDelete() throws Exception {
        when(packageService.deleteMapping(1L)).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/package/{id}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Package deleted successfully"));
    }

    @Test
    @DisplayName("Integração – putMapping deve atualizar Package")
    void testEdit() throws Exception {
        Package updated = new Package(1L, "Updated Package", "Updated Description", List.of("Updated Activity"));
        when(packageService.putMapping(any(Package.class))).thenReturn(updated);

        mockMvc.perform(put("/package")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    {
                      "id":1,
                      "title":"Updated Package",
                      "description":"Updated Description",
                      "activides":["Updated Activity"]
                    }
                    """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Package"));
    }
}

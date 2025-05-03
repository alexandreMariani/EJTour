package testController;

import app.controller.PackageController;
import app.entity.Package;
import app.service.PackageService;

import org.junit.jupiter.api.BeforeEach;
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
public class PackageControllerTest {

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
    void testFindById() throws Exception {
        Package pack = new Package();
        pack.setId(1L);
        pack.setTitle("Package 1");
        pack.setDescription("Description 1");

        when(packageService.findById(1L)).thenReturn(pack);

        mockMvc.perform(get("/package/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Package 1"))  
                .andExpect(jsonPath("$.description").value("Description 1"));
    }

    @Test
    void testFindByIdNotFound() throws Exception {
        when(packageService.findById(1L)).thenReturn(null);

        mockMvc.perform(get("/package/{id}", 1L))
                .andExpect(status().isNotFound())
                .andExpect(content().string("package not found"));
    }

    @Test
    void testFindAll() throws Exception {
        Package pack1 = new Package();
        pack1.setId(1L);
        pack1.setTitle("Package 1");

        Package pack2 = new Package();
        pack2.setId(2L);
        pack2.setTitle("Package 2");

        when(packageService.findAll()).thenReturn(List.of(pack1, pack2));

        mockMvc.perform(get("/package"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Package 1"))  
                .andExpect(jsonPath("$[1].title").value("Package 2"));  
    }

    @Test
    void testSave() throws Exception {
        Package pack = new Package();
        pack.setId(1L);
        pack.setTitle("New Package");
        pack.setDescription("Description");

        when(packageService.postMapping(any(Package.class))).thenReturn(pack);

        mockMvc.perform(post("/package")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"New Package\", \"description\":\"Description\"}"))  // Mudança aqui
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("New Package"));  // Mudança aqui
    }

    @Test
    void testDelete() throws Exception {
        when(packageService.deleteMapping(1L)).thenReturn(ResponseEntity.noContent().build());

        mockMvc.perform(delete("/package/{id}", 1L))
                .andExpect(status().isNoContent())
                .andExpect(content().string("Package deleted successfully"));
    }

    @Test
    void testEdit() throws Exception {
        Package pack = new Package();
        pack.setId(1L);
        pack.setTitle("Updated Package");  // Correção aqui

        when(packageService.putMapping(any(Package.class))).thenReturn(pack);

        mockMvc.perform(put("/package")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"title\":\"Updated Package\"}"))  // Mudança aqui
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Package"));  // Mudança aqui
    }
}

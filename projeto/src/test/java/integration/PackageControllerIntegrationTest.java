// package integration;

// import app.ProjetoApplication;
// import app.controller.PackageController;
// import app.service.PackageService;
// import app.entity.Package;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.web.servlet.MockMvc;
// import org.springframework.http.MediaType;
// import java.util.List;

// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


// @SpringBootTest(classes = ProjetoApplication.class)
// @AutoConfigureMockMvc
// public class PackageControllerIntegrationTest {

//     @Autowired
//     private MockMvc mockMvc;

//     @Autowired
//     private PackageService packageService;

//     @BeforeEach
//     public void setUp() {
//         Package pack = new Package(1L, "Package 1", "Description 1", List.of("Activity 1"));
//         packageService.postMapping(pack);
//     }

//     @Test
//     void testFindById() throws Exception {
//         mockMvc.perform(get("/package/{id}", 1L))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.id").value(1L))
//                 .andExpect(jsonPath("$.title").value("Package 1"))
//                 .andExpect(jsonPath("$.description").value("Description 1"));
//     }

//     @Test
//     void testSave() throws Exception {
//         mockMvc.perform(post("/package")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\"title\":\"New Package\",\"description\":\"New Description\",\"activides\":[\"New Activity\"]}"))
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.title").value("New Package"));
//     }

//     @Test
//     void testDelete() throws Exception {
//         mockMvc.perform(delete("/package/{id}", 1L))
//                 .andExpect(status().isNoContent())
//                 .andExpect(content().string("Package deleted successfully"));
//     }

//     @Test
//     void testEdit() throws Exception {
//         mockMvc.perform(put("/package")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("{\"id\":1,\"title\":\"Updated Package\",\"description\":\"Updated Description\",\"activides\":[\"Updated Activity\"]}"))
//                 .andExpect(status().isOk())
//                 .andExpect(jsonPath("$.title").value("Updated Package"));
//     }
// }

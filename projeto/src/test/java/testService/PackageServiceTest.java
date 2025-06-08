// package testService;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertThrows;
// import static org.mockito.Mockito.when;

// import app.entity.Package;
// import app.repository.PackageRepository;
// import app.service.PackageService;

// import org.junit.jupiter.api.DisplayName;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.extension.ExtendWith;

// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;

// import java.util.Arrays;
// import java.util.List;
// import java.util.Optional;

// @ExtendWith(MockitoExtension.class)
// public class PackageServiceTest {

//     @Mock
//     private PackageRepository packageRepository;

//     @InjectMocks
//     private PackageService packageService;

//     @Test
//     @DisplayName("Unit Test - FindAll: deve retornar lista de pacotes")
//     void testFindAll() {
//         Package pack = new Package();
//         List<Package> mockList = Arrays.asList(pack);
//         when(packageRepository.findAll()).thenReturn(mockList);

//         List<Package> result = packageService.findAll();

//         assertEquals(1, result.size());
//     }

//     @Test
//     @DisplayName("Unit Test - Save e Update: deve salvar e atualizar pacote")
//     void testPostAndPutMapping() {
//         Package pack = new Package();
//         when(packageRepository.save(pack)).thenReturn(pack);

//         Package saved = packageService.postMapping(pack);
//         Package updated = packageService.putMapping(pack);

//         assertEquals(pack, saved);
//         assertEquals(pack, updated);
//     }

//     @Test
//     @DisplayName("Unit Test - FindById: deve retornar pacote pelo id")
//     void testFindById() {
//         Package pack = new Package();
//         when(packageRepository.findById(1L)).thenReturn(Optional.of(pack));

//         Package result = packageService.findById(1L);

//         assertEquals(pack, result);
//     }

//     @Test
//     @DisplayName("Unit Test - FindById: deve lançar exceção se pacote não encontrado")
//     void testFindByIdNotFound() {
//         when(packageRepository.findById(1L)).thenReturn(Optional.empty());

//         assertThrows(RuntimeException.class, () -> packageService.findById(1L));
//     }
// }

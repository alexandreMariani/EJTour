package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import app.service.PackageService;
import app.entity.Package;

@RestController
@RequestMapping(value = "/package")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
                RequestMethod.OPTIONS }, allowedHeaders = "*")
public class PackageController {

        @Autowired
        private PackageService packageService;

        @GetMapping(value = "/{id}")
        public ResponseEntity<?> findById(@PathVariable Long id) {
                Package pack = packageService.findById(id);
                if (pack != null) {
                        return new ResponseEntity<>(pack, HttpStatus.OK);
                } else {
                        return new ResponseEntity<>("package not found", HttpStatus.NOT_FOUND);
                }
        }

        @GetMapping
        public ResponseEntity<Page<Package>> findAll(@PathVariable int numPaginaAtual) {
                Page<Package> lista = packageService.findAll(numPaginaAtual);
                return new ResponseEntity<>(lista, HttpStatus.OK);
        }

        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> save(@RequestBody Package pack) {
                Package savedPackage = packageService.postMapping(pack);
                return new ResponseEntity<>(savedPackage, HttpStatus.CREATED);

        }

        @DeleteMapping(value = "/{id}")
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> delete(@PathVariable Long id) {
                packageService.deleteMapping(id);
                return new ResponseEntity<>("Package deleted successfully", HttpStatus.NO_CONTENT);

        }

        @PutMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<?> edit(@RequestBody Package pack) {
                Package updatedPackage = packageService.putMapping(pack);
                return new ResponseEntity<>(updatedPackage, HttpStatus.OK);
        }

}

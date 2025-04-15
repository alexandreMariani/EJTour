package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import app.service.PackageService;
import app.entity.Package;
import java.util.List;

@RestController
@RequestMapping(value = "/package")
@CrossOrigin(
  origins = "*",
  methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.OPTIONS},
  allowedHeaders = "*"
)
public class PackageController {

    @Autowired
    private PackageService packageService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Package pack = packageService.findById(id);
            if (pack != null) {
                return new ResponseEntity<>(pack, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("package not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching package: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        try {
            List<Package> packages = packageService.findAll();
            return new ResponseEntity<>(packages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error fetching packages: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody Package pack) {
        try {
            Package savedPackage = packageService.postMapping(pack);
            return new ResponseEntity<>(savedPackage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error saving package: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

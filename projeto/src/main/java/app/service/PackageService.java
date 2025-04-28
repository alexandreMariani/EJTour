package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import app.repository.PackageRepository;
import app.entity.Package;

@Service
public class PackageService{

    @Autowired
    private PackageRepository packageRepository;

    public List<Package> findAll() {
        return packageRepository.findAll();
    }

    public Package findById(Long id) {
        Optional<Package> tuor = packageRepository.findById(id);
        if (tuor.isEmpty()) {
            throw new RuntimeException("Atividade não encontrado!");
        }
        return tuor.get();
    }

    public ResponseEntity<Void> deleteMapping(Long id) {

        if (!packageRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        packageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }


    public Package postMapping(@RequestBody Package newPackage) {
    return packageRepository.save(newPackage);
}

    public Package putMapping(@RequestBody Package updatedPackage) {
        return packageRepository.save(updatedPackage);
    }
}

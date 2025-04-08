package app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Package postMapping(Package pack) {
        Package newPack = packageRepository.save(pack); 
        return newPack;
    }
}

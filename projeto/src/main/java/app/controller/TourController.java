package app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import app.entity.Tour;
import app.service.TourService;

import java.util.List;

@RestController
@RequestMapping(value = "/tour")
public class TourController {

    @Autowired
    private TourService tourService;

    @GetMapping(value = "/{id}")
    public Tour findById(@PathVariable Long id){
        return tourService.findById(id);
    }

    @GetMapping
    public List<Tour> findAll(){
        return tourService.findAll();
    }

    @PostMapping
    public Tour save(@RequestBody Tour tour){
        return tourService.postMapping(tour);
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id){
        tourService.deleteMapping(id);
    }

    @PutMapping
    public Tour edit(@RequestBody Tour tour){
        return tourService.putMapping(tour);
    }

}

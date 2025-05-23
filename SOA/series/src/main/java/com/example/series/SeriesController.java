package com.example.series;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/series")
public class SeriesController {

    @Autowired
    private SeriesRepository repository;

    @PostMapping
    public ResponseEntity<?> addSeries(@RequestBody Series series) {
        repository.save(series);
        return ResponseEntity.ok("Series added");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSeries(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok("Series deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSeries(@PathVariable Long id, @RequestBody Series updatedSeries) {
        if (repository.existsById(id)) {
            Series existingSeries = repository.findById(id).orElse(null);
            if (existingSeries != null) {
                existingSeries.setTitle(updatedSeries.getTitle());
                existingSeries.setGenre(updatedSeries.getGenre());
                existingSeries.setYear(updatedSeries.getYear());

                repository.save(existingSeries);
                return ResponseEntity.ok("Series updated successfully");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<?> getSeries(@RequestParam(required = false) String title) {
        if (title != null) {
            List<Series> result = repository.findByTitle(title);
            if (result.isEmpty()) {
                return ResponseEntity.notFound().build();
            } else {
                return ResponseEntity.ok(result);
            }
        } else {
            List<Series> all = repository.findAll();
            return ResponseEntity.ok(all);
        }
    }
}
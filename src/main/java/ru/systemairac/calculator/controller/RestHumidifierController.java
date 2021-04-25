package ru.systemairac.calculator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.systemairac.calculator.domain.humidifier.Humidifier;
import ru.systemairac.calculator.dto.HumidifierDto;
import ru.systemairac.calculator.exception.HumidifierNotFoundException;
import ru.systemairac.calculator.service.allinterface.HumidifierService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/humidifiers")
public class RestHumidifierController {
    private HumidifierService humidifierService;

    @Autowired
    public RestHumidifierController(HumidifierService humidifierService) {
        this.humidifierService = humidifierService;
    }

    @GetMapping(produces = "application/json")
    public List<HumidifierDto> getAllHumidifiers() {
        return humidifierService.getAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<?> getOneHumidifier(@PathVariable Long id) {
        if (!humidifierService.existsById(id)) {
            throw new HumidifierNotFoundException("Humidifier not found, id: " + id);
        }
        return new ResponseEntity<>(humidifierService.findById(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteOneHumidifier(@PathVariable Long id) {
        humidifierService.deleteById(id);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> modifyHumidifier(@RequestBody Humidifier humidifier) {
        if (humidifier.getId() == null || !humidifierService.existsById(humidifier.getId())) {
            throw new HumidifierNotFoundException("Humidifier not found, id: " + humidifier.getId());
        }
        if (humidifier.getPrice().doubleValue() < 0.0) {
            return new ResponseEntity<>("Humidifier's price can not be negative", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(humidifierService.saveOrUpdate(humidifier), HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<?> handleException(HumidifierNotFoundException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
package com.ummati.ummati_core.controller;

import com.ummati.ummati_core.dto.VolunteerDTO;
import com.ummati.ummati_core.service.VolunteerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteers")
public class VolunteerController {

    @Autowired
    private VolunteerService volunteerService;


    @GetMapping
    public ResponseEntity<List<VolunteerDTO>> getAllVolunteers() {
        return ResponseEntity.ok(volunteerService.getAllVolunteers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolunteerDTO> getVolunteerById(@PathVariable Long id) {
        return ResponseEntity.ok(volunteerService.getVolunteerById(id));
    }

    @PostMapping
    public ResponseEntity<VolunteerDTO> createVolunteer(@RequestBody VolunteerDTO volunteerDTO) {
        return ResponseEntity.ok(volunteerService.createVolunteer(volunteerDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VolunteerDTO> updateVolunteer(@PathVariable Long id, @RequestBody VolunteerDTO volunteerDTO) {
        return ResponseEntity.ok(volunteerService.updateVolunteer(id, volunteerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        volunteerService.deleteVolunteer(id);
        return ResponseEntity.noContent().build();
    }
}

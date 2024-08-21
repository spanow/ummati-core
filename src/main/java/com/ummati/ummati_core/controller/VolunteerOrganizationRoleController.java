package com.ummati.ummati_core.controller;

import com.ummati.ummati_core.dto.VolunteerOrganizationRoleDTO;
import com.ummati.ummati_core.service.VolunteerOrganizationRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/volunteer-organization-roles")
public class VolunteerOrganizationRoleController {

    @Autowired
    private VolunteerOrganizationRoleService volunteerOrganizationRoleService;

    @GetMapping
    public ResponseEntity<List<VolunteerOrganizationRoleDTO>> getAllVolunteerOrganizationRoles() {
        return ResponseEntity.ok(volunteerOrganizationRoleService.getAllVolunteerOrganizationRoles());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VolunteerOrganizationRoleDTO> getVolunteerOrganizationRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(volunteerOrganizationRoleService.getVolunteerOrganizationRoleById(id));
    }

    @PostMapping
    public ResponseEntity<VolunteerOrganizationRoleDTO> createVolunteerOrganizationRole(@RequestBody VolunteerOrganizationRoleDTO volunteerOrganizationRoleDTO) {
        return ResponseEntity.ok(volunteerOrganizationRoleService.createVolunteerOrganizationRole(volunteerOrganizationRoleDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VolunteerOrganizationRoleDTO> updateVolunteerOrganizationRole(@PathVariable Long id, @RequestBody VolunteerOrganizationRoleDTO volunteerOrganizationRoleDTO) {
        return ResponseEntity.ok(volunteerOrganizationRoleService.updateVolunteerOrganizationRole(id, volunteerOrganizationRoleDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVolunteerOrganizationRole(@PathVariable Long id) {
        volunteerOrganizationRoleService.deleteVolunteerOrganizationRole(id);
        return ResponseEntity.noContent().build();
    }
}

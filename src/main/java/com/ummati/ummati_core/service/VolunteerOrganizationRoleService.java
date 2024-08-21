package com.ummati.ummati_core.service;

import com.ummati.ummati_core.dto.GenericMapper;
import com.ummati.ummati_core.dto.VolunteerOrganizationRoleDTO;
import com.ummati.ummati_core.entity.VolunteerOrganizationRoleEntity;
import com.ummati.ummati_core.exception.DeletionFailedException;
import com.ummati.ummati_core.exception.ResourceNotFoundException;
import com.ummati.ummati_core.repository.VolunteerOrganizationRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class VolunteerOrganizationRoleService {

    @Autowired
    private VolunteerOrganizationRoleRepository volunteerOrganizationRoleRepository;

    @Autowired
    private GenericMapper genericMapper;

    public List<VolunteerOrganizationRoleDTO> getAllVolunteerOrganizationRoles() {
        List<VolunteerOrganizationRoleEntity> roles = volunteerOrganizationRoleRepository.findAll();
        return genericMapper.toDTOList(roles, VolunteerOrganizationRoleDTO.class);
    }

    public VolunteerOrganizationRoleDTO getVolunteerOrganizationRoleById(Long id) {
        VolunteerOrganizationRoleEntity role = volunteerOrganizationRoleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(format("VolunteerOrganizationRole with id: %s not found", id)));
        return genericMapper.toDTO(role, VolunteerOrganizationRoleDTO.class);
    }

    public VolunteerOrganizationRoleDTO createVolunteerOrganizationRole(VolunteerOrganizationRoleDTO volunteerOrganizationRoleDTO) {
        VolunteerOrganizationRoleEntity role = genericMapper.toEntity(volunteerOrganizationRoleDTO, VolunteerOrganizationRoleEntity.class);
        role = volunteerOrganizationRoleRepository.save(role);
        return genericMapper.toDTO(role, VolunteerOrganizationRoleDTO.class);
    }

    public VolunteerOrganizationRoleDTO updateVolunteerOrganizationRole(Long id, VolunteerOrganizationRoleDTO volunteerOrganizationRoleDTO) {
        VolunteerOrganizationRoleEntity existingRole = volunteerOrganizationRoleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Volunteer Organization Role not found"));
        existingRole.setRole(genericMapper.toEntity(volunteerOrganizationRoleDTO, VolunteerOrganizationRoleEntity.class).getRole());
        VolunteerOrganizationRoleEntity updatedRole = volunteerOrganizationRoleRepository.save(existingRole);
        return genericMapper.toDTO(updatedRole, VolunteerOrganizationRoleDTO.class);
    }

    public void deleteVolunteerOrganizationRole(Long id) {
        if (!volunteerOrganizationRoleRepository.existsById(id)) {
            throw new ResourceNotFoundException(format("VolunteerOrganizationRole with id: %s not found", id));
        }

        try {
            volunteerOrganizationRoleRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeletionFailedException(format("Failed to delete VolunteerOrganizationRole with id: %s. Reason: %s", id, e.getMessage()));
        }
    }
}

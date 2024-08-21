package com.ummati.ummati_core.service;

import com.ummati.ummati_core.dto.GenericMapper;
import com.ummati.ummati_core.dto.RoleDTO;
import com.ummati.ummati_core.entity.RoleEntity;
import com.ummati.ummati_core.exception.DeletionFailedException;
import com.ummati.ummati_core.exception.ResourceNotFoundException;
import com.ummati.ummati_core.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private GenericMapper genericMapper;

    public List<RoleDTO> getAllRoles() {
        List<RoleEntity> roles = roleRepository.findAll();
        return genericMapper.toDTOList(roles, RoleDTO.class);
    }

    public RoleDTO getRoleById(Long id) {
        RoleEntity role = roleRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(format("Role with id: %s not found", id)));
        return genericMapper.toDTO(role, RoleDTO.class);
    }

    public RoleDTO createRole(RoleDTO roleDTO) {
        RoleEntity role = genericMapper.toEntity(roleDTO, RoleEntity.class);
        role = roleRepository.save(role);
        return genericMapper.toDTO(role, RoleDTO.class);
    }

    public RoleDTO updateRole(Long id, RoleDTO roleDTO) {
        RoleEntity existingRole = roleRepository.findById(id).orElseThrow(() -> new RuntimeException("Role not found"));
        existingRole.setName(roleDTO.getName());
        RoleEntity updatedRole = roleRepository.save(existingRole);
        return genericMapper.toDTO(updatedRole, RoleDTO.class);
    }

    public void deleteRole(Long id) {
        if (!roleRepository.existsById(id)) {
            throw new ResourceNotFoundException(format("Role with id: %s not found", id));
        }

        try {
            roleRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeletionFailedException(format("Failed to delete role with id: %s. Reason: %s", id, e.getMessage()));
        }
    }
}

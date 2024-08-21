package com.ummati.ummati_core.service;

import com.ummati.ummati_core.dto.GenericMapper;
import com.ummati.ummati_core.dto.VolunteerDTO;
import com.ummati.ummati_core.entity.*;
import com.ummati.ummati_core.exception.DeletionFailedException;
import com.ummati.ummati_core.exception.ResourceNotFoundException;
import com.ummati.ummati_core.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class VolunteerService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final VolunteerRepository volunteerRepository;
    private final GenericMapper genericMapper;
    private final SkillRepository skillRepository;
    private final OrganizationRepository organizationRepository;
    private final EventRepository eventRepository;
    private final VolunteerOrganizationRoleRepository volunteerOrganizationRoleRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository, BCryptPasswordEncoder bCryptPasswordEncoder, GenericMapper genericMapper, SkillRepository skillRepository, OrganizationRepository organizationRepository, EventRepository eventRepository, VolunteerOrganizationRoleRepository volunteerOrganizationRoleRepository) {
        this.volunteerRepository = volunteerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.genericMapper = genericMapper;
        this.skillRepository = skillRepository;
        this.organizationRepository = organizationRepository;
        this.eventRepository = eventRepository;
        this.volunteerOrganizationRoleRepository = volunteerOrganizationRoleRepository;
    }

    private VolunteerEntity toEntity(VolunteerDTO volunteerDTO) {
        VolunteerEntity volunteer = genericMapper.toEntity(volunteerDTO, VolunteerEntity.class);
        volunteer.setSkills(volunteerDTO.getSkillIds().stream().map(skillId -> skillRepository.findById(skillId).orElseThrow(() -> new RuntimeException("Skill not found"))).toList());
        volunteer.setOrganizations(volunteerDTO.getOrganizationIds().stream().map(organizationId -> organizationRepository.findById(organizationId).orElseThrow(() -> new RuntimeException("Organization not found"))).toList());
        volunteer.setEvents(volunteerDTO.getEventIds().stream().map(eventId -> eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"))).toList());
        volunteer.setOrganizationRoles(volunteerDTO.getOrganizationRoleIds().stream().map(organizationRoleId -> volunteerOrganizationRoleRepository.findById(organizationRoleId).orElseThrow(() -> new RuntimeException("OrganizationRole not found"))).toList());
        return volunteer;
    }

    private VolunteerDTO toDTO(VolunteerEntity volunteerEntity) {
        VolunteerDTO volunteerDTO = genericMapper.toDTO(volunteerEntity, VolunteerDTO.class);
        volunteerDTO.setSkillIds(volunteerEntity.getSkills().stream().map(SkillEntity::getId).toList());
        volunteerDTO.setOrganizationIds(volunteerEntity.getOrganizations().stream().map(OrganizationEntity::getId).toList());
        volunteerDTO.setEventIds(volunteerEntity.getEvents().stream().map(EventEntity::getId).toList());
        volunteerDTO.setOrganizationRoleIds(volunteerEntity.getOrganizationRoles().stream().map(VolunteerOrganizationRoleEntity::getId).toList());
        return volunteerDTO;
    }

    private List<VolunteerDTO> toDTOs(List<VolunteerEntity> volunteerEntities) {
        return volunteerEntities.stream().map(this::toDTO).toList();
    }

    public List<VolunteerDTO> getAllVolunteers() {
        return toDTOs(volunteerRepository.findAll());
    }

    public VolunteerDTO getVolunteerById(Long id) {
        VolunteerEntity volunteerEntity = volunteerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(format("Volunteer with id: %s not found", id)));
        return toDTO(volunteerEntity);
    }

    public VolunteerDTO createVolunteer(VolunteerDTO volunteerDTO) {
        VolunteerEntity volunteerEntity = toEntity(volunteerDTO);
        volunteerEntity.setPassword(bCryptPasswordEncoder.encode(volunteerEntity.getPassword()));
        volunteerEntity = volunteerRepository.save(volunteerEntity);
        return toDTO(volunteerEntity);
    }

    public VolunteerDTO updateVolunteer(Long id, VolunteerDTO volunteerDTO) {
        VolunteerEntity existingVolunteer = volunteerRepository.findById(id).orElseThrow(() -> new RuntimeException("Volunteer not found"));
        existingVolunteer.setFirstName(volunteerDTO.getFirstName());
        existingVolunteer.setLastName(volunteerDTO.getLastName());
        existingVolunteer.setEmail(volunteerDTO.getEmail());
        existingVolunteer.setPhoneNumber(volunteerDTO.getPhoneNumber());
        existingVolunteer.setAddress(volunteerDTO.getAddress());
        existingVolunteer.setCity(volunteerDTO.getCity());
        existingVolunteer.setState(volunteerDTO.getState());
        existingVolunteer.setCountry(volunteerDTO.getCountry());
        existingVolunteer.setPostalCode(volunteerDTO.getPostalCode());
        existingVolunteer.setActive(volunteerDTO.isActive());
        existingVolunteer.setConfirmedEmail(volunteerDTO.isConfirmedEmail());
        existingVolunteer.setConfirmedPhoneNumber(volunteerDTO.isConfirmedPhoneNumber());
        VolunteerEntity updatedVolunteer = volunteerRepository.save(existingVolunteer);
        return toDTO(updatedVolunteer);
    }

    public void deleteVolunteer(Long id) {
        if (!volunteerRepository.existsById(id)) {
            throw new ResourceNotFoundException(format("Volunteer with id: %s not found", id));
        }

        try {
            volunteerRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeletionFailedException(format("Failed to delete volunteer with id: %s. Reason: %s", id, e.getMessage()));
        }
    }
}

package com.ummati.ummati_core.service;

import com.ummati.ummati_core.dto.GenericMapper;
import com.ummati.ummati_core.dto.OrganizationDTO;
import com.ummati.ummati_core.entity.EventEntity;
import com.ummati.ummati_core.entity.OrganizationEntity;
import com.ummati.ummati_core.entity.VolunteerEntity;
import com.ummati.ummati_core.entity.VolunteerOrganizationRoleEntity;
import com.ummati.ummati_core.exception.DeletionFailedException;
import com.ummati.ummati_core.exception.ResourceNotFoundException;
import com.ummati.ummati_core.repository.EventRepository;
import com.ummati.ummati_core.repository.OrganizationRepository;
import com.ummati.ummati_core.repository.VolunteerOrganizationRoleRepository;
import com.ummati.ummati_core.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class OrganizationService {

    private final OrganizationRepository organizationRepository;

    private final GenericMapper genericMapper;
    private final VolunteerRepository volunteerRepository;
    private final EventRepository eventRepository;
    private final VolunteerOrganizationRoleRepository volunteerOrganizationRoleRepository;

    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository, GenericMapper genericMapper, VolunteerRepository volunteerRepository, EventRepository eventRepository, VolunteerOrganizationRoleRepository volunteerOrganizationRoleRepository) {
        this.organizationRepository = organizationRepository;
        this.genericMapper = genericMapper;
        this.volunteerRepository = volunteerRepository;
        this.eventRepository = eventRepository;
        this.volunteerOrganizationRoleRepository = volunteerOrganizationRoleRepository;
    }

    private OrganizationEntity toEntity(OrganizationDTO organizationDTO) {
        OrganizationEntity organization = genericMapper.toEntity(organizationDTO, OrganizationEntity.class);
        organization.setVolunteers(organizationDTO.getVolunteerIds().stream().map(volunteerId -> volunteerRepository.findById(volunteerId).orElseThrow(() -> new RuntimeException("Volunteer not found"))).toList());
        organization.setEvents(organizationDTO.getEventIds().stream().map(eventId -> eventRepository.findById(eventId).orElseThrow(() -> new RuntimeException("Event not found"))).toList());
        organization.setVolunteerRoles(organizationDTO.getVolunteerRoleIds().stream().map(volunteerRoleId -> volunteerOrganizationRoleRepository.findById(volunteerRoleId).orElseThrow(() -> new RuntimeException("VolunteerRole in the organization not found"))).toList());
        return organization;
    }

    private OrganizationDTO toDTO(OrganizationEntity organization) {
        OrganizationDTO organizationDTO = genericMapper.toDTO(organization, OrganizationDTO.class);
        organizationDTO.setVolunteerIds(organization.getVolunteers().stream().map(VolunteerEntity::getId).toList());
        organizationDTO.setEventIds(organization.getEvents().stream().map(EventEntity::getId).toList());
        organizationDTO.setVolunteerRoleIds(organization.getVolunteerRoles().stream().map(VolunteerOrganizationRoleEntity::getId).toList());
        return organizationDTO;
    }

    private List<OrganizationDTO> toDTOs(List<OrganizationEntity> organizationEntities) {
        return organizationEntities.stream().map(this::toDTO).toList();
    }

    public List<OrganizationDTO> getAllOrganizations() {
        List<OrganizationEntity> organizations = organizationRepository.findAll();
        return toDTOs(organizations);
    }

    public OrganizationDTO getOrganizationById(Long id) {
        OrganizationEntity organization = organizationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(format("Organization with id: %s not found", id)));
        return toDTO(organization);
    }

    public OrganizationDTO createOrganization(OrganizationDTO organizationDTO) {
        OrganizationEntity organization = toEntity(organizationDTO);
        organization = organizationRepository.save(organization);
        return toDTO(organization);
    }

    public OrganizationDTO updateOrganization(Long id, OrganizationDTO organizationDTO) {
        OrganizationEntity existingOrganization = organizationRepository.findById(id).orElseThrow(() -> new RuntimeException("Organization not found"));
        existingOrganization.setName(organizationDTO.getName());
        existingOrganization.setDescription(organizationDTO.getDescription());
        existingOrganization.setEmail(organizationDTO.getEmail());
        existingOrganization.setPhoneNumber(organizationDTO.getPhoneNumber());
        existingOrganization.setAddress(organizationDTO.getAddress());
        existingOrganization.setCity(organizationDTO.getCity());
        existingOrganization.setState(organizationDTO.getState());
        existingOrganization.setCountry(organizationDTO.getCountry());
        existingOrganization.setPostalCode(organizationDTO.getPostalCode());
        existingOrganization.setValidated(organizationDTO.isValidated());
        OrganizationEntity updatedOrganization = organizationRepository.save(existingOrganization);
        return toDTO(updatedOrganization);
    }

    public void deleteOrganizationById(Long id) {
        if (!organizationRepository.existsById(id)) {
            throw new ResourceNotFoundException(String.format("Organization with id: %s not found", id));
        }

        try {
            organizationRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeletionFailedException(String.format("Failed to delete organization with id: %s. Reason: %s", id, e.getMessage()));
        }
    }

}

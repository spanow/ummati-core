package com.ummati.ummati_core.service;

import com.ummati.ummati_core.dto.EventDTO;
import com.ummati.ummati_core.dto.GenericMapper;
import com.ummati.ummati_core.entity.EventEntity;
import com.ummati.ummati_core.entity.VolunteerEntity;
import com.ummati.ummati_core.exception.DeletionFailedException;
import com.ummati.ummati_core.exception.ResourceNotFoundException;
import com.ummati.ummati_core.repository.EventRepository;
import com.ummati.ummati_core.repository.VolunteerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class EventService {

    private final EventRepository eventRepository;

    private final GenericMapper genericMapper;
    private final VolunteerRepository volunteerRepository;

    @Autowired
    public EventService(EventRepository eventRepository, GenericMapper genericMapper, VolunteerRepository volunteerRepository) {
        this.eventRepository = eventRepository;
        this.genericMapper = genericMapper;
        this.volunteerRepository = volunteerRepository;
    }

    private EventEntity toEntity(EventDTO eventDTO) {
        EventEntity event = genericMapper.toEntity(eventDTO, EventEntity.class);
        event.setVolunteers(eventDTO.getVolunteerIds().stream().map(volunteerId -> volunteerRepository.findById(volunteerId).orElseThrow(() -> new RuntimeException("Volunteer not found"))).toList());
        return event;
    }

    private EventDTO toDTO(EventEntity eventEntity) {
        EventDTO eventDTO = genericMapper.toDTO(eventEntity, EventDTO.class);
        eventDTO.setVolunteerIds(eventEntity.getVolunteers().stream().map(VolunteerEntity::getId).toList());
        return eventDTO;
    }

    private List<EventDTO> toDTOs(List<EventEntity> eventEntities) {
        return eventEntities.stream().map(this::toDTO).toList();
    }


    public List<EventDTO> getAllEvents() {
        List<EventEntity> events = eventRepository.findAll();
        return toDTOs(events);
    }

    public EventDTO getEventById(Long id) {
        EventEntity event = eventRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(format("Event with id: %s not found", id)));
        return toDTO(event);
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        EventEntity event = toEntity(eventDTO);
        event = eventRepository.save(event);
        return toDTO(event);
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        EventEntity existingEvent = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        existingEvent.setTitle(eventDTO.getTitle());
        existingEvent.setDescription(eventDTO.getDescription());
        existingEvent.setLocation(eventDTO.getLocation());
        existingEvent.setDate(eventDTO.getDate());
        existingEvent.setDuration(eventDTO.getDuration());
        existingEvent.setMaxParticipants(eventDTO.getMaxParticipants());
        existingEvent.setStatus(eventDTO.getStatus());
        EventEntity updatedEvent = eventRepository.save(existingEvent);
        return toDTO(updatedEvent);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException(format("Event with id: %s not found", id));
        }

        try {
            eventRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeletionFailedException(format("Failed to delete event with id: %s. Reason: %s", id, e.getMessage()));
        }
    }

}

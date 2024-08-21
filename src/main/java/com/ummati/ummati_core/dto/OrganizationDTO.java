package com.ummati.ummati_core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationDTO {

    private Long id;
    private String name;
    private String description;
    private String email;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean validated;

    // Liste des identifiants des bénévoles associés à l'organisation
    private List<Long> volunteerIds;

    // Liste des identifiants des événements associés à l'organisation
    private List<Long> eventIds;

    // Liste des identifiants des rôles des bénévoles dans cette organisation
    private List<Long> volunteerRoleIds;
}

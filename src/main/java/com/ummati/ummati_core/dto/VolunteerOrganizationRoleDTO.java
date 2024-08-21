package com.ummati.ummati_core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerOrganizationRoleDTO {

    private Long id;

    // Identifiant du bénévole associé à ce rôle
    private Long volunteerId;

    // Identifiant de l'organisation associée à ce rôle
    private Long organizationId;

    // Identifiant du rôle que le bénévole occupe dans cette organisation
    private Long roleId;
}

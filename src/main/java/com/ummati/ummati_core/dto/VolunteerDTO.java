package com.ummati.ummati_core.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class VolunteerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String city;
    private String state;
    private String country;
    private String postalCode;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean isActive;
    private boolean confirmedEmail;
    private boolean confirmedPhoneNumber;

    // Liste des identifiants des organisations associées
    private List<Long> organizationIds;

    // Liste des identifiants des événements auxquels le bénévole participe
    private List<Long> eventIds;

    // Liste des identifiants des rôles que le bénévole occupe dans les organisations
    private List<Long> organizationRoleIds;

    // Liste des identifiants des compétences du bénévole
    private List<Long> skillIds;

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }
}

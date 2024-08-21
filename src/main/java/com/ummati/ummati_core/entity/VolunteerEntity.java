package com.ummati.ummati_core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "volunteers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VolunteerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private String password;

    @Column
    private String address;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String country;

    @Column
    private String postalCode;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    @Column
    private boolean isActive = true;

    @Column
    private boolean confirmedEmail = false;

    @Column
    private boolean confirmedPhoneNumber = false;

    @ManyToMany(mappedBy = "volunteers")
    private List<OrganizationEntity> organizations;

    @ManyToMany(mappedBy = "volunteers")
    private List<EventEntity> events;

    @OneToMany(mappedBy = "volunteer")
    private List<VolunteerOrganizationRoleEntity> organizationRoles;

    @ManyToMany
    @JoinTable(name = "volunteer_skills", joinColumns = @JoinColumn(name = "volunteer_id"), inverseJoinColumns = @JoinColumn(name = "skill_id"))
    private List<SkillEntity> skills;  // Les compétences du bénévole


    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

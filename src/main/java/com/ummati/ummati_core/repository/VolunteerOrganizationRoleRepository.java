package com.ummati.ummati_core.repository;

import com.ummati.ummati_core.entity.VolunteerOrganizationRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VolunteerOrganizationRoleRepository extends JpaRepository<VolunteerOrganizationRoleEntity, Long> {
}
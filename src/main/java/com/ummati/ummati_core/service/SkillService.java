package com.ummati.ummati_core.service;

import com.ummati.ummati_core.dto.GenericMapper;
import com.ummati.ummati_core.dto.SkillDTO;
import com.ummati.ummati_core.entity.SkillEntity;
import com.ummati.ummati_core.exception.DeletionFailedException;
import com.ummati.ummati_core.exception.ResourceNotFoundException;
import com.ummati.ummati_core.repository.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.String.format;

@Service
public class SkillService {

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private GenericMapper genericMapper;

    public List<SkillDTO> getAllSkills() {
        List<SkillEntity> skills = skillRepository.findAll();
        return genericMapper.toDTOList(skills, SkillDTO.class);
    }

    public SkillDTO getSkillById(Long id) {
        SkillEntity skill = skillRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(format("Skill with id: %s not found", id)));
        return genericMapper.toDTO(skill, SkillDTO.class);
    }

    public SkillDTO createSkill(SkillDTO skillDTO) {
        SkillEntity skill = genericMapper.toEntity(skillDTO, SkillEntity.class);
        skill = skillRepository.save(skill);
        return genericMapper.toDTO(skill, SkillDTO.class);
    }

    public SkillDTO updateSkill(Long id, SkillDTO skillDTO) {
        SkillEntity existingSkill = skillRepository.findById(id).orElseThrow(() -> new RuntimeException("Skill not found"));
        existingSkill.setName(skillDTO.getName());
        SkillEntity updatedSkill = skillRepository.save(existingSkill);
        return genericMapper.toDTO(updatedSkill, SkillDTO.class);
    }

    public void deleteSkill(Long id) {
        if (!skillRepository.existsById(id)) {
            throw new ResourceNotFoundException(format("Skill with id: %s not found", id));
        }

        try {
            skillRepository.deleteById(id);
        } catch (Exception e) {
            throw new DeletionFailedException(format("Failed to delete skill with id: %s. Reason: %s", id, e.getMessage()));
        }
    }

}

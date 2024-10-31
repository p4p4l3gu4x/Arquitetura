package com.fidelity.backend.model.dto.converter;

import com.fidelity.backend.model.dto.BaseDTO;
import com.fidelity.backend.model.entity.BaseEntity;

public interface BaseEntityConverter<K extends BaseEntity<?>, Y extends BaseDTO<?>> {

    K convertToEntity(Y dto);

    Y convertToDTO(K entity);
}

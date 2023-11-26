package com.appware_system.books.service;

import com.appware_system.books.model.entity.RoleEntity;
import org.springframework.stereotype.Service;

@Service
public interface RoleService {
    void saveRole(RoleEntity role);
}

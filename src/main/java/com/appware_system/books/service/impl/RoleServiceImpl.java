package com.appware_system.books.service.impl;

import com.appware_system.books.model.entity.RoleEntity;
import com.appware_system.books.repository.RoleRepository;
import com.appware_system.books.service.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Saves a RoleEntity object by persisting it in the repository.
     *
     * @param role the RoleEntity object to save
     */
    @Override
    public void saveRole(RoleEntity role) {
        roleRepository.save(role);
    }



}

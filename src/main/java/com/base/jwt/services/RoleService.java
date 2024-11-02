package com.base.jwt.services;

import com.base.jwt.models.Permission;
import com.base.jwt.models.Role;
import com.base.jwt.repositories.PermissionRepository;
import com.base.jwt.repositories.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Transactional
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    public Role save(Role role) {
        Set<Permission> permissions = role.getPermissions().stream()
                .map(permission -> permissionRepository.findByName(permission.getName()).orElse(permission))
                .collect(Collectors.toSet());
        role.setPermissions(permissions);

        return roleRepository.save(role);
    }

    public Role update(Integer id, Role role) {
        Role foundRole = roleRepository.findById_(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found."));

        Set<Permission> permissions = role.getPermissions().stream()
                .map(permission -> permissionRepository.findByName(permission.getName()).orElse(permission))
                .collect(Collectors.toSet());
        foundRole.setPermissions(permissions);

        return foundRole;
    }

    public void delete(Integer id) {
        roleRepository.deleteById(id);
    }
}
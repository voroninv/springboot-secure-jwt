package com.base.jwt.controllers;

import com.base.jwt.models.Role;
import com.base.jwt.services.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;

    @GetMapping
    public ResponseEntity<List<Role>> findAll() {
        LOGGER.info("find roles request initiated.");
        List<Role> roles = roleService.findAll();
        LOGGER.info("find roles request processed.");

        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<Role> save(@RequestBody Role role) {
        LOGGER.info("save role: {} request initiated.", role.getName());
        Role savedRole = roleService.save(role);
        LOGGER.info("save role: {} request processed.", role.getName());

        return ResponseEntity.status(HttpStatus.CREATED).body(savedRole);
    }

    @PutMapping("{id}")
    public ResponseEntity<Role> update(@PathVariable Integer id, @RequestBody Role role) {
        LOGGER.info("update role: {} request initiated.", role.getName());
        Role savedRole = roleService.update(id, role);
        LOGGER.info("update role: {} request processed.", role.getName());

        return ResponseEntity.ok(savedRole);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        LOGGER.info("delete role id: {} request initiated.", id);
        roleService.delete(id);
        LOGGER.info("delete role id: {} request processed.", id);

        return ResponseEntity.ok().build();
    }
}

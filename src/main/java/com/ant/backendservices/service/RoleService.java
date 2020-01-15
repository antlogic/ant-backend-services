package com.ant.backendservices.service;

import com.ant.backendservices.exception.AppException;
import com.ant.backendservices.model.Role;
import com.ant.backendservices.model.RoleName;
import com.ant.backendservices.repository.RoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    private static List<Role> configRoles() {
        List<Role> appRoles = new ArrayList<>();

        Role roleAdmin = new Role();
        roleAdmin.setName(RoleName.ROLE_ADMIN);

        Role roleUser = new Role();
        roleUser.setName(RoleName.ROLE_USER);

        appRoles.add(roleAdmin);
        appRoles.add(roleUser);

        return appRoles;
    }

    @PostConstruct
    @Transactional
    public void initAppRoles() {
        if (CollectionUtils.isEmpty(roleRepository.findAll())) {
            configRoles().forEach(role -> roleRepository.save(role));
            log.info("Initialized application access roles.");
        }
    }

    public Set<Role> getRoles(List<RoleName> roleNames) {
        Set<Role> roles = new HashSet<>();
        roleNames.forEach(roleName -> {
            Role role = getRole(roleName);
            roles.add(role);
        });
        return roles;
    }

    @Transactional
    public Role getRole(RoleName roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new AppException("User Role not set."));
    }
}

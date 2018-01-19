package demo.app.web.facade;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.app.core.service.SearchFilter;
import demo.app.sso.domain.EntityAudit;
import demo.app.sso.domain.Permission;
import demo.app.sso.domain.UserRole;
import demo.app.sso.service.EntityAuditService;
import demo.app.sso.service.PermissionService;
import demo.app.sso.service.UserRoleService;
import demo.app.web.mapper.ModelMapper;
import demo.app.web.vo.EntityAuditVO;
import demo.app.web.vo.UserRoleVO;

@Service
public class UserRoleFacadeImpl implements UserRoleFacade {

    @Autowired
    private UserRoleService                   service;

    @Autowired
    private PermissionService                 permissionService;

    @Autowired
    private EntityAuditService                entityAuditService;

    @Autowired
    private ModelMapper<UserRole, UserRoleVO> mapper;

    @Override
    public Page<UserRoleVO> list(SearchFilter filter, Pageable pageable) {

        Page<UserRole> page = null;
        if (filter == null || filter.isEmpty()) {
            page = service.findAll(pageable);
        } else {
            page = service.findByName(filter.getFilter(), pageable);
        }

        return mapper.mapTo(page, pageable, UserRoleVO.class);
    }

    @Override
    public UserRoleVO find(Integer id) {

        UserRole record = service.findById(id);
        return mapper.mapTo(record, UserRoleVO.class);
    }

    @Override
    public void delete(Integer id) {

        service.delete(id);
    }

    @Override
    public UserRoleVO create(UserRoleVO vo) {

        UserRole entity = mapper.mapFrom(vo, UserRole.class);

        // all associated entities need to be loaded and set into the mapped entity so JPA can persist the entity
        entity.setPermissions(loadManagedPermissions(vo));

        entity = service.create(entity);
        return mapper.mapTo(entity, UserRoleVO.class);
    }

    @Override
    public UserRoleVO update(UserRoleVO vo) {

        UserRole entity = mapper.mapFrom(vo, UserRole.class);

        // all associated entities need to be loaded and set into the mapped entity so JPA can persist the entity
        entity.setPermissions(loadManagedPermissions(vo));
        entity.setAudit(loadManagedAudit(vo));

        entity = service.update(entity);
        return mapper.mapTo(entity, UserRoleVO.class);
    }

    private EntityAudit loadManagedAudit(UserRoleVO vo) {
        return Optional.ofNullable(vo.getAudit()).map(EntityAuditVO::getId)
                .map(auditId -> entityAuditService.findById(auditId))
                .orElse(null);
    }

    private Set<Permission> loadManagedPermissions(UserRoleVO vo) {
        return Optional.ofNullable(vo.getPermissions()).orElse(new ArrayList<>()).stream()
                .map(detachedPermission -> permissionService.findById(detachedPermission.getId()))
                .collect(Collectors.toSet());
    }
}

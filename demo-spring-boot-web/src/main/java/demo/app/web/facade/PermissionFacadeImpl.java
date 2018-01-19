package demo.app.web.facade;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.app.core.service.SearchFilter;
import demo.app.sso.domain.EntityAudit;
import demo.app.sso.domain.Permission;
import demo.app.sso.service.EntityAuditService;
import demo.app.sso.service.PermissionService;
import demo.app.web.mapper.ModelMapper;
import demo.app.web.vo.EntityAuditVO;
import demo.app.web.vo.PermissionVO;

@Service
public class PermissionFacadeImpl implements PermissionFacade {

    @Autowired
    private PermissionService                     service;

    @Autowired
    private EntityAuditService                    entityAuditService;

    @Autowired
    private ModelMapper<Permission, PermissionVO> mapper;

    @Override
    public Page<PermissionVO> list(SearchFilter filter, Pageable pageable) {

        Page<Permission> page = null;
        if (filter == null || filter.isEmpty()) {
            page = service.findAll(pageable);
        } else {
            page = service.findByName(filter.getFilter(), pageable);
        }

        return mapper.mapTo(page, pageable, PermissionVO.class);
    }

    @Override
    public PermissionVO find(Integer id) {

        Permission record = service.findById(id);
        return mapper.mapTo(record, PermissionVO.class);
    }

    @Override
    public void delete(Integer id) {

        service.delete(id);
    }

    @Override
    public PermissionVO create(PermissionVO vo) {

        Permission entity = mapper.mapFrom(vo, Permission.class);
        entity = service.create(entity);
        return mapper.mapTo(entity, PermissionVO.class);
    }

    @Override
    public PermissionVO update(PermissionVO vo) {

        Permission entity = mapper.mapFrom(vo, Permission.class);

        // all associated entities need to be loaded and set into the mapped entity so JPA can persist the entity
        entity.setAudit(loadManagedAudit(vo));

        entity = service.update(entity);
        return mapper.mapTo(entity, PermissionVO.class);
    }

    private EntityAudit loadManagedAudit(PermissionVO vo) {
        return Optional.ofNullable(vo.getAudit()).map(EntityAuditVO::getId)
                .map(auditId -> entityAuditService.findById(auditId))
                .orElse(null);
    }
}

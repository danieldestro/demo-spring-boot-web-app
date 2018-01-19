package demo.app.web.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.app.core.service.CrudServiceImpl;
import demo.app.core.service.SearchFilter;
import demo.app.sso.domain.EntityAudit;
import demo.app.sso.domain.UserAccount;
import demo.app.sso.domain.UserRole;
import demo.app.sso.service.EntityAuditService;
import demo.app.sso.service.UserAccountService;
import demo.app.sso.service.UserRoleService;
import demo.app.web.mapper.ModelMapper;
import demo.app.web.vo.EntityAuditVO;
import demo.app.web.vo.UserAccountVO;

@Service
public class UserAccountFacadeImpl extends CrudServiceImpl<UserAccount, Integer> implements UserAccountFacade {

    @Autowired
    private UserAccountService                      service;

    @Autowired
    private UserRoleService                         userRoleService;

    @Autowired
    private EntityAuditService                      entityAuditService;

    @Autowired
    private ModelMapper<UserAccount, UserAccountVO> mapper;

    @Override
    public Page<UserAccountVO> list(SearchFilter filter, Pageable pageable) {
        Page<UserAccount> page = null;
        if (filter == null || filter.isEmpty()) {
            page = service.findAll(pageable);
        } else {
            page = service.findByEmailAddressContaining(filter.getFilter(), pageable);
        }
        return mapper.mapTo(page, pageable, UserAccountVO.class);
    }

    @Override
    public String findEmployeeNumber(String email) {
        return service.findEmployeeNumber(email);
    }

    @Override
    public UserAccountVO find(Integer id) {

        UserAccount record = service.findById(id);
        return mapper.mapTo(record, UserAccountVO.class);
    }

    @Override
    public void delete(Integer id) {

        service.delete(id);
    }

    @Override
    public UserAccountVO create(UserAccountVO vo) {

        UserAccount entity = mapper.mapFrom(vo, UserAccount.class);

        // all associated entities need to be loaded and set into the mapped entity so JPA can persist the entity
        entity.setRoles(loadManagedRoles(vo));

        entity = service.create(entity);
        return mapper.mapTo(entity, UserAccountVO.class);
    }

    @Override
    public UserAccountVO update(UserAccountVO vo) {

        UserAccount entity = mapper.mapFrom(vo, UserAccount.class);

        // all associated entities need to be loaded and set into the mapped entity so JPA can persist the entity
        entity.setRoles(loadManagedRoles(vo));
        entity.setAudit(loadManagedAudit(vo));

        entity = service.update(entity);
        return mapper.mapTo(entity, UserAccountVO.class);
    }

    private List<UserRole> loadManagedRoles(UserAccountVO vo) {
        return Optional.ofNullable(vo.getRoles()).orElse(new ArrayList<>()).stream()
                .map(detachedRole -> userRoleService.findById(detachedRole.getId()))
                .collect(Collectors.toList());
    }

    private EntityAudit loadManagedAudit(UserAccountVO vo) {
        return Optional.ofNullable(vo.getAudit()).map(EntityAuditVO::getId)
                .map(auditId -> entityAuditService.findById(auditId))
                .orElse(null);
    }
}

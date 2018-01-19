package demo.app.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.app.core.service.CrudServiceImpl;
import demo.app.sso.domain.EntityAudit;
import demo.app.sso.repository.EntityAuditRepository;

@Service
@SuppressWarnings("unused")
public class EntityAuditServiceImpl extends CrudServiceImpl<EntityAudit, Integer> implements EntityAuditService {

    @Autowired
    private EntityAuditRepository repository;

}

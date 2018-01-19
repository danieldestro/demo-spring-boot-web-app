package demo.app.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.app.core.service.CrudServiceImpl;
import demo.app.sso.domain.Permission;
import demo.app.sso.repository.PermissionRepository;

@Service
public class PermissionServiceImpl extends CrudServiceImpl<Permission, Integer> implements PermissionService {

    @Autowired
    private PermissionRepository repository;

    @Override
    public Page<Permission> findByName(String name, Pageable page) {
        return repository.findByNameContaining(name, page);
    }

}

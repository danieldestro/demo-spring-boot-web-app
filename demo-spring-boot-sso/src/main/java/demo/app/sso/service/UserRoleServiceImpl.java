package demo.app.sso.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.app.core.service.CrudServiceImpl;
import demo.app.sso.domain.UserRole;
import demo.app.sso.repository.UserRoleRepository;

@Service
public class UserRoleServiceImpl extends CrudServiceImpl<UserRole, Integer> implements UserRoleService {

    @Autowired
    private UserRoleRepository repository;

    @Override
    public Page<UserRole> findByName(String name, Pageable page) {
        return repository.findByNameContaining(name, page);
    }

}

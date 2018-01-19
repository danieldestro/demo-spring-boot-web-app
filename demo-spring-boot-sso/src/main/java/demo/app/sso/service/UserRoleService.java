package demo.app.sso.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.app.core.service.CrudService;
import demo.app.sso.domain.UserRole;

public interface UserRoleService extends CrudService<UserRole, Integer> {

    Page<UserRole> findByName(String name, Pageable page);
}

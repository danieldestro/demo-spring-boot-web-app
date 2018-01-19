package demo.app.sso.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.app.core.service.CrudService;
import demo.app.sso.domain.Permission;

public interface PermissionService extends CrudService<Permission, Integer> {

    Page<Permission> findByName(String name, Pageable page);
}

package demo.app.manager.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.app.core.service.CrudService;
import demo.app.manager.domain.Application;

public interface ApplicationService extends CrudService<Application, Integer> {

    List<Application> findByName(String name);

    Page<Application> findByName(String name, Pageable page);
}

package demo.app.demo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.app.core.service.CrudService;
import demo.app.demo.domain.Demo;

public interface DemoService extends CrudService<Demo, Integer> {

    Page<Demo> findByName(String name, Pageable page);

}

package demo.app.manager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.app.core.service.CrudServiceImpl;
import demo.app.manager.data.ApplicationRepository;
import demo.app.manager.domain.Application;

@Service
public class ApplicationServiceImpl extends CrudServiceImpl<Application, Integer> implements ApplicationService {

    @Autowired
    private ApplicationRepository repository;

    @Override
    public List<Application> findByName(String name) {
        return repository.findByNameContaining(name);
    }

    @Override
    public Page<Application> findByName(String name, Pageable page) {
        return repository.findByNameContaining(name, page);
    }
}

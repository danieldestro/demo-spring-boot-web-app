package demo.app.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.app.core.service.CrudServiceImpl;
import demo.app.demo.data.DemoRepository;
import demo.app.demo.domain.Demo;

@Service
public class DemoServiceImpl extends CrudServiceImpl<Demo, Integer> implements DemoService {

    @Autowired
    private DemoRepository repository;

    @Override
    public Page<Demo> findByName(String name, Pageable page) {
        return repository.findByNameContaining(name, page);
    }
}

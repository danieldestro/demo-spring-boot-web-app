package demo.app.web.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.app.core.service.SearchFilter;
import demo.app.manager.domain.Application;
import demo.app.manager.service.ApplicationService;
import demo.app.web.mapper.ModelMapper;
import demo.app.web.vo.ApplicationVO;

@Service
public class ApplicationFacadeImpl implements ApplicationFacade {

    @Autowired
    private ApplicationService                      service;

    @Autowired
    private ModelMapper<Application, ApplicationVO> mapper;

    @Override
    public Page<ApplicationVO> list(SearchFilter filter, Pageable pageable) {

        Page<Application> page = null;
        if (filter == null || filter.isEmpty()) {
            page = service.findAll(pageable);
        } else {
            page = service.findByName(filter.getFilter(), pageable);
        }

        return mapper.mapTo(page, pageable, ApplicationVO.class);
    }

    @Override
    public ApplicationVO find(Integer id) {

        Application record = service.findById(id);
        return mapper.mapTo(record, ApplicationVO.class);
    }

    @Override
    public void delete(Integer id) {

        service.delete(id);
    }

    @Override
    public ApplicationVO create(ApplicationVO vo) {

        Application entity = mapper.mapFrom(vo, Application.class);
        entity = service.create(entity);
        return mapper.mapTo(entity, ApplicationVO.class);
    }

    @Override
    public ApplicationVO update(ApplicationVO vo) {

        Application entity = mapper.mapFrom(vo, Application.class);
        entity = service.update(entity);
        return mapper.mapTo(entity, ApplicationVO.class);
    }
}

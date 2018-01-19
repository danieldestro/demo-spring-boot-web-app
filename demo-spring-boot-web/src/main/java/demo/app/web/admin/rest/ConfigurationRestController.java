package demo.app.web.admin.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.app.core.data.BusinessException;
import demo.app.core.service.SearchFilter;
import demo.app.core.util.MessageUtils;
import demo.app.core.util.StringUtils;
import demo.app.manager.domain.GlobalConfiguration;
import demo.app.manager.service.GlobalConfigurationService;

@RestController
@RequestMapping(value = "/api/admin/configurations", produces = MediaType.APPLICATION_JSON_VALUE)
public class ConfigurationRestController {

    @Autowired
    private GlobalConfigurationService service;

    @Autowired
    private MessageUtils               messageUtils;

    @RequestMapping(value = "/contexts", method = RequestMethod.GET)
    public ResponseEntity<List<String>> listContext() {
        return new ResponseEntity<>(service.listContexts(), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    public Page<GlobalConfiguration> list(SearchFilter filter, String context, @PageableDefault Pageable page) {
        if (filter == null || filter.isEmpty()) {
            if (StringUtils.isEmpty(context)) {
                return service.findAll(page);
            } else {
                return service.findByContext(context, page);
            }
        } else {
            if (StringUtils.isEmpty(context)) {
                return service.findByName(filter.getFilter(), page);
            } else {
                return service.findByNameAndContext(filter.getFilter(), context, page);
            }
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public GlobalConfiguration find(@PathVariable Integer id) {
        return service.findById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public GlobalConfiguration create(@RequestBody GlobalConfiguration entity) {
        validate(entity);
        return service.create(entity);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public GlobalConfiguration update(@RequestBody GlobalConfiguration entity) {
        validate(entity);
        return service.update(entity);
    }

    private void validate(GlobalConfiguration entity) {
        if (!StringUtils.validLength(entity.getName(), 2, 100)) {
            String msg = messageUtils.getMessage("err.field.min.max.length", "Name", 2, 100);
            throw new BusinessException(msg);
        }
    }
}

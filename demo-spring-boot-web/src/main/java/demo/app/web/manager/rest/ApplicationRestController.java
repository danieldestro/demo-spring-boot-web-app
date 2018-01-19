package demo.app.web.manager.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import demo.app.core.data.BusinessException;
import demo.app.core.service.SearchFilter;
import demo.app.core.util.MessageUtils;
import demo.app.core.util.StringUtils;
import demo.app.web.facade.ApplicationFacade;
import demo.app.web.vo.ApplicationVO;

@RestController
@RequestMapping(value = "/api/manager/applications", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApplicationRestController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationRestController.class);

    @Autowired
    private ApplicationFacade   facade;

    @Autowired
    private MessageUtils        messageUtils;

    @GetMapping
    public Page<ApplicationVO> list(SearchFilter filter, @PageableDefault Pageable page) {

        LOG.debug("list page: {}", page.getPageNumber());
        return facade.list(filter, page);
    }

    @GetMapping("/{id}")
    public ApplicationVO find(@PathVariable Integer id) {

        LOG.debug("find id: {}", id);
        return facade.find(id);
    }

    @ResponseBody
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {

        LOG.debug("delete id: {}", id);
        facade.delete(id);
    }

    @PostMapping
    public ApplicationVO create(@RequestBody ApplicationVO vo) {

        LOG.debug("create: {}", vo.getName());
        validate(vo);
        return facade.create(vo);
    }

    @PutMapping
    public ApplicationVO update(@RequestBody ApplicationVO vo) {

        LOG.debug("update: [{}] {}", vo.getId(), vo.getName());
        validate(vo);
        return facade.update(vo);
    }

    private void validate(ApplicationVO vo) {

        if (!StringUtils.validLength(vo.getName(), 5, 30)) {
            String msg = messageUtils.getMessage("err.field.min.max.length", "Name", 5, 30);
            throw new BusinessException(msg);
        }

        if (!StringUtils.validLength(vo.getDescription(), 3)) {
            String msg = messageUtils.getMessage("err.desc.too.short");
            throw new BusinessException(msg);
        }
    }
}

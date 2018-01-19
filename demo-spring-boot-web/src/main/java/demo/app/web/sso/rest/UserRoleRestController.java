package demo.app.web.sso.rest;

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
import org.springframework.web.bind.annotation.RestController;

import demo.app.core.data.BusinessException;
import demo.app.core.service.SearchFilter;
import demo.app.core.util.MessageUtils;
import demo.app.web.facade.UserRoleFacade;
import demo.app.web.vo.UserRoleVO;

@RestController
@RequestMapping(value = "/api/security/roles", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRoleRestController {

    @Autowired
    private UserRoleFacade facade;

    @Autowired
    private MessageUtils   messageUtils;

    @GetMapping
    public Page<UserRoleVO> list(SearchFilter filter, @PageableDefault Pageable page) {
        return facade.list(filter, page);
    }

    @GetMapping("/{id}")
    public UserRoleVO find(@PathVariable Integer id) {
        return facade.find(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        facade.delete(id);
    }

    @PostMapping
    public UserRoleVO create(@RequestBody UserRoleVO vo) {
        validate(vo);
        return facade.create(vo);
    }

    @PutMapping
    public UserRoleVO update(@RequestBody UserRoleVO vo) {
        validate(vo);
        return facade.update(vo);
    }

    private void validate(UserRoleVO vo) {
        if (vo.getDescription() != null && vo.getDescription().length() <= 2) {
            String msg = messageUtils.getMessage("err.desc.too.short");
            throw new BusinessException(msg);
        }
    }

}

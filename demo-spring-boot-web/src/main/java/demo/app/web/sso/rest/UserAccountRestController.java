package demo.app.web.sso.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import demo.app.core.service.SearchFilter;
import demo.app.core.util.MessageUtils;
import demo.app.web.exception.EntityNotFoundException;
import demo.app.web.facade.UserAccountFacade;
import demo.app.web.vo.UserAccountVO;

@RestController
@RequestMapping(value = "/api/security/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAccountRestController {

    @Autowired
    private UserAccountFacade facade;

    @Autowired
    private MessageUtils      messageUtils;

    @GetMapping
    public Page<UserAccountVO> list(SearchFilter filter, @PageableDefault Pageable page) {
        return facade.list(filter, page);
    }

    @RequestMapping(value = "/employeeNumber/{email:.+}", method = RequestMethod.GET)
    public ResponseEntity<String> findEmployeeNumber(@PathVariable String email) {
        String employeeNumber = facade.findEmployeeNumber(email);
        if (employeeNumber == null) {
            throw new EntityNotFoundException(messageUtils.getMessage("err.user.employeeNumber.notFound", email));
        }
        return new ResponseEntity<>(employeeNumber, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public UserAccountVO find(@PathVariable Integer id) {
        return facade.find(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id) {
        facade.delete(id);
    }

    @PostMapping
    public UserAccountVO create(@RequestBody UserAccountVO vo) {
        return facade.create(vo);
    }

    @PutMapping
    public UserAccountVO update(@RequestBody UserAccountVO vo) {
        return facade.update(vo);
    }

}

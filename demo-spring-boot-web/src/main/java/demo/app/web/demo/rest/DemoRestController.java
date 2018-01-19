package demo.app.web.demo.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import demo.app.core.service.SearchFilter;
import demo.app.core.util.ThreadUtils;
import demo.app.demo.domain.Demo;
import demo.app.demo.service.DemoService;
import demo.app.sso.service.UserAccountService;

@RestController
@RequestMapping(value = "/api/demo/security", produces = MediaType.APPLICATION_JSON_VALUE)
public class DemoRestController {

    @Autowired
    private DemoService        service;

    @Autowired
    private UserAccountService userAccountService;

    @GetMapping
    @PreAuthorize("hasAuthority('CAC_VIEW_DEMO_PAGE')")
    public Page<Demo> list(SearchFilter filter, @PageableDefault Pageable page) {

        ThreadUtils.sleep(500); // just to pretend this action take some time to run

        if (filter == null || filter.isEmpty()) {
            return service.findAll(page);
        } else {
            return service.findByName(filter.getFilter(), page);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('CAC_VIEW_DEMO_PAGE')")
    public Demo find(@PathVariable Integer id) {

        return service.findById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Integer id) {

        // you can still check manually for roles
        boolean hasAuthority = userAccountService.userHasAuthority("CAC_EDIT_DEMO_PAGE");
        if (hasAuthority) {
            // just for demonstration purposes...
        }

        service.delete(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('CAC_EDIT_DEMO_PAGE')")
    public Demo create(@RequestBody Demo entity) {

        ThreadUtils.sleep(500); // just to pretend this action take some time to run

        // you can still check manually for roles
        boolean isAdmin = userAccountService.userHasRole("ADMIN");
        if (isAdmin) {
            // just for demonstration purposes...
        }

        return service.create(entity);
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN') and hasAuthority('CAC_EDIT_DEMO_PAGE')")
    public Demo update(@RequestBody Demo entity) {
        return service.update(entity);
    }
}

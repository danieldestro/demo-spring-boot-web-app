package demo.app.web.facade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import demo.app.core.service.SearchFilter;
import demo.app.web.vo.ApplicationVO;

public interface ApplicationFacade {

    Page<ApplicationVO> list(SearchFilter filter, Pageable page);

    ApplicationVO find(Integer id);

    void delete(Integer id);

    ApplicationVO create(ApplicationVO vo);

    ApplicationVO update(ApplicationVO vo);
}

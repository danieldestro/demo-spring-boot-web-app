package demo.app.web.mapper;

import org.springframework.stereotype.Component;

import demo.app.manager.domain.Application;
import demo.app.web.vo.ApplicationVO;

/**
 * Mapper to convert <code>Application</code> to/from <code>ApplicationVO</code> and vice versa.
 */
@Component
public class ApplicationMapper extends BaseMapper<Application, ApplicationVO> {

    @Override
    protected void configureMappings() {
        defaultMappingConfiguration(Application.class, ApplicationVO.class);
    }
}

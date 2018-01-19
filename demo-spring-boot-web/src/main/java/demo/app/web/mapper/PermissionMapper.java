package demo.app.web.mapper;

import org.springframework.stereotype.Component;

import demo.app.sso.domain.Permission;
import demo.app.web.vo.PermissionVO;

/**
 * Mapper to convert <code>Permission</code> to/from <code>PermissionVO</code>.
 */
@Component
public class PermissionMapper extends BaseMapper<Permission, PermissionVO> {

    @Override
    protected void configureMappings() {
        getMapperFactory()
                .classMap(Permission.class, PermissionVO.class)
                .byDefault().register();
    }
}

package demo.app.web.mapper;

import org.springframework.stereotype.Component;

import demo.app.sso.domain.EntityAudit;
import demo.app.web.vo.EntityAuditVO;

/**
 * Mapper to convert <code>EntityAudit</code> to/from <code>EntityAuditVO</code>.
 */
@Component
public class EntityAuditMapper extends BaseMapper<EntityAudit, EntityAuditVO> {

    @Override
    protected void configureMappings() {
        getMapperFactory()
                .classMap(EntityAudit.class, EntityAuditVO.class)
                .byDefault().register();
    }
}

package com.qatang.admin.web.validator.resource;

import com.qatang.admin.entity.resource.Resource;
import com.qatang.admin.service.resource.ResourceService;
import com.qatang.admin.web.form.resource.ResourceForm;
import com.qatang.core.exception.ValidateFailedException;
import com.qatang.core.validator.AbstractValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author qatang
 * @since 2015-01-27 11:07
 */
@Component
public class CreateResourceValidator extends AbstractValidator<ResourceForm> {
    @Autowired
    private ResourceService resourceService;

    @Override
    public boolean validate(ResourceForm resourceForm) throws ValidateFailedException {
        if (StringUtils.isEmpty(resourceForm.getResource().getName())) {
            String msg = String.format("资源名称不能为空");
            logger.error(msg);
            throw new ValidateFailedException("resource.name", msg);
        }

//        if (StringUtils.isEmpty(resourceForm.getResource().getIdentifier())) {
//            String msg = String.format("标识符不能为空");
//            logger.error(msg);
//            throw new ValidateFailedException("resource.identifier", msg);
//        }

        if (!StringUtils.isEmpty(resourceForm.getResource().getIdentifier())) {
            Resource conResource = resourceService.findByIdentifier(resourceForm.getResource().getIdentifier());
            if (conResource != null) {
                String msg = String.format("标识符已被使用");
                logger.error(msg);
                throw new ValidateFailedException("resource.identifier", msg);
            }
        }

        if (resourceForm.getResource().getType() == null) {
            String msg = String.format("资源类型不能为空");
            logger.error(msg);
            throw new ValidateFailedException("resource.type", msg);
        }

        if (resourceForm.getResource().getValid() == null) {
            String msg = String.format("是否有效必须选");
            logger.error(msg);
            throw new ValidateFailedException("resource.valid", msg);
        }
        return true;
    }
}

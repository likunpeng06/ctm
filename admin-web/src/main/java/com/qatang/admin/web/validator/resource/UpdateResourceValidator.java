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
 * @since 2015-01-27 10:02
 */
@Component
public class UpdateResourceValidator extends AbstractValidator<ResourceForm> {
    @Autowired
    private ResourceService resourceService;

    @Override
    public boolean validate(ResourceForm resourceForm) throws ValidateFailedException {
        logger.info("开始验证resourceForm参数");
        if (resourceForm == null || resourceForm.getResource() == null) {
            String msg = String.format("resourceForm参数不能为空");
            logger.error(msg);
            throw new ValidateFailedException("resourceForm", msg);
        }
        if (resourceForm.getResource().getId() == null) {
            String msg = String.format("更新资源，资源id为空");
            logger.error(msg);
            throw new ValidateFailedException("resource.id", msg);
        }

        Resource resource = resourceService.get(resourceForm.getResource().getId());
        if (resource == null) {
            String msg = String.format("所要更新的资源不存在");
            logger.error(msg);
            throw new ValidateFailedException("resource.id", msg);
        }

        if (StringUtils.isEmpty(resourceForm.getResource().getName())) {
            String msg = String.format("资源名称不能为空");
            logger.error(msg);
            throw new ValidateFailedException("resource.name", msg);
        }

        if (StringUtils.isEmpty(resourceForm.getResource().getIdentifier())) {
            String msg = String.format("资源标识符不能为空");
            logger.error(msg);
            throw new ValidateFailedException("resource.identifier", msg);
        }

        Resource tmpResource = resourceService.findByIdentifier(resourceForm.getResource().getIdentifier());
        if (tmpResource != null && !resource.getIdentifier().equals(resourceForm.getResource().getIdentifier())) {
            String msg = String.format("该标识符已被使用");
            logger.error(msg);
            throw new ValidateFailedException("resource.identifier", msg);
        }

        if (resourceForm.getResource().getValid() == null) {
            String msg = String.format("是否有效不能为空");
            logger.error(msg);
            throw new ValidateFailedException("resource.valid", msg);
        }

        if (resourceForm.getResource().getType() == null) {
            String msg = String.format("资源类型不能为空");
            logger.error(msg);
            throw new ValidateFailedException("resource.type", msg);
        }

        return true;
    }
}

package com.qatang.admin.web.form.resource;

import com.qatang.admin.entity.resource.Resource;
import com.qatang.core.form.AbstractForm;

import javax.validation.Valid;

/**
 * @author qatang
 * @since 2014-12-24 16:01
 */
public class ResourceForm extends AbstractForm {
    private static final long serialVersionUID = 2360377247790869119L;

    @Valid
    private Resource resource;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }
}

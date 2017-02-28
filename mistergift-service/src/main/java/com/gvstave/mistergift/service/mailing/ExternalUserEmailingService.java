package com.gvstave.mistergift.service.mailing;

import org.apache.velocity.Template;
import org.springframework.mail.javamail.MimeMessageHelper;

import java.util.Map;

public class ExternalUserEmailingService extends AbstractEmailingService {

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTemplateName() {
        return "external-user-invitation";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepare(Template template, MimeMessageHelper message, Map<String, Object> data) throws Exception {
        message.setSubject(translator.translate("external-user.mail.subject"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean control(Map<String, Object> data) {
        return true;
    }

}

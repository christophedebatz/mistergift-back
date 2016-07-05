package com.gvstave.mistergift.service.mailing;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Map;

/**
 *
 */
@Service
public class LandingUserEmailingService extends AbstractEmailingService {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepare(MimeMessageHelper message, Map<String, Object> data) throws Exception {
        Template template = getDefaultEmailingFactory().getTemplater().getTemplate(getTemplatePath());
        Context context = new VelocityContext(data);

        StringWriter sw = new StringWriter();
        template.merge(context, sw);

        message.setText(sw.toString(), true);


    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean control(Map<String, Object> data) {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getTemplatePath() {
        return "/WEB-INF/templates/landing-email-en.vm";
    }

}

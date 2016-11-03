package com.gvstave.mistergift.service.mailing;

import com.gvstave.mistergift.service.i18n.Translator;
import org.apache.velocity.Template;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

/**
 *
 */
@Service
public class LandingUserEmailingService extends AbstractEmailingService {

    /** The message source. */
    @Inject
    private Translator translator;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepare(Template template, MimeMessageHelper message, Map<String, Object> data) throws Exception {
        message.setSubject(translator.translate("landing.mail.subject"));
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
    public String getTemplateName() {
        return "landing-email";
    }

}

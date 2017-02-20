package com.gvstave.mistergift.service.mailing;

import com.gvstave.mistergift.service.i18n.Translator;
import org.apache.velocity.Template;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Map;

/**
 * Manages the mail that is sent to the user when he ask for new password.
 */
@Service
public class UserPasswordEmailService extends AbstractEmailingService {

    /** The message source. */
    @Inject
    private Translator translator;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void prepare(Template template, MimeMessageHelper message, Map<String, Object> data) throws Exception {
        message.setSubject(translator.translate("user.password.mail.subject"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean control(Map<String, Object> data) {
        return data.keySet().containsAll(Arrays.asList("user.name", "user.password.token"));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTemplateName() {
        return "user-password-email";
    }

}

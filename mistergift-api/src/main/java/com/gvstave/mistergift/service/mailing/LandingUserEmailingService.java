package com.gvstave.mistergift.service.mailing;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import javax.mail.MessagingException;
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
    protected void prepare(MimeMessageHelper message, Map<String, Object> data) throws MessagingException {
        String text = VelocityEngineUtils.mergeTemplateIntoString(getDefaultEmailingFactory().getTemplater(), "utf-8", getTemplatePath(), data);
        message.setText(text, true);
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
        return "com/gvstave/mistergift/resource/bisou.vm";
    }

}

package com.gvstave.mistergift.service.mailing;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.StringWriter;
import java.util.Map;
import java.util.Objects;

/**
 *
 */
@Service
abstract class AbstractEmailingService implements Mailable {

    /** The emailing factory. */
    @Inject
    private DefaultEmailingFactory defaultEmailingFactory;

    /** The env. */
    @Inject
    private Environment environment;

    /** The list of recepients. */
    private String[] recipients;

    /** The expeditor. */
    private String expeditor;

    /**
     * Send an email to the given recipient.
     *
     * @param recipient The recipient email.
     * @param model The model.
     */
    public void send(String recipient, Map<String, Object> model) {
        Objects.requireNonNull(recipient);
        send(environment.getProperty("mail.from"), new String[] { recipient }, model);
    }

    /**
     * Send the message.
     *
     * @param data The variables the message need.
     */
    public void send(String expeditor, String[] recipients, Map<String, Object> data) {
        Objects.requireNonNull(expeditor);
        Objects.requireNonNull(recipients);

        if (!control(data)) {
            throw new RuntimeException("Invalid mailing property.");
        }

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(recipients);
            message.setFrom(expeditor);

            String locale = LocaleContextHolder.getLocale().getDisplayLanguage().toLowerCase();
            String templatePath = String.format("%s/%s-%s.vm", getTemplateDirectory(), getTemplateName(), locale);
            Template template = getDefaultEmailingFactory().getTemplater().getTemplate(templatePath);

            if (data != null && !data.isEmpty()) {
                Context context = new VelocityContext(data);
                StringWriter sw = new StringWriter();
                template.merge(context, sw);
                message.setText(sw.toString(), true);
            }

            AbstractEmailingService.this.prepare(template, message, data);
        };

        defaultEmailingFactory.getMailer().send(preparator);
    }

    /**
     * Returns the base directory that contains templates.
     *
     * @return The template directory.
     */
    protected final String getTemplateDirectory() {
        return "/WEB-INF/templates";
    }

    /**
     * Returns the template name.
     *
     * @return The template name.
     */
    protected abstract String getTemplateName();

    /**
     * Returns tje default emailing factory.
     *
     * @return The default emailing factory.
     */
    protected DefaultEmailingFactory getDefaultEmailingFactory() {
        return defaultEmailingFactory;
    }

    /**
     * Prepares the message before sending it.
     *
     * @param message The message.
     * @param data The data.
     */
    protected abstract void prepare(Template template, MimeMessageHelper message, final Map<String, Object> data) throws Exception;

    /**
     * Controls the integrity of given data.
     *
     * @param data The data.
     * @return If all needed variables are present.
     */
    protected abstract boolean control(final Map<String, Object> data);

}

package com.gvstave.mistergift.service.mailing;

import com.gvstave.mistergift.service.i18n.Translator;
import com.gvstave.mistergift.service.mailing.exception.MailException;
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
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 *
 */
@Service
abstract class AbstractEmailingService implements Mailable {

    /**
     * The emailing factory.
     */
    @Inject
    private DefaultEmailingFactory defaultEmailingFactory;

    /**
     * The env.
     */
    @Inject
    private Environment environment;

    /**
     * The message source.
     */
    @Inject
    protected Translator translator;

    /**
     * The list of recepients.
     */
    private String[] recipients;

    /**
     * The expeditor.
     */
    private String expeditor;

    /**
     * Send an email to the given recipient.
     *
     * @param recipient The recipient email.
     * @param model     The model.
     */
    public void send(String recipient, Map<String, Object> model) throws MailException {
        Objects.requireNonNull(recipient);
        Locale locale = Optional.ofNullable(LocaleContextHolder.getLocale()).orElse(Locale.ENGLISH);
        send(environment.getProperty("mail.from"), new String[]{recipient}, model, locale);
    }

    /**
     * Send an email to the given recipient and with given locale.
     *
     * @param recipient The recipient email.
     * @param model     The model.
     * @param locale    The locale.
     */
    public void send(String recipient, Map<String, Object> model, Locale locale) throws MailException {
        Objects.requireNonNull(recipient);
        Objects.requireNonNull(locale);
        send(environment.getProperty("mail.from"), new String[]{recipient}, model, locale);
    }

    /**
     * Sends the mail.
     *
     * @param expeditor  The expeditor.
     * @param recipients The recipients.
     * @param data       The data.
     * @param locale     The locale.
     */
    public void send(String expeditor, String[] recipients, Map<String, Object> data, Locale locale)
            throws MailException {
        Objects.requireNonNull(expeditor);
        Objects.requireNonNull(recipients);
        Objects.requireNonNull(locale);

        if (!control(data)) {
            throw new IllegalArgumentException("Invalid mailing property.");
        }

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(recipients);
            message.setFrom(expeditor);

            String templatePath = constructTemplatePath(locale);
            if (getClass().getClassLoader().getResource(templatePath) == null) {
                templatePath = constructTemplatePath(null); // english by default
            }
            Template template = getDefaultEmailingFactory().getTemplater().getTemplate(templatePath);

            if (data != null && !data.isEmpty()) {
                Context context = new VelocityContext(data);
                StringWriter sw = new StringWriter();
                template.merge(context, sw);
                message.setText(sw.toString(), true);
            }

            AbstractEmailingService.this.prepare(template, message, data);
        };

        try {
            defaultEmailingFactory.getMailer().send(preparator);
        } catch (RuntimeException e) {
            throw new MailException("Unable to send email", e);
        }
    }

    /**
     * Returns the template path.
     * If locale is null, english will be provied instead.
     *
     * @param locale The locale.
     * @return The template path.
     */
    private String constructTemplatePath(Locale locale) {
        if (locale == null) {
            locale = Locale.ENGLISH;
        }
        return String.format("%s/%s-%s.vm", getTemplateDirectory(), getTemplateName(), locale.getLanguage());
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
    private DefaultEmailingFactory getDefaultEmailingFactory() {
        return defaultEmailingFactory;
    }

    /**
     * Prepares the message before sending it.
     *
     * @param message The message.
     * @param data    The data.
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

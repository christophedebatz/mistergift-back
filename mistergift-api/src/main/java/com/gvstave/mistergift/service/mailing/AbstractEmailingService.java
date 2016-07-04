package com.gvstave.mistergift.service.mailing;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.mail.MessagingException;
import java.util.Map;
import java.util.Objects;

@Component
abstract class AbstractEmailingService implements Mailable {

    /** The emailing factory. */
    @Inject
    private DefaultEmailingFactory defaultEmailingFactory;

    /** The list of recepients. */
    private String[] recipients;

    /** The expeditor. */
    private String expeditor;

    /**
     *
     * @return
     */
    protected DefaultEmailingFactory getDefaultEmailingFactory() {
        return defaultEmailingFactory;
    }

    /**
     *
     * @return
     */
    protected abstract String getTemplatePath();

    /**
     * Send the message.
     *
     * @param data The variables the message need.
     */
    public void send(String expeditor, final String[] recipients, final Map<String, Object> data) {
        Objects.requireNonNull(expeditor);
        Objects.requireNonNull(recipients);

        if (!control(data)) {
            throw new RuntimeException("Invalid mailing property");
        }

        MimeMessagePreparator preparator = mimeMessage -> {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage);
            message.setTo(recipients);
            message.setFrom(expeditor);

            AbstractEmailingService.this.prepare(message, data);
        };

        defaultEmailingFactory.getMailer().send(preparator);
    }

    /**
     * Prepares the message before sending it.
     *
     * @param message The message.
     * @param data The data.
     */
    protected abstract void prepare(MimeMessageHelper message, final Map<String, Object> data) throws MessagingException;

    /**
     * Controls the integrity of given data.
     *
     * @param data The data.
     * @return If all needed variables are present.
     */
    protected abstract boolean control(final Map<String, Object> data);

}

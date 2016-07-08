package com.gvstave.mistergift.service.mailing;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 *
 */
@Service
public class DefaultEmailingFactory {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(DefaultEmailingFactory.class);

    /** The environment. */
    @Inject
    private Environment environment;

    /** The Java mail service. */
    private JavaMailSenderImpl mailer;

    /** The Velocity engine. */
    private VelocityEngine templater;

    /**
     *
     * @return
     */
    public JavaMailSender getMailer() {
        if (mailer == null) {
            mailer = new JavaMailSenderImpl();

            Properties props = System.getProperties();
            props.setProperty("mail.smtp.port", environment.getProperty("mail.port"));
            props.setProperty("mail.smtp.socketFactory.port", environment.getProperty("mail.port"));
            props.setProperty("mail.smtp.host", environment.getProperty("host"));
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.auth", "true");

            Authenticator auth = new Authenticator() {

                /**
                 * {@inheritDoc}
                 */
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(
                        environment.getProperty("mail.username"),
                        environment.getProperty("mail.password")
                    );
                }

            };
            Session smtpSession = Session.getInstance(props, auth);
            smtpSession.setDebug(false);

            mailer.setDefaultEncoding(environment.getProperty("mail.encoding", "utf-8"));
            mailer.setSession(smtpSession);
        }

        return mailer;
    }

    /**
     *
     * @return
     */
    public VelocityEngine getTemplater() {
        if (templater == null) {
            templater = new VelocityEngine();

            try {
                templater.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
                templater.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
                templater.init();

            } catch (Exception ex) {
                LOGGER.error("Unable to instanciate Velocity templace engine.", ex);
            }
        }

        return templater;
    }

}

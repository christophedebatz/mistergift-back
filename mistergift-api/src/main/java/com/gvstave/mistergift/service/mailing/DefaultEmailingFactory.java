package com.gvstave.mistergift.service.mailing;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 *
 */
@Component
public class DefaultEmailingFactory {

    /** The logger. */
    private static Logger LOGGER = LoggerFactory.getLogger(DefaultEmailingFactory.class);

    /** The Java mail service. */
    private JavaMailSender mailer;

    /** The Velocity engine. */
    private VelocityEngine templater;

    /**
     *
     * @return
     */
    public JavaMailSender getMailer() {
        if (mailer == null) {
            mailer = new JavaMailSenderImpl();
        }

        return mailer;
    }

    /**
     *
     * @return
     */
    public VelocityEngine getTemplater() {
        if (templater == null) {
            try {

                Properties props = new Properties();
                props.put("resource.loader", "class");
                props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

                templater = new VelocityEngine(props);
            } catch (Exception ex) {
                LOGGER.error("Unable to instanciate Velocity templace engine.", ex);
            }
        }

        return templater;
    }

}

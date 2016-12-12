package com.gvstave.mistergift.api.controller.facebook;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * .
 */
@Controller
@RequestMapping(path = "/facebook/callback")
public class CallbackController {

    /**
     *
     * @param request
     */
    @RequestMapping(path = "")
    public void callback(HttpServletRequest request) {
        Optional.ofNullable(request.getHeader("code"))
            .ifPresent(code -> {

            });
    }

}

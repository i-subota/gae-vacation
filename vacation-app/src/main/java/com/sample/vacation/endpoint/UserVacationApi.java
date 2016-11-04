package com.sample.vacation.endpoint;

import com.google.api.server.spi.auth.GoogleOAuth2Authenticator;
import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.config.AuthLevel;
import com.google.api.server.spi.config.Named;
import com.google.appengine.api.oauth.OAuthRequestException;
import com.sample.vacation.entity.User;
import com.sample.vacation.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 * Created by Iryna Subota on 01/11/2016.
 */
@Api(name = "users",
        version = "v1",
        clientIds = {Constants.WEB_CLIENT_ID},
        namespace = @ApiNamespace(ownerDomain = "vacation.sample.com",
                ownerName = "vacation.sample.com",
                packagePath = "")
)
public class UserVacationApi {

    @Autowired
    private UserService userService;

    public UserVacationApi() {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }


    @ApiMethod(name = "getById",
            authenticators = {GoogleOAuth2Authenticator.class},
            audiences = {Constants.WEB_CLIENT_ID},
            authLevel = AuthLevel.REQUIRED
    )
    public User getUserById(@Named("id") String id, com.google.api.server.spi.auth.common.User user) throws OAuthRequestException {
        if (user != null) {
            return userService.getById(Long.valueOf(id));
        }
        throw  new OAuthRequestException("You have not access to this app!");
    }

}

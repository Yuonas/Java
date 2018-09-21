package com.redhat.developers.msa.ola;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.RefreshableKeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
public class OlaController {
    
    @RequestMapping(params = "cancel", method = RequestMethod.POST)
    public String cancelUpdateUser(HttpServletRequest request) {
        return "hej";
    }

    /*@Autowired
    private HolaService holaService;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/ola", produces = "text/plain")
    @ApiOperation("Returns the greeting in Portuguese")
    public String ola() {
        String hostname = System.getenv().getOrDefault("HOSTNAME", "Unknown");
        return String.format("Olá de %s", hostname);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/ola-chaining", produces = "application/json")
    @ApiOperation("Returns the greeting plus the next service in the chain")
    public List<String> sayHelloChaining() {
        List<String> greetings = new ArrayList<>();
        greetings.add(ola());
        greetings.addAll(holaService.hola());
        return greetings;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/ola-secured", produces = "text/plain")
    @ApiOperation("Returns a message that is only available for authenticated users")
    public String olaSecured(KeycloakPrincipal<RefreshableKeycloakSecurityContext> principal) {
        AccessToken token = principal.getKeycloakSecurityContext().getToken();
        return "This is a Secured resource. You are logged as " + token.getName();
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET, value = "/logout", produces = "text/plain")
    @ApiOperation("Logout")
    public String logout() throws ServletException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.logout();
        return "Logged out";
    }

    @RequestMapping(method = RequestMethod.GET, value = "/health")
    @ApiOperation("Used to verify the health of the service")
    public String health() {
        return "I'm ok";
    }*/
}

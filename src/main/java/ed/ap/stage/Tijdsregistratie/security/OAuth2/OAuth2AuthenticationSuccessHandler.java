package ed.ap.stage.Tijdsregistratie.security.OAuth2;

import ed.ap.stage.Tijdsregistratie.config.AppProperties;
import ed.ap.stage.Tijdsregistratie.entities.Employee;
import ed.ap.stage.Tijdsregistratie.entities.EmployeeType;
import ed.ap.stage.Tijdsregistratie.exceptions.BadRequestException;
import ed.ap.stage.Tijdsregistratie.exceptions.OAuth2AuthenticationProcessingException;
import ed.ap.stage.Tijdsregistratie.repositories.EmployeeRepository;
import ed.ap.stage.Tijdsregistratie.security.OAuth2.user.OAuth2UserInfo;
import ed.ap.stage.Tijdsregistratie.security.OAuth2.user.OAuth2UserInfoFactory;
import ed.ap.stage.Tijdsregistratie.security.TokenProvider;
import ed.ap.stage.Tijdsregistratie.security.UserPrincipal;
import ed.ap.stage.Tijdsregistratie.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.security.AuthProvider;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {


    @Autowired
    private EmployeeRepository employeeRepository;

    //@Autowired
    //private RoleRepository roleRepository;
    
    private TokenProvider tokenProvider;
    private AppProperties appProperties;
    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Autowired
    OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider, AppProperties appProperties,
                                       HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        this.tokenProvider = tokenProvider;
        this.appProperties = appProperties;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, "redirect_uri")
                .map(Cookie::getValue);

        if(redirectUri.isPresent() && !isAuthorizedRedirectUri(redirectUri.get())) {
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        UserPrincipal up = null;
        String azureToken = "";

        if(authentication instanceof OAuth2AuthenticationToken){
            OAuth2AuthenticationToken oAuth2Auth = (OAuth2AuthenticationToken)authentication;
            OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(oAuth2Auth.getAuthorizedClientRegistrationId(), oAuth2Auth.getPrincipal().getAttributes());
            
            // Arrays.asList(allowedStudents).contains("test")
            // allowedStudents.

            if(userInfo.userName().startsWith("s") ){
                return UriComponentsBuilder.fromUriString(targetUrl)
                    .queryParam("error", "USER_NOT_ALLOWED")
                    .build().toUriString();
                // throw new InternalAuthenticationServiceException("This user is not allowed to access this website");
            }

            Optional<Employee> userOptional = employeeRepository.findByEmail(userInfo.getEmail());
            Employee user;
            if(userOptional.isPresent()) {
                user = userOptional.get();
                user = updateExistingUser(user, userInfo);
            } else {
                user = registerNewUser(oAuth2Auth.getAuthorizedClientRegistrationId(), userInfo);
            }
            
            up = UserPrincipal.create(user);
        }

        String token = "";

        if(up != null)
        {
            token = tokenProvider.createToken(up);
        }
        else{
            token = tokenProvider.createToken(authentication);
        }

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    private Employee registerNewUser(String provider, OAuth2UserInfo oAuth2UserInfo) {
        Employee user = new Employee();
        Set<EmployeeType> roles = new HashSet<EmployeeType>();

        // TODO: Refactor later

       /* if(roleRepository.existsByRoleName(RoleName.USER)){
            roles.add(roleRepository.findByRoleName(RoleName.USER));
        }else{
            roles.add(roleRepository.save(EmployeeType.builder().roleName(RoleName.USER).build()));
        }*/


        user.setFirstName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setEmployee_type(roles);
        return employeeRepository.save(user);
    }

    private Employee updateExistingUser(Employee existingUser, OAuth2UserInfo oAuth2UserInfo) {
        existingUser.setFirstName(oAuth2UserInfo.getName());
        return employeeRepository.save(existingUser);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        URI clientRedirectUri = URI.create(uri);

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    if(authorizedURI.getHost().equalsIgnoreCase(clientRedirectUri.getHost())
                            && authorizedURI.getPort() == clientRedirectUri.getPort()) {
                        return true;
                    }
                    return false;
                });
    }
}
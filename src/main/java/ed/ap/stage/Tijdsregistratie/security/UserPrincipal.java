package ed.ap.stage.Tijdsregistratie.security;


import ed.ap.stage.Tijdsregistratie.entities.Employee;
import ed.ap.stage.Tijdsregistratie.entities.EmployeeType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.*;

public class UserPrincipal implements OAuth2User, UserDetails {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private UUID id;
    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(UUID id, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(Employee employee) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for(EmployeeType r : employee.getEmployee_type()){
            authorities.add(new SimpleGrantedAuthority("ROLE_" + r.getEmployeeRole().toString().toUpperCase()));
        }

        return new UserPrincipal(
                employee.getEmployeeId(),
                employee.getEmail(),
                employee.getPassword(),
                authorities
        );
    }

    public static UserPrincipal create(Employee employee, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(employee);
        userPrincipal.setAttributes(attributes);
        return userPrincipal;
    }

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(id);
    }
}
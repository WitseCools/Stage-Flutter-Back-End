package ed.ap.stage.Tijdsregistratie.controller.security;


import ed.ap.stage.Tijdsregistratie.entities.Employee;
import ed.ap.stage.Tijdsregistratie.entities.EmployeeType;
import ed.ap.stage.Tijdsregistratie.enums.EmployeeRole;
import ed.ap.stage.Tijdsregistratie.exceptions.BadRequestException;
import ed.ap.stage.Tijdsregistratie.payloads.ApiResponse;
import ed.ap.stage.Tijdsregistratie.payloads.AuthResponse;
import ed.ap.stage.Tijdsregistratie.payloads.LoginRequest;
import ed.ap.stage.Tijdsregistratie.payloads.SignUpRequest;
import ed.ap.stage.Tijdsregistratie.repositories.EmployeeRepository;
import ed.ap.stage.Tijdsregistratie.repositories.EmployeeTypeRepository;
import ed.ap.stage.Tijdsregistratie.security.TokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;


@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeTypeRepository employeeTypeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @PostMapping("/signup")

    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(employeeRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new BadRequestException("Email address already in use.");
        }

        // Creating user's account
        Employee user = new Employee();

        Set<EmployeeType> roles = new HashSet<EmployeeType>();
        if(employeeTypeRepository.existsByEmployeeRole(EmployeeRole.consultant)){
            roles.add(employeeTypeRepository.findByEmployeeRole(EmployeeRole.consultant));
        }else{
            roles.add(employeeTypeRepository.save(EmployeeType.builder().employeeRole(EmployeeRole.consultant).build()));
        }

        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setFunction(signUpRequest.getFunction());
        user.setActive(signUpRequest.getActive());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        user.setEmployee_type(roles);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = Employee.builder()
                .firstName(signUpRequest.getFirstName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .function(signUpRequest.getFunction())
                .lastName(signUpRequest.getLastName())
                .active(signUpRequest.getActive())
                .employee_type(roles)
                .build();

        Employee result = employeeRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/user/me")
                .buildAndExpand(result.getEmployeeId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "User registered successfully"));
    }
}
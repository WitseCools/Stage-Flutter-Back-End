package ed.ap.stage.Tijdsregistratie.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    private Boolean active;

    @NotBlank
    private String function;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;



}
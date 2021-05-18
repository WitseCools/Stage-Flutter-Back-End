package ed.ap.stage.Tijdsregistratie.controller;

import ed.ap.stage.Tijdsregistratie.dto.EmployeeDto;
import ed.ap.stage.Tijdsregistratie.annotations.Dto;
import ed.ap.stage.Tijdsregistratie.entities.Employee;
import ed.ap.stage.Tijdsregistratie.security.CurrentUser;
import ed.ap.stage.Tijdsregistratie.security.UserPrincipal;
import ed.ap.stage.Tijdsregistratie.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/me")
    @Dto(EmployeeDto.class)
    public Employee getCurrentEmployee(@CurrentUser UserPrincipal userPrincipal){
        return userService.getById(userPrincipal.getId()) ;
    }

    @GetMapping("/all")
    public List<Employee> Index(){
        return userService.getAll();
    }

}

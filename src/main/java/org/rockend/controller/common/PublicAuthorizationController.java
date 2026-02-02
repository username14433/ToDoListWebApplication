package org.rockend.controller.common;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rockend.entity.User;
import org.rockend.entity.UserRole;
import org.rockend.entity.dto.UserRegisterDto;
import org.rockend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collections;
import java.util.Set;

@Controller
public class PublicAuthorizationController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

//    private AuthenticationManager authenticationManager;
//    private SecurityContextRepository securityContextRepository;

    @Autowired
    public void setUserService(UserService userService,  PasswordEncoder passwordEncoder
//                               AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
//        this.authenticationManager = authenticationManager;
//        this.securityContextRepository = securityContextRepository;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model, @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("isAuthenticationFailed", true);
        }
        return "public/authorization/login-page";
    }

    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "public/authorization/registration-page";
    }

    @PostMapping("/registration")
    public String createUserAccount(@RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam String password,
                                    HttpServletRequest request) throws ServletException {
//        userService.save(userRegisterDto, UserRole.USER);
        String encodedPassword = passwordEncoder.encode(password);
        User user = new User(name, email, encodedPassword, UserRole.USER);

        userService.save(user);

        request.login(email, password); //Просто логиним пользователя одной функцией login()
//        forceAutoLogin(email, password, request, response);
        return "redirect:/account";
    }

    //Автоматически логиним пользователя после регистрации
    //Тут можно залезть в SecurityContext, SecurityContextRepository и HttpSession, это нужно если используется,
    // например JWT, но у нас ситуация проще, поэтому используем просто request.login()

//    private void forceAutoLogin(String email, String password, HttpServletRequest request, HttpServletResponse response) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(email, password)
//        );
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//        context.setAuthentication(authentication);
//
//        SecurityContextHolder.setContext(context);
//
//        //Сохраняем SecurityContext в сессию
//        securityContextRepository.saveContext(context, request, response);
//    }
}



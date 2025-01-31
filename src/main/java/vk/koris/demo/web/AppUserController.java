package vk.koris.demo.web;

import jakarta.validation.Valid;
import vk.koris.demo.domain.AppUser;
import vk.koris.demo.domain.AppUserRepository;
import vk.koris.demo.domain.SignupForm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;



@Controller
public class AppUserController {
    
    @Autowired
    private AppUserRepository repository; 
    
    @RequestMapping(value="/login")
    public String login() {    
        return "login";
    }
    
    @RequestMapping(value = "/signup")
    public String addStudent(Model model){
        model.addAttribute("signupform", new SignupForm());
        return "signup";
    }    
    
    @RequestMapping(value = "/saveuser", method = RequestMethod.POST)
public String save(@Valid @ModelAttribute("signupform") SignupForm signupForm, BindingResult bindingResult) {
    if (!bindingResult.hasErrors()) {
        if (signupForm.getPassword().equals(signupForm.getPasswordCheck())) { 
            String pwd = signupForm.getPassword();
            BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
            String hashPwd = bc.encode(pwd);

            AppUser newUser = new AppUser();
            newUser.setPasswordHash(hashPwd);
            newUser.setUsername(signupForm.getUsername());
            newUser.setEmail(signupForm.getEmail());
            newUser.setRole("USER"); 

            if (repository.findByUsername(signupForm.getUsername()) != null) {
                bindingResult.rejectValue("username", "err.username", "Username already exists");
                return "signup";
            } else if (repository.findByEmail(signupForm.getEmail()) != null) {
                bindingResult.rejectValue("email", "err.email", "Email already exists");
                return "signup";
            } else {
                repository.save(newUser);
            }
        } else {
            bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords do not match");
            return "signup";
        }
    } else {
        return "signup";
    }
    return "redirect:/login";
}
}

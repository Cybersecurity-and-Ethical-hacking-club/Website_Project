package esc.first.demo.Controllers;

import esc.first.demo.Model.User;
import esc.first.demo.Services.UserService;
import esc.first.demo.Services.UserServiceInt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceInt userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
//    @PostMapping("/register")
//    public User registerUser(@RequestBody User user){
//        return userService.registerUser(user);
//    }
    @GetMapping("/confirm-account")
    public String confirmEmail(@RequestParam("token") String token) {
        logger.info("Received email confirmation request with token: {}", token);
        User confirmedUser = userService.confirmEmail(token);
        if (confirmedUser != null) {
            logger.info("Email confirmed successfully for user: {}", confirmedUser.getEmail());
            return "Email confirmed successfully for user: " + confirmedUser.getEmail();
        } else {
            logger.warn("Invalid or expired token: {}", token);
            return "Invalid or expired token";
        }
    }
}

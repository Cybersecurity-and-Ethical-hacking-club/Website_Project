package esc.first.demo.Controllers;

import esc.first.demo.Model.User;
import esc.first.demo.Services.UserServiceInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private UserServiceInt userService;

    @GetMapping("/unconfirmed")
    public ResponseEntity<List<User>> getUnconfirmedUsers() {
        List<User> unconfirmedUsers = userService.getUnconfirmedUsers();
        return ResponseEntity.ok(unconfirmedUsers);
    }
    @PutMapping("/confirm-user/{userId}")
    public ResponseEntity<String> confirmUserByAdmin(@PathVariable Long userId, @RequestParam("token") String token) {
        User confirmedUser = userService.confirmAdmin(userId, token);
        if (confirmedUser != null) {
            return ResponseEntity.ok("User confirmed by admin successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user ID or token.");
        }
    }
    @GetMapping("/all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        return ResponseEntity.ok(allUsers);
    }

    @GetMapping("/confirmed-by-email")
    public ResponseEntity<List<User>> getUsersConfirmedByEmail() {
        List<User> emailConfirmedUsers = userService.getUsersConfirmedByEmail();
        return ResponseEntity.ok(emailConfirmedUsers);
    }

    @GetMapping("/confirmed-by-admin")
    public ResponseEntity<List<User>> getUsersConfirmedByAdmin() {
        List<User> adminConfirmedUsers = userService.getUsersConfirmedByAdmin();
        return ResponseEntity.ok(adminConfirmedUsers);
    }
    @DeleteMapping("/delete-user/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        boolean isDeleted = userService.deleteUser(userId);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        }
    }
    @GetMapping("/search/first-name")
    public ResponseEntity<List<User>> searchUsersByFirstName(@RequestParam("firstName") String firstName) {
        List<User> users = userService.searchUsersByFirstName(firstName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/last-name")
    public ResponseEntity<List<User>> searchUsersByLastName(@RequestParam("lastName") String lastName) {
        List<User> users = userService.searchUsersByLastName(lastName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/search/email")
    public ResponseEntity<List<User>> searchUsersByEmail(@RequestParam("email") String email) {
        List<User> users = userService.searchUsersByEmail(email);
        return ResponseEntity.ok(users);
    }


}

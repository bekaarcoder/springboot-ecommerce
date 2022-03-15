package com.ecommerce.shopmebackend.user;

import com.ecommerce.shopmebackend.exceptions.UserNotFoundException;
import com.ecommerce.shopmebackend.utils.FileUploadUtil;
import com.ecommerce.shopmecommon.entity.Role;
import com.ecommerce.shopmecommon.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> userList = userService.listAll();
        model.addAttribute("userList", userList);
        return "users";
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        List<Role> roles = userService.listRoles();
        User user = new User();
        user.setEnabled(true);
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("pageTitle", "Create New User");
        return "user_form";
    }

    @PostMapping("/users/save")
    public String saveUser(User user, RedirectAttributes redirectAttributes, @RequestParam("image") MultipartFile multipartFile) throws IOException {
        if(!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            user.setPhotos(fileName);
            User savedUser = userService.saveUser(user);
            String uploadDir = "user-photos/" + savedUser.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        } else {
            if (user.getPhotos().isEmpty()) user.setPhotos(null);
            userService.saveUser(user);
        }

        redirectAttributes.addFlashAttribute("message", "User has been saved.");
        return "redirect:/users";
    }

    @GetMapping("/users/edit/{id}")
    public String editUser(@PathVariable(name = "id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUser(id);
            List<Role> roles = userService.listRoles();

            model.addAttribute("user", user);
            model.addAttribute("roles", roles);
            model.addAttribute("pageTitle", "Edit User");
        } catch(UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("warning", "Could not find any user with the id: " + id);
            return "redirect:/users";
        }

        return "user_form";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Long id, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.delete(id);
            redirectAttributes.addFlashAttribute("message", "User with id " + id + " has been deleted successfully.");
        } catch(UserNotFoundException e) {
            redirectAttributes.addFlashAttribute("warning", "Could not find any user with the id: " + id);

        }
        return "redirect:/users";
    }

    @GetMapping("/users/{id}/enable/{status}")
    public String setUserStatus(@PathVariable(name = "id") Long id, @PathVariable(name = "status") boolean status, RedirectAttributes redirectAttributes) {
        userService.setEnabled(id, status);
        String enableStatus = status ? "enabled" : "disabled";
        String message = "User with id " + id + " has been " + enableStatus;
        redirectAttributes.addFlashAttribute("message", message);
        return "redirect:/users";
    }
}

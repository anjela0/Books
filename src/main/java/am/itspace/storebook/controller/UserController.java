package am.itspace.storebook.controller;

import am.itspace.storebook.entity.Order;
import am.itspace.storebook.entity.User;
import am.itspace.storebook.exception.DublicateResourceException;
import am.itspace.storebook.security.CurrentUser;
import am.itspace.storebook.service.OrderService;
import am.itspace.storebook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final OrderService orderService;

    @GetMapping("/user/verify")
    public String verifyUser(@RequestParam("email") String email,
                             @RequestParam("token") String token) throws Exception {
        userService.verifyUser(email, token);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user) throws DublicateResourceException, MessagingException {
        userService.save(user);
        return "redirect:/";
    }

    @GetMapping("/myOrders")
    public String myOrdersPage(@AuthenticationPrincipal CurrentUser currentUser,
                               ModelMap modelMap) {
        List<Order> myOrders = orderService.findAllByUser(currentUser.getUser());
        modelMap.addAttribute("myOrders", myOrders);
        return "myOrders";
    }

}

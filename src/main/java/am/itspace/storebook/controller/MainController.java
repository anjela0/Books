package am.itspace.storebook.controller;

import am.itspace.storebook.entity.Book;
import am.itspace.storebook.entity.User;
import am.itspace.storebook.entity.UserRole;
import am.itspace.storebook.exception.DublicateResourceException;
import am.itspace.storebook.security.CurrentUser;
import am.itspace.storebook.service.BookService;
import am.itspace.storebook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final BookService bookService;
    @GetMapping("/")
    public String mainPage(ModelMap modelMap) {
        List<Book> last20Books = bookService.findLast20Books();
        modelMap.addAttribute("books", last20Books);
        return "index";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal CurrentUser currentUser) {
        if(currentUser != null) {
            User user = currentUser.getUser();
            if(user.getUserRole() == UserRole.ADMIN) {
                return "redirect:/admin";
            } else if(user.getUserRole() == UserRole.USER) {
                return "redirect:/";
            }
        }
        return "redirect:/";
    }

}

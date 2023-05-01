package am.itspace.storebook.controller;

import am.itspace.storebook.entity.Book;
import am.itspace.storebook.entity.Order;
import am.itspace.storebook.security.CurrentUser;
import am.itspace.storebook.service.BookService;
import am.itspace.storebook.service.OrderService;
import am.itspace.storebook.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final BookService bookService;
    private final OrderService orderService;

    @GetMapping("/order/new")
    public String newOrder(@RequestParam("bookId") int bookId,
                           @AuthenticationPrincipal CurrentUser currentUser) {
        Optional<Book> byId = bookService.findById(bookId);
        if(byId.isEmpty()) {
            return "redirect:/";
        }
        Order order = Order.builder()
                .book(byId.get())
                .user(currentUser.getUser())
                .orderDateTime(new Date())
                .build();
        orderService.save(order);
        return "redirect:/myOrders";
    }

}

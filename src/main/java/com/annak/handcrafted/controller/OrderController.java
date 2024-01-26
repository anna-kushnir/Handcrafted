package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.NewOrderDto;
import com.annak.handcrafted.dto.OrderDto;
import com.annak.handcrafted.dto.ProductInCartDto;
import com.annak.handcrafted.entity.embedded.TypeOfReceipt;
import com.annak.handcrafted.entity.User;
import com.annak.handcrafted.service.OrderService;
import com.annak.handcrafted.service.ProductInCartService;
import com.annak.handcrafted.service.ProductInOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final UserDetailsService userDetailsService;
    private final ProductInCartService productInCartService;
    private final ProductInOrderService productInOrderService;

    @GetMapping
    public String getAllOrders(Principal principal, Model model) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        List<OrderDto> orderDtoList = orderService.getAllByUser(user);
        model.addAttribute("orders", orderDtoList);
        return "user/list_of_orders";
    }

    @GetMapping("/{id}")
    public String getOrderById(@PathVariable Long id, Principal principal, Model model, RedirectAttributes redirectAttributes) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Optional<OrderDto> orderDtoOptional = orderService.getById(id);
        if (orderDtoOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Order with id <%s> was not found!".formatted(id));
            return "redirect:/orders";
        }
        OrderDto orderDto = orderDtoOptional.get();
        if (!user.equals(orderDto.getUser())) {
            redirectAttributes.addFlashAttribute("message", "You can view only your own orders!");
            return "redirect:/orders";
        }
        model.addAttribute("order", orderDto);
        model.addAttribute("products", productInOrderService.getAllByOrderId(orderDto.getId()));
        return "user/order";
    }

    @GetMapping("/new")
    public String viewProductsToOrder(Principal principal, Model model) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        List<ProductInCartDto> productInCartDtoList = productInCartService.getAllByUser(user);
        model.addAttribute("productsInCart", productInCartDtoList);
        model.addAttribute("totalPrice", productInCartService.getTotalPriceOfProductsInCart(productInCartDtoList));
        return "user/create_order";
    }

    @GetMapping("/new/fillInTheOrderData")
    public String fillInTheOrderData(Principal principal, Model model) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        NewOrderDto orderDto = new NewOrderDto();
        orderDto.setUserPhone(user.getUserPhone());
        model.addAttribute("order", orderDto);
        model.addAttribute("typesOfReceipt", TypeOfReceipt.values());
        return "user/fill_order_data";
    }

    @PostMapping("/new")
    public String createOrder(Principal principal, @ModelAttribute("order") NewOrderDto newOrderDto, RedirectAttributes redirectAttributes) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        newOrderDto.setUser(user);
        String result = orderService.save(newOrderDto, productInCartService.getAllByUser(user));
        redirectAttributes.addFlashAttribute("message", result);
        return "redirect:/orders";
    }

    @DeleteMapping("/{id}/delete")
    public ResponseEntity<?> cancelOrderById(@PathVariable Long id, Principal principal) {
        var user = (User) userDetailsService.loadUserByUsername(principal.getName());
        Optional<OrderDto> orderDtoOptional = orderService.getById(id);
        if (orderDtoOptional.isPresent()) {
            OrderDto orderDto = orderDtoOptional.get();
            if (!user.equals(orderDto.getUser())) {
                return ResponseEntity.badRequest().build();
            }
            orderService.cancel(orderDto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}

package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.OrderDto;
import com.annak.handcrafted.entity.embedded.Status;
import com.annak.handcrafted.service.OrderService;
import com.annak.handcrafted.service.ProductInOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/orders")
@RequiredArgsConstructor
public class AdminOrderController {

    private final OrderService orderService;
    private final ProductInOrderService productInOrderService;

    @GetMapping
    public String getMenuWithOrderStatuses(Model model) {
        model.addAttribute("statuses", Status.values());
        return "admin/orders_menu";
    }

    @GetMapping("/{status_name}")
    public String getAllOrdersByStatus(@PathVariable String status_name, Model model) {
        model.addAttribute("status_name", status_name);
        model.addAttribute("orders", orderService.getAllByStatusName(status_name));
        return "admin/list_of_orders";
    }

    @GetMapping("/{status_name}/{id}")
    public String getAllOrdersByStatus(@PathVariable String status_name, @PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<OrderDto> orderDtoOptional = orderService.getById(id);
        if (orderDtoOptional.isPresent()) {
            if (orderDtoOptional.get().getStatus().equals(Status.valueOf(status_name.toUpperCase()))) {
                model.addAttribute("order", orderDtoOptional.get());
                model.addAttribute("products", productInOrderService.getAllByOrderId(orderDtoOptional.get().getId()));
                return "admin/order";
            }
            redirectAttributes.addFlashAttribute("message", "Order with id <%s> has another status!".formatted(id));
            return "redirect:/admin/orders/{status_name}";
        }
        redirectAttributes.addFlashAttribute("message", "Order with id <%s> was not found!".formatted(id));
        return "redirect:/admin/orders/{status_name}";
    }
}

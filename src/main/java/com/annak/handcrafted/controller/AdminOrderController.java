package com.annak.handcrafted.controller;

import com.annak.handcrafted.dto.OrderDto;
import com.annak.handcrafted.entity.embedded.Status;
import com.annak.handcrafted.service.OrderService;
import com.annak.handcrafted.service.ProductInOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
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
    public String getOrderById(@PathVariable String status_name, @PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
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

    @PutMapping("/{id}/acceptOrderWithPickup")
    public ResponseEntity<?> acceptOrderWithPickup(@PathVariable Long id, @RequestBody LocalDateTime receiptDate) {
        Optional<OrderDto> orderDtoOptional = orderService.getById(id);
        if (orderDtoOptional.isEmpty())
            return ResponseEntity.notFound().build();
        OrderDto orderDto = orderDtoOptional.get();
        if (orderDto.getStatus().getId() != 1)
            return ResponseEntity.badRequest().build();

        orderDto.setReceiptDate(receiptDate);
        orderDto.setStatus(Status.ACCEPTED);
        orderService.update(orderDto, productInOrderService.getAllDtosByOrderId(orderDto.getId()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/acceptOrderWithDelivery")
    public ResponseEntity<?> acceptOrderWithDelivery(@PathVariable Long id) {
        Optional<OrderDto> orderDtoOptional = orderService.getById(id);
        if (orderDtoOptional.isEmpty())
            return ResponseEntity.notFound().build();
        OrderDto orderDto = orderDtoOptional.get();
        if (orderDto.getStatus().getId() != 1)
            return ResponseEntity.badRequest().build();

        orderDto.setStatus(Status.ACCEPTED);
        orderService.update(orderDto, productInOrderService.getAllDtosByOrderId(orderDto.getId()));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}/declineOrder")
    public ResponseEntity<?> declineOrder(@PathVariable Long id) {
        Optional<OrderDto> orderDtoOptional = orderService.getById(id);
        if (orderDtoOptional.isEmpty())
            return ResponseEntity.notFound().build();
        OrderDto orderDto = orderDtoOptional.get();
        if (orderDto.getStatus().getId() != 1)
            return ResponseEntity.badRequest().build();

        orderService.cancel(orderDtoOptional.get());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/markOrderAsForwardedForDelivery")
    public ResponseEntity<?> markOrderAsForwardedForDelivery(@PathVariable Long id, @RequestBody Long invoiceNumber) {
        Optional<OrderDto> orderDtoOptional = orderService.getById(id);
        if (orderDtoOptional.isEmpty())
            return ResponseEntity.notFound().build();
        OrderDto orderDto = orderDtoOptional.get();
        if (orderDto.getTypeOfReceipt().getId() != 2 || orderDto.getStatus().getId() != 2)
            return ResponseEntity.badRequest().build();

        orderDto.setInvoiceNumber(invoiceNumber);
        orderDto.setStatus(Status.FORWARDED_FOR_DELIVERY);
        orderService.update(orderDto, productInOrderService.getAllDtosByOrderId(orderDto.getId()));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/markOrderAsReceived")
    public ResponseEntity<?> markOrderAsReceived(@PathVariable Long id) {
        Optional<OrderDto> orderDtoOptional = orderService.getById(id);
        if (orderDtoOptional.isEmpty())
            return ResponseEntity.notFound().build();
        OrderDto orderDto = orderDtoOptional.get();
        if ((orderDto.getTypeOfReceipt().getId() == 1 && orderDto.getStatus().getId() != 2) ||
                (orderDto.getTypeOfReceipt().getId() == 2 && orderDto.getStatus().getId() != 3))
            return ResponseEntity.badRequest().build();

        orderDto.setStatus(Status.RECEIVED);
        orderService.update(orderDto, productInOrderService.getAllDtosByOrderId(orderDto.getId()));
        return ResponseEntity.ok().build();
    }
}

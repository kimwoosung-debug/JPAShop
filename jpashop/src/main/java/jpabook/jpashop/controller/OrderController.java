package jpabook.jpashop.controller;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.service.ItemService;
import jpabook.jpashop.service.MemberService;
import jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/orders/new")
    public String newOrder(Model model) {

        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);

        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);


        return "Orders/orderForm";
    }

    @PostMapping("/orders/new")
    public String orders(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);
        return "redirect:/";
    }

    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch,
                            Model model) {
        List<Order> orders = orderService.searchOrders(orderSearch);
        model.addAttribute("orders", orders);

        return "Orders/orderList";
    }

    @PostMapping("/orders/{id}/cancel")
    public String orderCancel(@PathVariable("id") Long id) {
        orderService.cancelOrder(id);
        return "redirect:/orders";
    }

}

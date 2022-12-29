package jpabook.jpashop.Controller;

import jpabook.jpashop.Domain.*;
import jpabook.jpashop.Service.ItemService;
import jpabook.jpashop.Service.MemberService;
import jpabook.jpashop.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    @ResponseBody
    public Order order() {
        Member member = new Member();
        member.setName("조경서");
        member.setAddress(new Address("전주시", "몰라", "10006"));
        memberService.join(member);

        Book book = new Book();
        book.setName("해리포터");
        book.setPrice(30000);
        book.setStockQuantity(10);
        itemService.saveItem(book);

        return orderService.findOne(orderService.order(member.getId(), book.getId(), 10));
    }

}

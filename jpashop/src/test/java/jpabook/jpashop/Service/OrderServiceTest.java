package jpabook.jpashop.Service;

import jpabook.jpashop.Domain.*;
import jpabook.jpashop.Repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @PersistenceContext
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    ItemService itemService;

    @Test
    public void 주문_취소() {
        Member member = new Member();
        member.setName("김우성");
        Long memberId = memberService.join(member);

        Item item = new Book();
        item.setName("해리포터");
        item.setPrice(10000);
        item.setStockQuantity(50);
        Long itemId = itemService.saveItem(item);

        Long orderId = orderService.order(memberId, itemId, 1);

        assertEquals(item.getStockQuantity(), 49);

        orderService.cancelOrder(orderId);

        Order order = orderService.findOne(orderId);

        //검증
        //1. OrderStatus가 CANCEL로 변경
        assertEquals(order.getStatus(), OrderStatus.CANCEL);
        //2. 주문 취소된 상품은 그만큼 재고가 다시 증가해야 한다.
        assertEquals(item.getStockQuantity(), 50);
    }
}
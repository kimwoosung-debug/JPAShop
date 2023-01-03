package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

    private final EntityManager em;

    public void save(Order order) {
        em.persist(order);
    }

    public Order findOne(Long id) {
        return em.find(Order.class, id);
    }

    //동적 주문 조회
    public List<Order> findAll(OrderSearch orderSearch) {
        String jpql = "select o From Order o join o.member m";
        boolean isFistCondition = true;

        //둘다 있을 때
        //"select o From Order o join o.member m where o.status = :status and m.name like :name"

        //주문 상태 만 있을 때
        //"select o From Order o join o.member m where o.status = :status"

        // 회원만 있을 때
        //"select o From Order o join o.member m where m.name like :name"

        // 주문상태가 있으면 if문 블록 실행
        // 동적 쿼리 queryDSL
        if (orderSearch.getOrderStatus() != null) {
            if (isFistCondition) {
                jpql += " where";
                isFistCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " o.status = :status";
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFistCondition) {
                jpql += " where";
                isFistCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " m.name like :name";
        }

        // 회원과 주문상태를 파악해서
        // 동적 쿼리를 생성
        // 쿼리는 DB에 전달 (-> JPA)
        // JPQL 쿼리 생성 -> JPA에게 전달
        TypedQuery<Order> query = em.createQuery(jpql, Order.class).setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null) {
            query = query.setParameter("status", orderSearch.getOrderStatus());
        }
        if (StringUtils.hasText(orderSearch.getMemberName())) {
            query = query.setParameter("name", orderSearch.getMemberName());
        }
        return query.getResultList();
    }
}

package nior_near.server.domain.order.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nior_near.server.domain.letter.entity.Letter;
import nior_near.server.domain.letter.entity.LetterStatus;
import nior_near.server.domain.letter.repository.LetterRepository;
import nior_near.server.domain.order.dto.request.OrderAddRequestDto;
import nior_near.server.domain.order.dto.response.OrderAddResponseDto;
import nior_near.server.domain.order.entity.Order;
import nior_near.server.domain.order.entity.OrderMenu;
import nior_near.server.domain.order.exception.handler.OrderHandler;
import nior_near.server.domain.order.repository.OrderMenuRepository;
import nior_near.server.domain.order.repository.OrderRepository;
import nior_near.server.domain.payment.entity.Payment;
import nior_near.server.domain.payment.entity.PaymentStatus;
import nior_near.server.domain.payment.repository.PaymentRepository;
import nior_near.server.domain.store.entity.Menu;
import nior_near.server.domain.store.entity.Store;
import nior_near.server.domain.store.repository.MenuRepository;
import nior_near.server.domain.store.repository.StoreRepository;
import nior_near.server.domain.user.entity.Member;
import nior_near.server.domain.user.repository.MemberRepository;
import nior_near.server.global.common.BaseResponseDto;
import nior_near.server.global.common.ResponseCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderCommandServiceImpl implements OrderCommandService {

    private final MemberRepository memberRepository;
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final PaymentRepository paymentRepository;
    private final LetterRepository letterRepository;

    @Override
    @Transactional
    public BaseResponseDto<OrderAddResponseDto> addOrder(Long memberId, OrderAddRequestDto orderAddRequestDto) {

        Member member = memberRepository.findById(memberId).orElseThrow(() -> new OrderHandler(ResponseCode.MEMBER_NOT_FOUND));
        Store store = storeRepository.findById(orderAddRequestDto.getStoreId()).orElseThrow(() -> new OrderHandler(ResponseCode.STORE_NOT_FOUND));

        Long totalPrice = getTotalPrice(orderAddRequestDto.getMenus());

        /**
         * TODO: totalPrice 와 결제할 멤버 정보를 매개변수로 가지는 결제 로직 추가될 것
         */

        Order order = orderRepository.save(
                Order.builder()
                        .member(member)
                        .store(store)
                        .place(store.getPlace())
                        .phone(orderAddRequestDto.getMemberPhone())
                        .requestMessage(orderAddRequestDto.getRequestMessage())
                        .totalPrice(totalPrice)
                        .orderUID(UUID.randomUUID().toString())
                        .build()
        );

        List<OrderMenu> orderMenuList = getOrderMenuList(orderAddRequestDto.getMenus(), order);
        orderMenuRepository.saveAll(orderMenuList);

        // 주문 생성 후, 편지 보내기
        sendChefLetterToMember(store.getMember(), member, store.getLetter());

        // response 생성
        List<OrderAddResponseDto.OrderMenuInfo> orderMenuInfoList = getOrderMenuInfoList(orderAddRequestDto.getMenus());

        OrderAddResponseDto orderAddResponseDto = OrderAddResponseDto.builder()
                .orderId(order.getId())
                .message(store.getMessage())
                .profileImage(store.getProfileImage())
                .totalPrice(totalPrice)
                .orderMenus(orderMenuInfoList)
                .build();

        Payment payment = paymentRepository.save(
                Payment.builder()
                        .price(totalPrice)
                        .paymentStatus(PaymentStatus.READY)
                        .build()
        );

        order.update(payment);

        return BaseResponseDto.onSuccess(orderAddResponseDto, ResponseCode.OK);
    }

    private Long getTotalPrice(List<OrderAddRequestDto.OrderMenuItem> menus) {
        long totalPrice = 0;

        for (OrderAddRequestDto.OrderMenuItem orderMenuItem : menus) {
            Menu menu = menuRepository.findById(orderMenuItem.getMenuId()).orElseThrow(() -> new OrderHandler(ResponseCode.MENU_NOT_FOUND));
            totalPrice += menu.getPrice() * orderMenuItem.getQuantity();
        }

        return totalPrice;
    }

    private List<OrderMenu> getOrderMenuList(List<OrderAddRequestDto.OrderMenuItem> menus, Order order) {
        List<OrderMenu> orderMenuList = new ArrayList<>();

        for (OrderAddRequestDto.OrderMenuItem orderMenuItem : menus) {
            Menu menu = menuRepository.findById(orderMenuItem.getMenuId()).orElseThrow(() -> new OrderHandler(ResponseCode.MENU_NOT_FOUND));
            orderMenuList.add(OrderMenu.builder().menu(menu).order(order).quantity(orderMenuItem.getQuantity()).build());
        }

        return orderMenuList;
    }

    private List<OrderAddResponseDto.OrderMenuInfo> getOrderMenuInfoList(List<OrderAddRequestDto.OrderMenuItem> menus) {
        List<OrderAddResponseDto.OrderMenuInfo> orderMenuInfoList = new ArrayList<>();

        for (OrderAddRequestDto.OrderMenuItem orderMenuItem : menus) {
            Menu menu = menuRepository.findById(orderMenuItem.getMenuId()).orElseThrow(() -> new OrderHandler(ResponseCode.MENU_NOT_FOUND));
            orderMenuInfoList.add(
                    OrderAddResponseDto.OrderMenuInfo.builder()
                    .menuName(menu.getName())
                    .menuPrice(menu.getPrice())
                    .quantity(orderMenuItem.getQuantity()).build()
            );
        }

        return orderMenuInfoList;
    }

    private void sendChefLetterToMember(Member sender, Member receiver, String letterImageLink) {

        Letter letter = Letter.builder()
                .senderName(sender.getName())
                .imageLink(letterImageLink)
                .status(LetterStatus.UNREAD)
                .sender(sender)
                .receiver(receiver)
                .build();

        letterRepository.save(letter);
    }
}

package com.openwallet.service;

import com.openwallet.Category;
import com.openwallet.Emotion;
import com.openwallet.domain.Expense;
import com.openwallet.domain.Subscription;
import com.openwallet.repository.ExpenseRepository;
import com.openwallet.repository.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionScheduler {

    private final SubscriptionRepository subscriptionRepository;
    private final ExpenseRepository expenseRepository;

    @Scheduled(cron = "0 0 0 * * *") // 매일 0시
    public void processSubscriptions() {
        LocalDate today = LocalDate.now();

        List<Subscription> list = subscriptionRepository.findAll();

        for (Subscription sub : list) {

            if (sub.getNextPaymentDate().isEqual(today)) {

                Expense expense = Expense.builder()
                        .title(sub.getTitle())
                        .date(today)
                        .price(sub.getPrice())
                        .category(Category.SUBSCRIPTION)
                        .emotion(Emotion.NEUTRAL)
                        .memo(sub.getTitle() + " 정기결제")
                        .satisfaction(sub.getSatisfaction())
                        .build();

                expenseRepository.save(expense);

                //다음 결제일 갱신
                sub.setNextPaymentDate(sub.getNextPaymentDate().plusMonths(1));
                subscriptionRepository.save(sub);
            }
        }
    }
}
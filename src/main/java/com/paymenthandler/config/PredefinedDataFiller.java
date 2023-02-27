package com.paymenthandler.config;

import com.paymenthandler.model.Case;
import com.paymenthandler.model.Country;
import com.paymenthandler.model.Payment;
import com.paymenthandler.repository.CaseRepository;
import com.paymenthandler.repository.PaymentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Currency;

@Configuration
public class PredefinedDataFiller {

    @Bean
    @Transactional
    CommandLineRunner commandLineRunner(CaseRepository caseRepository, PaymentRepository paymentRepository) {

        return args -> {
            Payment payment = new Payment(1649223L, 499.99, Currency.getInstance("SEK"));
            Case case1 = new Case(payment, Country.SWEDEN);
            caseRepository.save(case1);

            Payment payment1 = new Payment(1649224L, 2000D, Currency.getInstance("DKK"));
            Case case2 = new Case(payment1, Country.DENMARK);
            caseRepository.save(case2);

        };
    }
}

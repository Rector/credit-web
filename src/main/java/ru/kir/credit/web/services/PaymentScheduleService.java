package ru.kir.credit.web.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kir.credit.web.entities.PaymentSchedule;
import ru.kir.credit.web.error_handling.InvalidDataException;
import ru.kir.credit.web.repositories.PaymentScheduleRepository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static ru.kir.credit.web.utils.CreditSettings.CREDIT_TERM_IN_MONTHS;

@Service
@RequiredArgsConstructor
public class PaymentScheduleService {
    private final PaymentScheduleRepository paymentScheduleRepository;

    @Transactional
    public List<PaymentSchedule> addNewPaymentScheduleForCreditOffer(BigDecimal creditSum, BigDecimal interestRate) {
        List<PaymentSchedule> paymentScheduleList = new ArrayList<>();
        BigDecimal remainingSumCredit = BigDecimal.valueOf(creditSum.doubleValue());
        BigDecimal interestRatePerMonth = interestRate.divide(BigDecimal.valueOf(100 * 12), 6, 1);

        BigDecimal repaymentSumCreditBody = creditSum.divide(BigDecimal.valueOf(CREDIT_TERM_IN_MONTHS), 2, 1);

        BigDecimal repaymentSumInterest;
        BigDecimal paymentSum;

        java.sql.Date paymentDate;
        Calendar calendar = Calendar.getInstance();
        PaymentSchedule paymentSchedule = null;
        BigDecimal paymentCorrector = BigDecimal.valueOf(0);

        while (repaymentSumCreditBody.doubleValue() < remainingSumCredit.doubleValue()) {
            paymentCorrector = paymentCorrector.add(repaymentSumCreditBody);

            paymentSchedule = new PaymentSchedule();

            calendar.add(Calendar.MONTH, 1);
            paymentDate = new Date(calendar.getTime().getTime());
            paymentSchedule.setPaymentDate(paymentDate);

            paymentSchedule.setRepaymentSumCreditBody(repaymentSumCreditBody);
            repaymentSumInterest = remainingSumCredit.multiply(interestRatePerMonth);
            paymentSchedule.setRepaymentSumInterest(repaymentSumInterest);

            paymentSum = repaymentSumCreditBody.add(repaymentSumInterest);
            paymentSchedule.setPaymentSum(paymentSum);

            paymentScheduleList.add(paymentScheduleRepository.save(paymentSchedule));

            remainingSumCredit = remainingSumCredit.subtract(repaymentSumCreditBody);
        }

        if (paymentCorrector.doubleValue() < creditSum.doubleValue()) {
            paymentSchedule = paymentScheduleList.get(paymentScheduleList.size() - 1);

            BigDecimal difference = creditSum.subtract(paymentCorrector);
            paymentSchedule.setRepaymentSumCreditBody(repaymentSumCreditBody.add(difference));

            paymentSchedule.setRepaymentSumInterest(paymentSchedule.getRepaymentSumCreditBody().multiply(interestRatePerMonth));

            paymentSchedule.setPaymentSum(paymentSchedule.getRepaymentSumCreditBody().add(paymentSchedule.getRepaymentSumInterest()));
            paymentScheduleList.set(paymentScheduleList.size() - 1, paymentSchedule);
        }

        return paymentScheduleList;
    }

    @Transactional
    public List<PaymentSchedule> updatePaymentScheduleForCreditOffer(List<PaymentSchedule> paymentScheduleList,
                                                                     BigDecimal creditSum,
                                                                     BigDecimal interestRate) {
        List<PaymentSchedule> finalPaymentScheduleList = paymentScheduleList;

        for (int i = 0; i < paymentScheduleList.size(); i++) {
            int finalI1 = i;
            PaymentSchedule paymentSchedule = paymentScheduleRepository.findById(paymentScheduleList.get(i).getUuid())
                    .orElseThrow(() -> new InvalidDataException(String.format("Payment schedule with uuid: '%s' not found",
                            finalPaymentScheduleList.get(finalI1).getUuid())));
            paymentScheduleRepository.delete(paymentSchedule);
        }

        paymentScheduleList = new ArrayList<>();

        BigDecimal remainingSumCredit = BigDecimal.valueOf(creditSum.doubleValue());
        BigDecimal interestRatePerMonth = interestRate.divide(BigDecimal.valueOf(100 * 12), 6, 1);

        BigDecimal repaymentSumCreditBody = creditSum.divide(BigDecimal.valueOf(CREDIT_TERM_IN_MONTHS), 2, 1);

        BigDecimal repaymentSumInterest;
        BigDecimal paymentSum;

        java.sql.Date paymentDate;
        Calendar calendar = Calendar.getInstance();
        PaymentSchedule paymentSchedule = null;
        BigDecimal paymentCorrector = BigDecimal.valueOf(0);

        while (repaymentSumCreditBody.doubleValue() < remainingSumCredit.doubleValue()) {
            paymentCorrector = paymentCorrector.add(repaymentSumCreditBody);

            paymentSchedule = new PaymentSchedule();

            calendar.add(Calendar.MONTH, 1);
            paymentDate = new Date(calendar.getTime().getTime());
            paymentSchedule.setPaymentDate(paymentDate);

            paymentSchedule.setRepaymentSumCreditBody(repaymentSumCreditBody);
            repaymentSumInterest = remainingSumCredit.multiply(interestRatePerMonth);
            paymentSchedule.setRepaymentSumInterest(repaymentSumInterest);

            paymentSum = repaymentSumCreditBody.add(repaymentSumInterest);
            paymentSchedule.setPaymentSum(paymentSum);

            paymentScheduleList.add(paymentScheduleRepository.save(paymentSchedule));

            remainingSumCredit = remainingSumCredit.subtract(repaymentSumCreditBody);
        }

        if (paymentCorrector.doubleValue() < creditSum.doubleValue()) {
            paymentSchedule = paymentScheduleList.get(paymentScheduleList.size() - 1);

            BigDecimal difference = creditSum.subtract(paymentCorrector);
            paymentSchedule.setRepaymentSumCreditBody(repaymentSumCreditBody.add(difference));

            paymentSchedule.setRepaymentSumInterest(paymentSchedule.getRepaymentSumCreditBody().multiply(interestRatePerMonth));

            paymentSchedule.setPaymentSum(paymentSchedule.getRepaymentSumCreditBody().add(paymentSchedule.getRepaymentSumInterest()));
            paymentScheduleList.set(paymentScheduleList.size() - 1, paymentSchedule);
        }

        return paymentScheduleList;
    }

}

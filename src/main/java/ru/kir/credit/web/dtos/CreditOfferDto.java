package ru.kir.credit.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import ru.kir.credit.web.entities.CreditOffer;
import ru.kir.credit.web.entities.PaymentSchedule;
import ru.kir.credit.web.utils.DateComparator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Data
public class CreditOfferDto {
    private String uuid;
    private ClientDto clientDto;
    private CreditDto creditDto;
    private BigDecimal creditSum;

    private BigDecimal totalPaymentsSum;
    private BigDecimal totalRepaymentsSumCreditBody;
    private BigDecimal totalRepaymentsSumInterest;

    private List<PaymentScheduleDto> paymentScheduleDtoList;

    public CreditOfferDto(CreditOffer creditOffer) {
        this.uuid = creditOffer.getUuid();
        this.clientDto = new ClientDto(creditOffer.getClient());
        this.creditDto = new CreditDto(creditOffer.getCredit());
        this.creditSum = creditOffer.getCreditSum();

        this.totalPaymentsSum = BigDecimal.valueOf(0);
        this.totalRepaymentsSumCreditBody = BigDecimal.valueOf(0);
        this.totalRepaymentsSumInterest = BigDecimal.valueOf(0);

        if (creditOffer.getPaymentScheduleList() != null) {
            this.paymentScheduleDtoList = new ArrayList<>();
            List<PaymentSchedule> paymentScheduleList = creditOffer.getPaymentScheduleList();

            for (int i = 0; i < paymentScheduleList.size(); i++) {
                PaymentScheduleDto paymentScheduleDto = new PaymentScheduleDto();
                paymentScheduleDto.setUuid(paymentScheduleList.get(i).getUuid());
                paymentScheduleDto.setPaymentDate(paymentScheduleList.get(i).getPaymentDate());
                paymentScheduleDto.setPaymentSum(paymentScheduleList.get(i).getPaymentSum());
                totalPaymentsSum = totalPaymentsSum.add(paymentScheduleList.get(i).getPaymentSum());

                paymentScheduleDto.setRepaymentSumCreditBody(paymentScheduleList.get(i).getRepaymentSumCreditBody());
                totalRepaymentsSumCreditBody = totalRepaymentsSumCreditBody.add(paymentScheduleList.get(i).getRepaymentSumCreditBody());

                paymentScheduleDto.setRepaymentSumInterest(paymentScheduleList.get(i).getRepaymentSumInterest());
                totalRepaymentsSumInterest = totalRepaymentsSumInterest.add(paymentScheduleList.get(i).getRepaymentSumInterest());

                paymentScheduleDtoList.add(paymentScheduleDto);
            }

            paymentScheduleDtoList.sort(new DateComparator());
        } else {
            this.paymentScheduleDtoList = new ArrayList<>();
        }

    }

}

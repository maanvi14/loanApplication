
package com.example.loanapplication.loan.dto;

import java.math.BigDecimal;

/*
 * REQUEST DTO (Data Transfer Object):
 *
 * LoanApplicationRequest carries loan details sent by the client(Postman or frontend) to the backend through an HTTP request.
 *
 * The Controller receives this object and passes it to the Service layer for loan evaluation.
 *
 * This DTO contains only the input data required to apply for a loan.
 * It is separate from the database entity.
 */
public class LoanApplicationRequest {

    // ID of the existing user submitting the loan application.
    private Long userId;

    // Amount of money the user wants to borrow.
    private BigDecimal loanAmount;

    // Applicant's annual income, used to evaluate loan eligibility.
    private BigDecimal annualIncome;

    // Applicant's credit score, used to check the minimum score requirement.
    private Integer creditScore;

    /*
     * No-argument constructor.
     * Jackson uses this constructor to create an empty LoanApplicationRequest object when converting incoming JSON into a Java object (deserialization).
     */
    public LoanApplicationRequest() {
    }

    //Returns the ID of the user submitting the loan application.
    public Long getUserId() {
        return userId;
    }

    //Sets the ID of the user submitting the loan application.
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    //Returns the loan amount requested by the applicant.
    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    //Sets the loan amount requested by the applicant.
    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    //Returns the applicant's annual income.
    public BigDecimal getAnnualIncome() {
        return annualIncome;
    }

    //Sets the applicant's annual income.
    public void setAnnualIncome(BigDecimal annualIncome) {
        this.annualIncome = annualIncome;
    }

    //Returns the applicant's credit score.
    public Integer getCreditScore() {
        return creditScore;
    }

    //Sets the applicant's credit score.
    public void setCreditScore(Integer creditScore) {
        this.creditScore = creditScore;
    }
}

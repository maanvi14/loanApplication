
package com.example.loanapplication.loan.dto;

import java.math.BigDecimal;

/*
 * RESPONSE DTO (Data Transfer Object):
 *
 * LoanApplicationResponse carries the loan application details from the backend to the client (Postman or frontend).
 *
 * The Service layer prepares the response, and the Controller sends it back to the client as an HTTP response.
 *
 * It contains the information that the client needs to see, without directly exposing the database entity.
 */
public class LoanApplicationResponse {

    // Unique ID generated for the saved loan application.
    private Long id;

    // ID of the user who submitted the loan application.
    private Long userId;

    // Amount of money requested by the applicant.
    private BigDecimal loanAmount;

    // Final loan decision: APPROVED or REJECTED.
    private String status;

    /*
     * No-argument constructor.
     * Jackson uses this constructor when creating this object while converting Java response data into JSON (serialization).
     */
    public LoanApplicationResponse() {
    }

    // Returns the unique ID of the loan application.
    public Long getId() {
        return id;
    }

    // Sets the unique ID of the loan application.
    public void setId(Long id) {
        this.id = id;
    }

    // Returns the ID of the applicant.
    public Long getUserId() {
        return userId;
    }

    // Sets the ID of the applicant.
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Returns the requested loan amount.
    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    // Sets the requested loan amount.
    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    // Returns the final loan approval status.
    public String getStatus() {
        return status;
    }

    // Sets the final loan approval status.
    public void setStatus(String status) {
        this.status = status;
    }
}

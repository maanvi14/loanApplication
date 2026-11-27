
package com.example.loanapplication.loan.service;

import com.example.loanapplication.loan.entity.LoanApplication;
import com.example.loanapplication.loan.repository.LoanApplicationRepository;
import com.example.loanapplication.client.UserServiceClient;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

/*
 * MILESTONE 2:
 *
 * LoanApprovalService contains the business logic for evaluating
 * loan approval decisions.
 *
 * It is a Domain Service because it handles loan eligibility
 * and approval decisions separately from user management.
 */
@Service
public class LoanApprovalService {

    /*
     * BASELINE BUSINESS RULES (OUR PROJECT ASSUMPTIONS):
     *
     * 1. Minimum credit score = 700.
     * 2. Annual income must be greater than zero.
     * 3. Requested loan amount must be greater than zero.
     * 4. Requested loan must not exceed 5 times annual income.
     * 5. Credit score must be between 300 and 900.
     */

    private static final int MIN_CREDIT_SCORE = 700;
    private static final int MIN_VALID_CREDIT_SCORE = 300;
    private static final int MAX_VALID_CREDIT_SCORE = 900;

    private static final BigDecimal MAX_LOAN_INCOME_MULTIPLIER =
            BigDecimal.valueOf(5);

    // Dependency used to communicate with the separate User Service.
    private final UserServiceClient userServiceClient;

    // Dependency used to save loan applications in the loan database.
    private final LoanApplicationRepository loanApplicationRepository;

    /*
     * Constructor Injection:
     *
     * Spring provides both dependencies:
     * 1. LoanApplicationRepository: communicates with the loan database.
     * 2. UserServiceClient: communicates with the separate User Service.
     */
    public LoanApprovalService(
            LoanApplicationRepository loanApplicationRepository,
            UserServiceClient userServiceClient) {

        this.loanApplicationRepository = loanApplicationRepository;
        this.userServiceClient = userServiceClient;
    }

    /*
     * Evaluates a loan application using the defined business rules.
     *
     * Returns APPROVED if all rules are satisfied.
     * Otherwise, returns REJECTED.
     */
    public String evaluateLoan(LoanApplication application) {

        // Rule 1: Requested loan amount must be positive.
        if (application.getLoanAmount() == null ||
                application.getLoanAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return "REJECTED";
        }

        // Rule 2: Annual income must be positive.
        if (application.getAnnualIncome() == null ||
                application.getAnnualIncome().compareTo(BigDecimal.ZERO) <= 0) {
            return "REJECTED";
        }

        // Rule 3: Credit score must be within the valid range.
        Integer creditScore = application.getCreditScore();

        if (creditScore == null ||
                creditScore < MIN_VALID_CREDIT_SCORE ||
                creditScore > MAX_VALID_CREDIT_SCORE) {
            return "REJECTED";
        }

        // Rule 4: Applicant must meet the minimum credit score.
        if (creditScore < MIN_CREDIT_SCORE) {
            return "REJECTED";
        }

        // Rule 5: Loan amount must not exceed 5 times annual income.
        BigDecimal maximumEligibleLoan =
                application.getAnnualIncome()
                        .multiply(MAX_LOAN_INCOME_MULTIPLIER);

        if (application.getLoanAmount().compareTo(maximumEligibleLoan) > 0) {
            return "REJECTED";
        }

        // All business rules have been satisfied.
        return "APPROVED";
    }

    /*
     * Submits a loan application.
     *
     * 1. Checks whether the applicant exists in User Service.
     * 2. Evaluates the loan using business rules.
     * 3. Stores the approval decision.
     * 4. Saves the application in the loan database.
     */
    public LoanApplication submitLoanApplication(LoanApplication application) {

        // A loan application must be associated with a user ID.
        if (application.getUserId() == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        // Call User Service to verify that the applicant exists.
        // If the user does not exist, the User Service returns an error.
        userServiceClient.getUserById(application.getUserId());

        // Evaluate the loan using the existing business rules.
        String decision = evaluateLoan(application);

        // Store the approval decision.
        application.setStatus(decision);

        // Save the loan application in the loan database.
        return loanApplicationRepository.save(application);
    }
}


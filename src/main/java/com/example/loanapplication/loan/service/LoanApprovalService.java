package com.example.loanapplication.loan.service;

import com.example.loanapplication.loan.entity.LoanApplication;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import com.example.loanapplication.loan.repository.LoanApplicationRepository;
/*
 * MILESTONE2:
 * LoanApprovalService contains the business logic for evaluating loan approval decisions.
 * A Domain Service is a class that contains the business rules and decisions of a particular business domain.
 * Here, LoanApprovalService is a Domain Service, why? because it handles loan eligibility and approval decisions separately from user management.
 */
@Service //@Service tells Spring to create and manage an object of this class as a Spring Bean.
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
    private static final int MIN_CREDIT_SCORE = 700; //private = keeps the rule hidden inside the class; shares one copy in memory = static , and prevents it from being changed at runtime(final).

    private static final int MIN_VALID_CREDIT_SCORE = 300;

    private static final int MAX_VALID_CREDIT_SCORE = 900;

    private static final BigDecimal MAX_LOAN_INCOME_MULTIPLIER =
            BigDecimal.valueOf(5);
    /*
     * Constructor Injection:
     *
     * Spring injects the LoanApplicationRepository bean through this constructor.
     * The repository allows this service to interact with the loan_applications table.
     */
    private final LoanApplicationRepository loanApplicationRepository;

    public LoanApprovalService(LoanApplicationRepository loanApplicationRepository) {
        this.loanApplicationRepository = loanApplicationRepository;
    }
    /*
     * Evaluates a loan application using the defined business rules.
     *
     * Returns APPROVED if all rules are satisfied.
     * Otherwise, returns REJECTED.
     */
    public String evaluateLoan(LoanApplication application) { //application = input parameter -> an object containing the applicants data(loan, amount, credit score)

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
     * 1. Evaluates the application using the business rules.
     * 2. Stores the decision (APPROVED or REJECTED) in the application.
     * 3. Saves the application in the database using the repository.
     *
     * Returns the saved application, including its generated database ID.
     */
    public LoanApplication submitLoanApplication(LoanApplication application) {

        // Evaluate the application using our existing business logic.
        String decision = evaluateLoan(application);

        // Store the approval decision in the application object.
        application.setStatus(decision);

        // Save the application in MySQL and return the saved record.
        return loanApplicationRepository.save(application);
    }

}

package com.example.loanapplication.loan.repository;

import com.example.loanapplication.loan.entity.LoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 * Repository layer:
 * Handles database operations for LoanApplication.
 *
 * JpaRepository provides ready-made methods such as:
 * save(), findById(), findAll(), deleteById().
 */
public interface LoanApplicationRepository
        extends JpaRepository<LoanApplication, Long> {
}
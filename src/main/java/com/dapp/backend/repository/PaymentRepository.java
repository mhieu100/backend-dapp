package com.dapp.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dapp.backend.model.Appointment;
import com.dapp.backend.model.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByAppointment(Appointment appointment);
}

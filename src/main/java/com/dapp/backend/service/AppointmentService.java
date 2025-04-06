package com.dapp.backend.service;

import lombok.RequiredArgsConstructor;

import java.util.List;


import org.springframework.stereotype.Service;
import org.web3j.model.VaccineAppointment;
import org.web3j.model.VaccineAppointment.Appointment;
import org.web3j.protocol.core.methods.response.TransactionReceipt;

import com.dapp.backend.model.Center;
import com.dapp.backend.model.Vaccine;
import com.dapp.backend.model.request.ReqAppointment;
import com.dapp.backend.repository.CenterRepository;
import com.dapp.backend.repository.VaccineRepository;
import com.dapp.backend.util.FormatDateTime;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    private final VaccineRepository vaccineRepository;
    private final CenterRepository centerRepository;
    private final VaccineAppointment contract;
    

    public String createAppointmentWithMetaMark(ReqAppointment reqAppointment, String walletAddress) throws Exception {
        Vaccine vaccine = vaccineRepository.findById(reqAppointment.getVaccineId()).get();
        Center center = centerRepository.findById(reqAppointment.getCenterId()).get();
        String date = FormatDateTime.convertDateToString(reqAppointment.getDate());
        String time = FormatDateTime.convertTimeToString(reqAppointment.getTime());

        TransactionReceipt receipt = contract.createAppointment(vaccine.getName(), center.getName(), date,
                time, walletAddress).send();
        return "Appointment created. Transaction hash: " +
                receipt.getTransactionHash();
    }

    public List<Appointment> getAppointmentsByCenter(String centerName) throws Exception {
        return contract.getAppointmentsByCenter(centerName).send();
    }

    public List<Appointment> getAppointmentsByPatient(String patientAddress) throws Exception {
        return contract.getAppointmentsByPatient(patientAddress).send();
    }

    // public ResAppointment convertToAppointment(VaccineAppointment.Appointment
    // appointment) {
    // ResAppointment res = new ResAppointment();
    // res.setVaccineName(appointment.vaccineName);
    // res.setCenterName(appointment.centerName);
    // res.setDate(FormatDateTime.parseStringToDate(appointment.date));
    // res.setTime(FormatDateTime.parseStringToTime(appointment.time));
    // return res;
    // }

    // private Status convertStatus(BigInteger status) {
    // switch (status.intValue()) {
    // case 0: return Status.PENDING;
    // case 1: return Status.CANCELLED;
    // case 2: return Status.PROCESSING;
    // case 3: return Status.COMPLETED;
    // default: return Status.PENDING;
    // }
    // }

    // public ResAppointment convertToReqAppointment(Appointment appointment) {
    // ResAppointment res = new ResAppointment();
    // res.setAppointmentId(appointment.getAppointmentId());
    // res.setVaccineName(appointment.getVaccine().getName());
    // res.setPatientName(appointment.getPatient().getFullname());
    // res.setDoctorName(appointment.getDoctor() != null ?
    // appointment.getDoctor().getFullname() : "");
    // res.setCashierName(appointment.getCashier() != null ?
    // appointment.getCashier().getFullname() : "");
    // res.setCenterName(appointment.getCenter().getName());
    // res.setDate(appointment.getAppointmentDate());
    // res.setTime(appointment.getAppointmentTime());
    // res.setStatus(appointment.getStatus());
    // return res;
    // }

    // public ResAppointment createAppointmentWithCash(ReqAppointment
    // reqAppointment)
    // throws UnsupportedEncodingException {
    // Vaccine vaccine =
    // vaccineRepository.findById(reqAppointment.getVaccineId()).get();
    // vaccine.setStockQuantity(vaccine.getStockQuantity() - 1);

    // Appointment appointment = Appointment.builder()
    // .vaccine(vaccineRepository.save(vaccine))
    // .patient(userRepository.findByWalletAddress(reqAppointment.getPatientAddress()).get())
    // .center(centerRepository.findById(reqAppointment.getCenterId()).get())
    // .appointmentDate(reqAppointment.getAppointmentDate())
    // .appointmentTime(reqAppointment.getAppointmentTime())
    // .status(Status.PENDING).build();
    // appointmentRepository.save(appointment);

    // Payment payment = Payment.builder()
    // .appointment(appointment)
    // .paymentDate(LocalDate.now())
    // .amount(vaccine.getPrice())
    // .paymentMethod(Payment.PaymentMethod.CASH)
    // .status(Payment.PaymentStatus.PENDING)
    // .build();
    // paymentRepository.save(payment);

    // ResAppointment res = convertToReqAppointment(appointment);
    // return res;
    // }

    // public ResPayment createAppointmentWithMetaMark(ReqAppointment
    // reqAppointment)
    // throws UnsupportedEncodingException {
    // Vaccine vaccine =
    // vaccineRepository.findById(reqAppointment.getVaccineId()).get();
    // vaccine.setStockQuantity(vaccine.getStockQuantity() - 1);
    // Appointment appointment = Appointment.builder()
    // .vaccine(vaccineRepository.save(vaccine))
    // .patient(userRepository.findByWalletAddress(reqAppointment.getPatientAddress()).get())
    // .center(centerRepository.findById(reqAppointment.getCenterId()).get())
    // .appointmentDate(reqAppointment.getAppointmentDate())
    // .appointmentTime(reqAppointment.getAppointmentTime())
    // .status(Status.PENDING).build();
    // appointmentRepository.save(appointment);
    // Payment payment = Payment.builder()
    // .appointment(appointment)
    // .paymentDate(LocalDate.now())
    // .amount(vaccine.getPrice())
    // .paymentMethod(Payment.PaymentMethod.METAMARK)
    // .status(Payment.PaymentStatus.PENDING)
    // .build();
    // payment = paymentRepository.save(payment);
    // ResPayment resPayment = new ResPayment();
    // resPayment.setPaymentId(payment.getPaymentId());
    // resPayment.setAppointmentId(appointment.getAppointmentId());
    // return resPayment;
    // }

    // @Transactional
    // public String createAppointmentWithCreditCard(ReqAppointment reqAppointment,
    // String ipAddress)
    // throws UnsupportedEncodingException {
    // Vaccine vaccine =
    // vaccineRepository.findById(reqAppointment.getVaccineId()).get();
    // vaccine.setStockQuantity(vaccine.getStockQuantity() - 1);
    // Appointment appointment = Appointment.builder()
    // .vaccine(vaccineRepository.save(vaccine))
    // .patient(userRepository.findByWalletAddress(reqAppointment.getPatientAddress()).get())
    // .center(centerRepository.findById(reqAppointment.getCenterId()).get())
    // .appointmentDate(reqAppointment.getAppointmentDate())
    // .appointmentTime(reqAppointment.getAppointmentTime())
    // .status(Status.PENDING).build();
    // appointmentRepository.save(appointment);
    // Payment payment = Payment.builder()
    // .appointment(appointment)
    // .paymentDate(LocalDate.now())
    // .amount(vaccine.getPrice())
    // .paymentMethod(Payment.PaymentMethod.CREDIT_CARD)
    // .status(Payment.PaymentStatus.PENDING)
    // .build();
    // Payment newPayment = paymentRepository.save(payment);
    // String paymentUrl =
    // vnPayService.createPaymentUrl(Math.round(vaccine.getPrice()),
    // String.valueOf(newPayment.getPaymentId()), ipAddress);
    // return paymentUrl;
    // }

    // @Transactional
    // public String updatePaymentStatus(int paymentId, String vnpResponse) {
    // Payment payment = paymentRepository.findById((long) paymentId).get();
    // if (vnpResponse.equals("00")) {
    // payment.setStatus(Payment.PaymentStatus.COMPLETED);
    // payment.setPaymentDate(LocalDate.now());
    // } else {
    // payment.setStatus(Payment.PaymentStatus.FAILED);
    // payment.setPaymentDate(LocalDate.now());
    // }
    // paymentRepository.save(payment);
    // return "Payment status updated";
    // }

    // @Transactional
    // public String updatePaymentMetaMarkStatus(int paymentId) {
    // Payment payment = paymentRepository.findById((long) paymentId).get();

    // payment.setStatus(Payment.PaymentStatus.COMPLETED);
    // payment.setPaymentDate(LocalDate.now());

    // paymentRepository.save(payment);
    // return "Payment status updated";
    // }

    // public Pagination getAllAppointments(Specification<Appointment>
    // specification, Pageable pageable) {
    // Page<Appointment> pageAppointment =
    // appointmentRepository.findAll(specification, pageable);
    // Pagination pagination = new Pagination();
    // Pagination.Meta meta = new Pagination.Meta();
    // meta.setPage(pageable.getPageNumber() + 1);
    // meta.setPageSize(pageable.getPageSize());
    // meta.setPages(pageAppointment.getTotalPages());
    // meta.setTotal(pageAppointment.getTotalElements());
    // pagination.setMeta(meta);
    // List<ResAppointment> listAppointments = pageAppointment.getContent()
    // .stream().map(this::convertToReqAppointment)
    // .collect(Collectors.toList());
    // pagination.setResult(listAppointments);
    // return pagination;
    // }

    // public Pagination getAllAppointmentsOfUser(Specification<Appointment>
    // specification, Pageable pageable) {
    // Page<Appointment> pageAppointment =
    // appointmentRepository.findAll(specification, pageable);
    // Pagination pagination = new Pagination();
    // Pagination.Meta meta = new Pagination.Meta();

    // meta.setPage(pageable.getPageNumber() + 1);
    // meta.setPageSize(pageable.getPageSize());

    // meta.setPages(pageAppointment.getTotalPages());
    // meta.setTotal(pageAppointment.getTotalElements());

    // pagination.setMeta(meta);

    // List<ResAppointment> listAppointments = pageAppointment.getContent()
    // .stream().map(this::convertToReqAppointment)
    // .collect(Collectors.toList());

    // pagination.setResult(listAppointments);

    // return pagination;
    // }

    // public ResAppointment updateAppointmentOfCashier(String id, ReqAppointment
    // reqAppointment, HttpSession session) {
    // String walletAddress = (String) session.getAttribute("walletAddress");
    // User cashier = userRepository.findByWalletAddress(walletAddress).get();
    // User doctor =
    // userRepository.findByWalletAddress(reqAppointment.getDoctorAddress()).get();
    // Appointment appointment =
    // appointmentRepository.findById(Long.parseLong(id)).get();
    // appointment.setCashier(cashier);
    // appointment.setDoctor(doctor);
    // appointment.setStatus(Status.PROCESSING);
    // this.appointmentRepository.save(appointment);
    // return convertToReqAppointment(appointment);
    // }

    // public ResAppointment cancelAppointment(String id) {
    // Appointment appointment =
    // appointmentRepository.findById(Long.parseLong(id)).get();
    // appointment.setStatus(Status.CANCELLED);
    // Payment payment = this.paymentRepository.findByAppointment(appointment);
    // payment.setStatus(Payment.PaymentStatus.FAILED);
    // payment.setPaymentDate(LocalDate.now());
    // this.paymentRepository.save(payment);
    // this.appointmentRepository.save(appointment);
    // return convertToReqAppointment(appointment);
    // }

    // public ResAppointment completeAppointment(String id) {
    // Appointment appointment =
    // appointmentRepository.findById(Long.parseLong(id)).get();
    // appointment.setStatus(Status.COMPLETED);
    // Payment payment = this.paymentRepository.findByAppointment(appointment);
    // if (payment.getPaymentMethod().equals(Payment.PaymentMethod.CASH)) {
    // payment.setStatus(Payment.PaymentStatus.COMPLETED);
    // payment.setPaymentDate(LocalDate.now());
    // }
    // this.paymentRepository.save(payment);
    // this.appointmentRepository.save(appointment);
    // return convertToReqAppointment(appointment);
    // }

}

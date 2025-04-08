package com.dapp.backend.controller;

import lombok.RequiredArgsConstructor;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.model.VaccineAppointment.Appointment;

import com.dapp.backend.annotation.ApiMessage;
import com.dapp.backend.model.mapper.AppointmentMapper;
import com.dapp.backend.model.request.ProcessAppointment;
import com.dapp.backend.model.request.ReqAppointment;
import com.dapp.backend.model.response.AppointmentDto;
import com.dapp.backend.service.AppointmentService;
import com.dapp.backend.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {
        private final AppointmentService appointmentService;
        private final UserService userService;

        @PostMapping("/meta-mark")
        @ApiMessage("Create a appointments with metamark")
        public ResponseEntity<String> createAppointmentWithMetaMark(@RequestBody ReqAppointment reqAppointment,
                        HttpSession session)
                        throws Exception {
                String walletAddress = (String) session.getAttribute("walletAddress");
                return ResponseEntity.ok()
                                .body(appointmentService.createAppointmentWithMetaMark(reqAppointment, walletAddress));
        }

        // @PostMapping("/update-metamark-payment")
        // @ApiMessage("Update status of payment metamark")
        // public ResponseEntity<String> updatePaymentMetaMarkStatus(@RequestParam int
        // paymentId) {
        // return
        // ResponseEntity.ok().body(appointmentService.updatePaymentMetaMarkStatus(paymentId));
        // }

        @GetMapping
        @ApiMessage("Get all appointments of center")
        public ResponseEntity<List<AppointmentDto>> getAllAppointmentsOfCenter(HttpSession session) throws Exception {
                String walletAddress = (String) session.getAttribute("walletAddress");
                String centerName = this.userService.getUserByWalletAddress(walletAddress).get().getCenter().getName();
                List<Appointment> appointments = appointmentService.getAppointmentsByCenter(centerName);
                List<AppointmentDto> dtos = appointments.stream()
                                .map(AppointmentMapper::toDto)
                                .collect(Collectors.toList());
                return ResponseEntity.ok().body(dtos);
        }

        @GetMapping("/my-schedule")
        @ApiMessage("Get all appointments of doctor")
        public ResponseEntity<List<AppointmentDto>> getAllAppointmentsOfDoctor(HttpSession session) throws Exception {
                String walletAddress = (String) session.getAttribute("walletAddress");
                String centerName = this.userService.getUserByWalletAddress(walletAddress).get().getCenter().getName();
                List<Appointment> appointments = appointmentService.getAppointmentsByCenter(centerName);
                List<AppointmentDto> dtos = appointments.stream()
                                .map(AppointmentMapper::toDto)
                                .filter(appointment -> appointment.getDoctorAddress() != null
                                                && walletAddress.equals(appointment.getDoctorAddress()))
                                .collect(Collectors.toList());
                return ResponseEntity.ok().body(dtos);
        }

        @PutMapping("/{id}")
        @ApiMessage("Update a appointment of cashier")
        public ResponseEntity<String> updateAppointmentOfCashier(@PathVariable long id,
                        @RequestBody ProcessAppointment processAppointment,
                        HttpSession session) throws Exception {
                String cashierWalletAddress = (String) session.getAttribute("walletAddress");
                return ResponseEntity.ok().body(appointmentService.processAppointment(BigInteger.valueOf(id),
                                processAppointment.getDoctorAddress(), cashierWalletAddress));
        }

        @PutMapping("/{id}/complete")
        @ApiMessage("Complete a appointment")
        public ResponseEntity<String> completeAppointment(@PathVariable long id) throws Exception {
                return ResponseEntity.ok().body(appointmentService.completeAppointment(BigInteger.valueOf(id)));
        }

        @PutMapping("/{id}/cancel")
        @ApiMessage("Cancel a appointment")
        public ResponseEntity<String> cancelAppointment(@PathVariable long id) throws Exception {
                return ResponseEntity.ok().body(appointmentService.cancelAppointment(BigInteger.valueOf(id)));
        }

}

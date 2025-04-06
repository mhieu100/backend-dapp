package com.dapp.backend.controller;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web3j.model.VaccineAppointment.Appointment;

import com.dapp.backend.annotation.ApiMessage;
import com.dapp.backend.model.mapper.AppointmentMapper;
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

        // @PostMapping("/cash")
        // @ApiMessage("Create a appointments with cash")
        // public ResponseEntity<ResAppointment> createAppointmentWithCash(@RequestBody ReqAppointment reqAppointment)
        //                 throws UnsupportedEncodingException {
        //         return ResponseEntity.ok().body(appointmentService.createAppointmentWithCash(reqAppointment));
        // }

        @PostMapping("/meta-mark")
        @ApiMessage("Create a appointments with metamark")
        public ResponseEntity<String> createAppointmentWithMetaMark(@RequestBody ReqAppointment reqAppointment, HttpSession session)
                        throws Exception {
                String walletAddress = (String) session.getAttribute("walletAddress");
                return ResponseEntity.ok().body(appointmentService.createAppointmentWithMetaMark(reqAppointment ,walletAddress));
        }

        // @PostMapping("/credit-card")
        // @ApiMessage("Create a appointments with credit card")
        // public ResponseEntity<String> createAppointmentWithCreditCard(@RequestBody ReqAppointment reqAppointment,
        //                 HttpServletRequest request)
        //                 throws UnsupportedEncodingException {
        //         String ipAddress = request.getRemoteAddr();
        //         return ResponseEntity.ok()
        //                         .body(appointmentService.createAppointmentWithCreditCard(reqAppointment, ipAddress));
        // }

        // @PostMapping("/update-payment")
        // @ApiMessage("Update status of payment")
        // public ResponseEntity<String> updatePaymentStatus(@RequestParam int paymentId,
        //                 @RequestParam String vnpResponse) {
        //         System.out.println(paymentId + " " + vnpResponse);
        //         return ResponseEntity.ok().body(appointmentService.updatePaymentStatus(paymentId, vnpResponse));
        // }

        // @PostMapping("/update-metamark-payment")
        // @ApiMessage("Update status of payment metamark")
        // public ResponseEntity<String> updatePaymentMetaMarkStatus(@RequestParam int paymentId) {
        //         return ResponseEntity.ok().body(appointmentService.updatePaymentMetaMarkStatus(paymentId));
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

        // @GetMapping("/my-schedule")
        // @ApiMessage("Get all appointments of doctor")
        // public ResponseEntity<Pagination> getAllAppointmentsOfDoctor(@Filter Specification<Appointment> specification,
        //                 Pageable pageable, HttpSession session) {
        //                         String walletAddress = (String) session.getAttribute("walletAddress");

        //         String docterName = this.userService.getUserByWalletAddress(walletAddress).get().getFullname();

        //         specification = Specification.where(specification).and((root, query, criteriaBuilder) -> criteriaBuilder
        //                         .equal(root.get("doctor").get("fullname"), docterName));

        //         return ResponseEntity.ok().body(appointmentService.getAllAppointments(specification, pageable));
        // }

        // @PutMapping("/{id}")
        // @ApiMessage("Update a appointment of cashier")
        // public ResponseEntity<ResAppointment> updateAppointmentOfCashier(@PathVariable String id,
        //                 @RequestBody ReqAppointment reqAppointment, HttpSession session) {
        //         return ResponseEntity.ok().body(appointmentService.updateAppointmentOfCashier(id, reqAppointment, session));
        // }

        // @PutMapping("/{id}/cancel")
        // @ApiMessage("Cancel a appointment")
        // public ResponseEntity<ResAppointment> cancelAppointment(@PathVariable String id) {
        //         return ResponseEntity.ok().body(appointmentService.cancelAppointment(id));
        // }

        // @PutMapping("/{id}/complete")
        // @ApiMessage("Complete a appointment")
        // public ResponseEntity<ResAppointment> completeAppointment(@PathVariable String id) {
        //         return ResponseEntity.ok().body(appointmentService.completeAppointment(id));
        // }
}

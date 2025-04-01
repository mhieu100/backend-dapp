package com.dapp.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dapp.backend.annotation.ApiMessage;
import com.dapp.backend.exception.InvalidException;
import com.dapp.backend.model.User;
import com.dapp.backend.model.request.ReqUser;
import com.dapp.backend.model.response.ResLogin;
import com.dapp.backend.service.AuthService;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @ApiMessage("Register a new patient")
    public ResponseEntity<User> registerUser(@RequestBody ReqUser reqUser) throws InvalidException {
        return ResponseEntity.ok().body(authService.registerUser(reqUser));
    }

    @PostMapping("/login")
    @ApiMessage("Login a patient")
    public ResponseEntity<ResLogin> loginUser(@RequestBody User user, HttpSession session) throws InvalidException {
        session.setAttribute("walletAddress", user.getWalletAddress());
        return ResponseEntity.ok().body(authService.loginUser(user.getWalletAddress()));
    }

    @GetMapping("/account")
    @ApiMessage("Get a patient's profile")
    public ResponseEntity<ResLogin> getProfile(HttpSession session) throws InvalidException {
        String walletAddress = (String) session.getAttribute("walletAddress");
        return ResponseEntity.ok().body(authService.loginUser(walletAddress));
    }

    @PostMapping("/logout")
    @ApiMessage("Logout a patient")
    public void logoutUser(HttpSession session) {
        session.removeAttribute("walletAddress");
    }
}

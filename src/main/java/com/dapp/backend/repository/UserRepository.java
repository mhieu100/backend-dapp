package com.dapp.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dapp.backend.model.User;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByWalletAddress(String walletAddress);
}

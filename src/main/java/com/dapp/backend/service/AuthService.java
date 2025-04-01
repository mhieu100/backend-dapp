package com.dapp.backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.dapp.backend.exception.InvalidException;
import com.dapp.backend.model.User;
import com.dapp.backend.model.request.ReqUser;
import com.dapp.backend.model.response.ResUser;
import com.dapp.backend.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public User registerUser(ReqUser reqUser) throws InvalidException {
        Optional<User> existUser = userRepository.findByWalletAddress(reqUser.getWalletAddress());
        if (existUser.isPresent()) {
            throw new InvalidException("Usser already exist");

        }
        User user = new User();
        user.setWalletAddress(reqUser.getWalletAddress());
        user.setFullname(reqUser.getFullname());
        user.setEmail(reqUser.getEmail());
        return userRepository.save(user);
    }

    public ResUser loginUser(String walletAddress) throws InvalidException {
        Optional<User> user = userRepository.findByWalletAddress(walletAddress);
        if (!user.isPresent()) {
            throw new InvalidException("Not found user");

        }
        ResUser resUser = new ResUser();
        resUser.setUser(user.get());
        return resUser;
    }

    public User getProfile(String walletAddress) throws InvalidException {
        Optional<User> user = userRepository.findByWalletAddress(walletAddress);
        if (!user.isPresent()) {
            throw new InvalidException("Not found user");

        }
        return user.get();
    }
}

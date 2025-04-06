package com.dapp.backend.config;

import java.math.BigInteger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.crypto.Credentials;
import org.web3j.model.VaccineAppointment;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.StaticGasProvider;

@Configuration
public class Web3jConfig {

    private static final String PRIVATE_KEY = "0x049a9cbfcf54904deabbceba024365c0d6afc7d9339e87c3677ee40ccfd7fc08";
    private static final String CONTRACT_ADDRESS = "0x90C459D0AE7802b464Db01c77b35f6CaF2782c3b";
    private static final BigInteger GAS_PRICE = BigInteger.valueOf(20_000_000_000L);
    private static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);

    @Bean
    Web3j web3j() {
        return Web3j.build(new HttpService("http://127.0.0.1:7545")); // Ganache URL
    }

    @Bean
    public Credentials credentials() {
        return Credentials.create(PRIVATE_KEY);
    }

    @Bean
    public VaccineAppointment vaccineAppointmentContract(Web3j web3j, Credentials credentials) {
        return VaccineAppointment.load(
                CONTRACT_ADDRESS,
                web3j,
                credentials,
                new StaticGasProvider(GAS_PRICE, GAS_LIMIT));
    }
}
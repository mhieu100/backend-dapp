package com.dapp.backend.config;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import com.dapp.backend.model.Permission;
import com.dapp.backend.model.Role;
import com.dapp.backend.model.User;
import com.dapp.backend.repository.PermissionRepository;
import com.dapp.backend.repository.RoleRepository;
import com.dapp.backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>> START INIT DATABASE");
        long countRoles = this.roleRepository.count();
        long countUsers = this.userRepository.count();
        long countPermissions = this.permissionRepository.count();

        if (countPermissions == 0) {
            ArrayList<Permission> arr = new ArrayList<>();
            arr.add(new Permission("Get all appointments of center", "/appointments", "GET", "APPOINTMENT"));
            arr.add(new Permission("Update a appointment of cashier", "/appointments/{id}", "PUT", "APPOINTMENT"));
            arr.add(new Permission("Cancel a appointment", "/appointments/{id}/cancel", "PUT", "APPOINTMENT"));
            arr.add(new Permission("Complete a appointment", "/appointments/{id}/complete", "PUT", "APPOINTMENT"));
            arr.add(new Permission("Create a appointments with cash", "/appointments/cash", "POST", "APPOINTMENT"));
            arr.add(new Permission("Create a appointments with credit card", "/appointments/credit-card", "POST",
                    "APPOINTMENT"));
            arr.add(new Permission("Update status of payment", "/appointments/update-payment", "POST", "APPOINTMENT"));
            arr.add(new Permission("Get all appointments of doctor", "/appointments/my-schedule", "GET",
                    "APPOINTMENT"));

            arr.add(new Permission("Access profile", "/auth/account", "GET", "AUTH"));
            arr.add(new Permission("Get all appointments of user", "/auth/my-appointments", "GET", "AUTH"));

            arr.add(new Permission("Create a center", "/centers", "POST", "CENTER"));
            arr.add(new Permission("Get a center by id", "/centers/{id}", "GET", "CENTER"));
            arr.add(new Permission("Update a center", "/centers/{id}", "PUT", "CENTER"));
            arr.add(new Permission("Delete a center", "/centers/{id}", "DELETE", "CENTER"));

            arr.add(new Permission("Create a vaccine", "/vaccines", "POST", "VACCINE"));
            arr.add(new Permission("Get a vaccine by id", "/vaccines/{id}", "GET", "VACCINE"));
            arr.add(new Permission("Update a vaccine", "/vaccines/{id}", "PUT", "VACCINE"));
            arr.add(new Permission("Delete a vaccine", "/vaccines/{id}", "DELETE", "VACCINE"));

            arr.add(new Permission("Create a permission", "/permissions", "POST", "PERMISSION"));
            arr.add(new Permission("Get all permissions", "/permissions", "GET", "PERMISSION"));
            arr.add(new Permission("Update a permission", "/permissions", "PUT", "PERMISSION"));
            arr.add(new Permission("Delete a permission", "/permissions/{id}", "DELETE", "PERMISSION"));

            arr.add(new Permission("Update a user", "/users/{walletAddress}", "PUT", "USER"));
            arr.add(new Permission("Delete a user", "/users/{walletAddress}", "DELETE", "USER"));
            arr.add(new Permission("Get all users", "/users", "GET", "USER"));
            arr.add(new Permission("Get all doctors of center", "/users/doctors", "GET", "USER"));

            arr.add(new Permission("Get all roles", "/roles", "GET", "ROLE"));
            arr.add(new Permission("Update a role", "/roles/{id}", "PUT", "ROLE"));

            arr.add(new Permission("Upload a file", "/files", "GET", "FILE"));

            this.permissionRepository.saveAll(arr);
        }

        if (countRoles == 0) {
            Role adminRole = new Role();
            Role patientRole = new Role();
            Role doctorRole = new Role();
            Role cashierRole = new Role();
            adminRole.setName("ADMIN");
            adminRole.setPermissions(this.permissionRepository.findAll());
            patientRole.setName("PATIENT");
            doctorRole.setName("DOCTOR");
            cashierRole.setName("CASHIER");
            this.roleRepository.save(adminRole);
            this.roleRepository.save(patientRole);
            this.roleRepository.save(doctorRole);
            this.roleRepository.save(cashierRole);
        }

        if (countUsers == 0) {
            User adminUser = new User();
            adminUser.setWalletAddress("0xef9fDC1e465E658ABfc0625A54fb1859B18d67C8");
            adminUser.setEmail("admin@gmail.com");
            adminUser.setFullname("I'm admin");
            Role adminRole = this.roleRepository.findByName("ADMIN");
            if (adminRole != null) {
                adminUser.setRole(adminRole);
            }
            this.userRepository.save(adminUser);

            User doctorHN = new User();
            doctorHN.setWalletAddress("0xCfB82b8DDDa9e286538162Ee244EADFC0c8cC17F");
            doctorHN.setEmail("hoangtd@gmail.com");
            doctorHN.setFullname("Hoang Tran Duy");
            Role doctorRole = this.roleRepository.findByName("DOCTOR");
            if (doctorRole != null) {
                doctorHN.setRole(doctorRole);
            }
            this.userRepository.save(doctorHN);

            User doctorHCM = new User();
            doctorHCM.setWalletAddress("0x4482bF4280d238F53Fe96b0F30fE2475e5b3e1A5");
            doctorHCM.setEmail("hieutm@gmail.com");
            doctorHCM.setFullname("Tran Minh Hieu");
            if (doctorRole != null) {
                doctorHCM.setRole(doctorRole);
            }
            this.userRepository.save(doctorHCM);

            User cashierHN = new User();
            cashierHN.setWalletAddress("0x5b4288677e4536F7CF49795D5AF6c8BBeA197B20");
            cashierHN.setEmail("hongnt@gmail.com");
            cashierHN.setFullname("Nguyen Thi Hong");
            Role cashierRole = this.roleRepository.findByName("CASHIER");
            if (cashierRole != null) {
                cashierHN.setRole(cashierRole);
            }
            this.userRepository.save(cashierHN);

            User cashierHCM = new User();
            cashierHCM.setWalletAddress("0x6E76068c73811E956BDBaA6E0E617A9Bc228b85B");
            cashierHCM.setEmail("thuydn@gmail.com");
            cashierHCM.setFullname("Doan Ngoc Thuy");
            if (cashierRole != null) {
                cashierHCM.setRole(cashierRole);
            }
            this.userRepository.save(cashierHCM);

            User patientUser = new User();
            patientUser.setWalletAddress("0xbFD9513fA8CF8775fAE52Bb49FD2d77e0E503b15");
            patientUser.setEmail("patient@gmail.com");
            patientUser.setFullname("I'm patient");
            Role patientRole = this.roleRepository.findByName("PATIENT");
            if (patientRole != null) {
                patientUser.setRole(patientRole);
            }
            this.userRepository.save(patientUser);
        }
    }

}

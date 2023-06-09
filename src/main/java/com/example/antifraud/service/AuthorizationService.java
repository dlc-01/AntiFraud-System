package com.example.antifraud.service;

import com.example.antifraud.repository.StolenCardRepository;
import com.example.antifraud.repository.SuspiciousIpRepository;
import com.example.antifraud.service.dto.UserRole;
import com.example.antifraud.util.AntiFraudException;
import com.example.antifraud.util.CommonExceptions;
import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@Component
public class AuthorizationService {
    UserService userService;
    StolenCardRepository stolenCardRepository;
    SuspiciousIpRepository suspiciousIpRepository;

    public void validateEntry(Principal userPrincipal, List<UserRole> expectedRoles) throws AntiFraudException {
        if (userPrincipal == null) {
            throw CommonExceptions.NO_PRINCIPAL.getException();
        } else if (userPrincipal.getName() != null) {
            UserDetails user = userService.loadUserByUsername(userPrincipal.getName());
            if (user != null) {
                if (expectedRoles.stream()
                        .map(i -> new SimpleGrantedAuthority("ROLE_" + i.name()))
                        .anyMatch(i -> user.getAuthorities().contains(i))) {
                    return;
                }
                throw CommonExceptions.ACCESS_DENIED.getException();
            }
        }
        throw CommonExceptions.INVALID_CREDENTIALS.getException();
    }

    public void validateEntry(Principal userPrincipal, UserRole expectedRole) throws AntiFraudException {
        validateEntry(userPrincipal, List.of(expectedRole));
    }
}

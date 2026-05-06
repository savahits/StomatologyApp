package ru.shmelev.stomatologyapp.security;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.shmelev.stomatologyapp.domain.User;

import java.util.Collection;
import java.util.List;

public record CustomUserDetails(User user) implements UserDetails {

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getRole().getName()));
    }

    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    public String getHelloName() {
        if (user.getDoctor() != null) {
            String patronymic = user.getDoctor().getPatronymic() != null ? " " + user.getDoctor().getPatronymic() : "";
            return user.getDoctor().getName() + patronymic;
        } else {
            return user.getUsername();
        }
    }

    public boolean hasRole(String role) {
        return getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals(role));
    }

    public Long getDoctorId() {
        return user.getDoctor() != null ? user.getDoctor().getId() : null;
    }

    public String getRoleName() {
        return user.getRole() != null ? user.getRole().getName() : null;
    }

}

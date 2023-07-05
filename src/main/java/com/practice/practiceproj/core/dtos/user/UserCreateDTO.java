package com.practice.practiceproj.core.dtos.user;


import jakarta.validation.constraints.NotBlank;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class UserCreateDTO extends UserDTO {
    @NotBlank
    private String password;

    public UserCreateDTO() {
    }

    public UserCreateDTO(String mail, String fio, UserRoleDTO role,
                         UserStatusDTO status, String password) {
        super(UUID.randomUUID(), Instant.now(), Instant.now(), mail, fio, role, status);
        this.password = password;
    }

    public UserCreateDTO(UUID uuid, Instant dt_create, Instant dt_update, String mail,
                         String fio, UserRoleDTO role, UserStatusDTO status, String password) {
        super(uuid, dt_create, dt_update, mail, fio, role, status);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserCreateDTO that = (UserCreateDTO) o;
        return super.equals(o)
                && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), password);
    }
}

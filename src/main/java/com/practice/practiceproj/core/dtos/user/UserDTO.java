package com.practice.practiceproj.core.dtos.user;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.practice.practiceproj.core.BaseEssence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@JsonPropertyOrder({"uuid",
        "dt_create",
        "dt_update",
        "mail",
        "fio",
        "role",
        "status"})
public class UserDTO extends BaseEssence {
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$",
            message = "illegal format of email,correct example: email@mail.ru , google@gmail.com")
    protected String mail;
    @NotBlank
    protected String fio;
    @NotNull
    protected UserRoleDTO role;
    @NotNull
    protected UserStatusDTO status;

    public UserDTO() {
    }

    public UserDTO(String mail, String fio, UserRoleDTO role, UserStatusDTO status) {
        super(UUID.randomUUID(), Instant.now(), Instant.now());
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
    }

    public UserDTO(UUID uuid, Instant dt_create, Instant dt_update, String mail,
                   String fio, UserRoleDTO role, UserStatusDTO status) {
        super(uuid, dt_create, dt_update);
        this.mail = mail;
        this.fio = fio;
        this.role = role;
        this.status = status;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public UserRoleDTO getRole() {
        return role;
    }

    public void setRole(UserRoleDTO role) {
        this.role = role;
    }

    public UserStatusDTO getStatus() {
        return status;
    }

    public void setStatus(UserStatusDTO status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        UserDTO userDTO = (UserDTO) o;
        return super.equals(o)
                && Objects.equals(mail, userDTO.mail)
                && Objects.equals(fio, userDTO.fio)
                && role == userDTO.role
                && status == userDTO.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), mail, fio, role, status);
    }
}

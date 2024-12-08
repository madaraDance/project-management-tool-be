package com.example.project_management_tool.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(
        name = "first_name",
        nullable = false
    )
    @NotBlank(message = "First Name can not be empty or null.")
    @Size(
        max = 25,
        message = "First Name must be at most 25 characters")
    private String firstName;

    @Column(
        name = "last_name",
        nullable = false)
    @NotBlank(message = "Last Name can not be empty or null.")
    @Size(
        max = 25,
        message = "Last Name must be at most 25 characters")
    private String lastName;

    @Column(
        name = "email",
        nullable = false,
        unique = true)
    @NotBlank(message = "Email can not be empty or null.")
    @Email(message = "Invalid email format")
    @Size(
            max = 75,
            message = "First Name must be at most 75 characters")
    private String email;

    @Column(
        name = "password",
        nullable = false,
        columnDefinition = "TEXT")
    @NotBlank(message = "Password can not be empty or null.")
    @Size(
        min = 8,
        message = "Password must be at least 8 characters")
    private String password;

    @Column(
        name = "company_id",
        nullable = false)
    private UUID companyId;

    @Transient
    private List<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

package com.hemodialBackend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.STRING, length = 20)
@Table(name="gerant",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String prenom;

    @Column
    private String nom;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Column
    @NotBlank
    @Size(max = 120)
    private String password;

    @Column
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(style = "yyyy-MM-dd")
    private LocalDate dateNaissance;

    @Column
    @Enumerated(EnumType.STRING)
    private Civilite civlite;

    @Column
    private Long phone;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime createDateTime;

    @Column
    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @Column
    @JsonIgnore
    private String resetPasswordToken;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role = Role.DOCTOR;

    @JsonIgnore
    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
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

/*
 
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING, length = 20)
@Table(uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        })
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column(updatable = false)
    @CreationTimestamp
    @JsonIgnore
    private LocalDateTime createDateTime;

    @Column
    @UpdateTimestamp
    @JsonIgnore
    private LocalDateTime updateDateTime;
    
    @Column
    private String prenom;

    @Column
    private String nom;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @Column
    @NotBlank
    @Size(max = 120)
    private String password;

    @Column
    @JsonIgnore
    private String resetPasswordToken;

    @Column
    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    private String username;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", createDateTime=" + createDateTime + ", updateDateTime=" + updateDateTime
				+ ", prenom=" + prenom + ", nom=" + nom + ", email=" + email + ", password=" + password
				+ ", resetPasswordToken=" + resetPasswordToken + ", role=" + role + ", username=" + username + "]";
	}




}

*/

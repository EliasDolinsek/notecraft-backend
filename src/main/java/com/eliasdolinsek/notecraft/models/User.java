package com.eliasdolinsek.notecraft.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = "email")
})
@NoArgsConstructor
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Getter
  private Long id;

  @NotBlank
  @Size(max = 50)
  @Email
  @Getter
  @Setter
  private String email;

  @NotBlank
  @Size(max = 120)
  @Getter
  @Setter
  private String password;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
  @Getter
  @Setter
  private Set<Role> roles = new HashSet<>();

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }
}
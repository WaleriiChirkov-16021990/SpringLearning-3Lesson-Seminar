//package ru.gb.springdemo.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.Collection;
//import java.util.List;
//
//@Entity
//@Builder
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Table(name = "users")
//public class USer implements UserDetails {
//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
//    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
//    private Long id;
//
//    @Column(name = "username", unique = true, nullable = false)
//    private String username;
//
//    @Column(name = "password", nullable = false)
//    private String password;
//
//    @Column(name = "email", unique = true, nullable = false)
//    private String email;
//
//    @Enumerated(EnumType.STRING)
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(name = "persons_roles",
//            joinColumns = @JoinColumn(name = "person_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private List<Role> role;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return role.stream().map(r -> new SimpleGrantedAuthority(r.getName())).toList();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}

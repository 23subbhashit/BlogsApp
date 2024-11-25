//package com.example.blogs.entities;
//
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Entity
//@Table(name="users")
//@NoArgsConstructor
//@Getter
//@Setter
//public class User implements UserDetails {
//
//   @Id
//   @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//   @Column(name="user_name", nullable=false, length=100)
//   private String name;
//    private String email;
//    private String password;
//    private String about;
//
//    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<Post> posts=new ArrayList<>();
//
//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//    @JoinTable(name="user_role",joinColumns = @JoinColumn(name="user",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name="role", referencedColumnName = "id"))
//    private Set<Role> roles=new HashSet<>();
//
//
////    @Enumerated(value = EnumType.STRING)
////    private Role role;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities=this.roles.stream().map((role)->new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//        return authorities;
//    }
//
//    @Override
//    public String getUsername() {
//        return this.email;
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
package com.example.blogs.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="user_name", nullable=false, length=100)
    private String name;

    private String email;
    private String password;
    private String about;

    @OneToMany(mappedBy = "user", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts=new ArrayList<>();

    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "role_id", referencedColumnName = "id")
    //private String role;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    // other UserDetails methods...

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(getRole()));
//        return authorities;
//    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getUserAuthorities();
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

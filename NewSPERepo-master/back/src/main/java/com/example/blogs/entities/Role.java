package com.example.blogs.entities;//package com.example.blogs.entities;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import lombok.Data;
//
//@Entity
//@Data
//public class Role {
//    @Id
//    private int id;
//    private String name;
//}


import com.example.blogs.entities.Permission;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//package com.example.blogs.entities;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum Role {
    USER(
            Set.of(
                    Permission.USER_GET,
                    Permission.USER_POST,
                    Permission.ADMIN_GET,
                    Permission.ADMIN_POST
            )
    ),
    ADMIN(
            Set.of(
                    Permission.ADMIN_GET,
                    Permission.ADMIN_POST
            )
    );

    private Set<Permission> permissions;

    public List<SimpleGrantedAuthority> getUserAuthorities() {
        var authorities = new java.util.ArrayList<>(getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .toList());

        authorities.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return authorities;
    }
}
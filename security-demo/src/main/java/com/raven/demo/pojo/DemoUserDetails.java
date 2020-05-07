package com.raven.demo.pojo;

import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Setter
public class DemoUserDetails implements UserDetails {

    private Long id;
    private String username;
    private String password;
    private int enabled;
    private Collection<? extends GrantedAuthority> authorities;


    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return this.enabled == 1;
    }

    public Long getId() {
        return id;
    }
}

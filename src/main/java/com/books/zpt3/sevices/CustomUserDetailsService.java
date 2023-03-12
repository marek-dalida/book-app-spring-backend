package com.books.zpt3.sevices;

import com.books.zpt3.models.Role;
import com.books.zpt3.models.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.HashMap;

public class CustomUserDetailsService implements UserDetailsService {

    static HashMap<String, String> users = new HashMap<String, String>() {{
        put("user1", "user1");
        put("user2", "user2");
        put("user3", "user3");
        put("user4", "user4");
        put("user5", "user5");
        put("admin", "admin");
    }};

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {

        if(users.containsKey(s)){
            User user;
            if(s.equals("admin")){
                user = new User(s, s, Role.ADMIN);
            } else {
                user = new User(s, s, Role.USER);
            }
            return new UserPrincipal(user);
        } else {
            throw new UsernameNotFoundException(s);
        }
    }
}

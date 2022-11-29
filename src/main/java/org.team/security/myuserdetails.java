pack org.team.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.team.exceptions.UserNotFoundException;
import org.team.models.student;
import org.team.models.faculty;

import java.util.Collection;
import java.util.Collections;


public class MyUserDetails implements UserDetails {
    private student student;
    private faculty faculty;

    public MyUserDetails(faculty faculty, student student){
       this.faculty = faculty;
       this.student = student;
   }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       if (faculty == null){
           return Collections.singletonList(new SimpleGrantedAuthority("student"));
       }else {
           return Collections.singletonList(new SimpleGrantedAuthority("faculty"));
       }
    }

    @Override
    public String getPassword() {
       if (faculty == null && student != null){
           return student.getPassword();
       }else if (student == null && faculty != null){
           return faculty.getPassword();
       }else {
           throw new UserNotFoundException();
       }
    }

    @Override
    public String getUsername() {
        if (faculty == null && student != null){
            return student.getUsername();
        }else if (faculty != null && student == null){
            return faculty.getUsername();
        }else {
            throw new UserNotFoundException();
        }
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
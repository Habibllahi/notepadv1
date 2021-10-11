/**
 * @Author: Hamzat Habibllahi
 */
package ng.com.codetrik.notepad.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static ng.com.codetrik.notepad.user.Permission.*;

public enum Role {
    DEVELOPER(new HashSet(Arrays.asList(WRITE,READ,UPDATE,DELETE,SPECIAL))),
    USER(new HashSet(Arrays.asList(WRITE,READ,UPDATE,DELETE)));


    private final Set<Permission> permission;

    Role(Set<Permission> permission){
        this.permission =permission;
    }

    public Set<SimpleGrantedAuthority> getAuthoritiesOfRole(){
        Set<SimpleGrantedAuthority> sga = this.permission.stream().map( permission -> new SimpleGrantedAuthority(permission.name())).collect(Collectors.toSet());
        sga.add(new SimpleGrantedAuthority("ROLE_"+this.name()));
        return sga;
    }

}

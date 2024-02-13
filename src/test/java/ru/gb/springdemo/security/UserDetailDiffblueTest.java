package ru.gb.springdemo.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import ru.gb.springdemo.model.Person;
import ru.gb.springdemo.model.Role;

class UserDetailDiffblueTest {
    /**
     * Method under test: {@link UserDetail#getAuthorities()}
     */
    @Test
    void testGetAuthorities() {
        // Arrange
        Person user = new Person();
        user.setRole(new ArrayList<>());

        // Act and Assert
        assertTrue((new UserDetail(user)).getAuthorities().isEmpty());
    }

    /**
     * Method under test: {@link UserDetail#getAuthorities()}
     */
    @Test
    void testGetAuthorities2() {
        // Arrange
        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());

        ArrayList<Role> role2 = new ArrayList<>();
        role2.add(role);

        Person user = new Person();
        user.setRole(role2);

        // Act
        Collection<? extends GrantedAuthority> actualAuthorities = (new UserDetail(user)).getAuthorities();

        // Assert
        assertEquals(1, actualAuthorities.size());
        assertEquals("Name", ((List<? extends GrantedAuthority>) actualAuthorities).get(0).getAuthority());
    }

    /**
     * Method under test: {@link UserDetail#getAuthorities()}
     */
    @Test
    void testGetAuthorities3() {
        // Arrange
        Role role = new Role();
        role.setName("Name");
        role.setPersons(new ArrayList<>());
        role.setUuid(UUID.randomUUID());

        Role role2 = new Role();
        role2.setName("42");
        role2.setPersons(new ArrayList<>());
        role2.setUuid(UUID.randomUUID());

        ArrayList<Role> role3 = new ArrayList<>();
        role3.add(role2);
        role3.add(role);

        Person user = new Person();
        user.setRole(role3);

        // Act
        Collection<? extends GrantedAuthority> actualAuthorities = (new UserDetail(user)).getAuthorities();

        // Assert
        assertEquals(2, actualAuthorities.size());
        assertEquals("42", ((List<? extends GrantedAuthority>) actualAuthorities).get(0).toString());
        assertEquals("Name", ((List<? extends GrantedAuthority>) actualAuthorities).get(1).toString());
    }

    /**
     * Method under test: {@link UserDetail#getPassword()}
     */
    @Test
    void testGetPassword() {
        // Arrange, Act and Assert
        assertNull((new UserDetail(new Person())).getPassword());
    }

    /**
     * Method under test: {@link UserDetail#getUsername()}
     */
    @Test
    void testGetUsername() {
        // Arrange, Act and Assert
        assertNull((new UserDetail(new Person())).getUsername());
    }

    /**
     * Methods under test:
     *
     * <ul>
     *   <li>{@link UserDetail#UserDetail(Person)}
     *   <li>{@link UserDetail#isAccountNonExpired()}
     *   <li>{@link UserDetail#isAccountNonLocked()}
     *   <li>{@link UserDetail#isCredentialsNonExpired()}
     *   <li>{@link UserDetail#isEnabled()}
     * </ul>
     */
    @Test
    void testGettersAndSetters() {
        // Arrange
        Person user = new Person();
        user.setId(UUID.randomUUID());
        user.setName("Name");
        user.setPassword("iloveyou");
        user.setRole(new ArrayList<>());

        // Act
        UserDetail actualUserDetail = new UserDetail(user);
        boolean actualIsAccountNonExpiredResult = actualUserDetail.isAccountNonExpired();
        boolean actualIsAccountNonLockedResult = actualUserDetail.isAccountNonLocked();
        boolean actualIsCredentialsNonExpiredResult = actualUserDetail.isCredentialsNonExpired();

        // Assert
        assertTrue(actualIsAccountNonExpiredResult);
        assertTrue(actualIsAccountNonLockedResult);
        assertTrue(actualIsCredentialsNonExpiredResult);
        assertTrue(actualUserDetail.isEnabled());
    }
}

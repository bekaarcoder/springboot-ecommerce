package com.ecommerce.shopmebackend.user;

import static org.assertj.core.api.Assertions.assertThat;

import com.ecommerce.shopmecommon.entity.Role;
import com.ecommerce.shopmecommon.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void testCreateUser() {
        Role adminRole = testEntityManager.find(Role.class, Long.valueOf(1));
        User newUser = new User("johndoe@gmail.com", "password123", "John", "Doe");
        newUser.addRole(adminRole);

        User savedUser = userRepository.save(newUser);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateUserWithTwoRoles() {
        Role roleEditor = new Role((long) 3);
        Role roleAssistant = new Role((long) 5);
        User userJane = new User("janedoe@gmail.com", "password123", "Jane", "Doe");

        userJane.addRole(roleEditor);
        userJane.addRole(roleAssistant);

        User savedUser = userRepository.save(userJane);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> userList = userRepository.findAll();
        userList.forEach(user -> System.out.println(user));

    }

    @Test
    public void testGetUserById() {
        User user = userRepository.findById(Long.valueOf(1)).get();
        System.out.println(user);
        assertNotNull(user);
    }

    @Test
    public void testUpdateUser() {
        User user = userRepository.findById(Long.valueOf(1)).get();
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Test
    public void testUpdateUserRole() {
        User user = userRepository.findById(Long.valueOf(2)).get();
        Role roleEditor = new Role((long) 3);
        Role roleSalesPerson = new Role((long) 2);

        user.getRoles().remove(roleEditor);
        user.addRole(roleSalesPerson);

        userRepository.save(user);
    }

    @Test
    public void testDeleteUser() {
        Long userId = (long) 2;
        userRepository.deleteById(userId);
    }

    @Test
    public void testFindUserEmail() {
        User user = userRepository.findByEmail("bruce@gmail.com");
        System.out.println(user);
        assertNotNull(user);
    }

    @Test
    public void testCountById() {
        Long id = (long) 3;
        Long count = userRepository.countById(id);
        System.out.println(count);
        assertNotNull(count);
    }

    @Test
    public void testEnableUser() {
        Long id = (long) 3;
        userRepository.updateUserStatus(id, true);

        User user = userRepository.findById(id).get();
        assertTrue(user.isEnabled());
    }

    @Test
    public void testDisableUser() {
        Long id = (long) 3;
        userRepository.updateUserStatus(id, false);

        User user = userRepository.findById(id).get();
        assertFalse(user.isEnabled());
    }

    @Test
    public void testPaginationFirstPage() {
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(pageable);

        List<User> userList = page.getContent();

        userList.forEach(user -> System.out.println(user));

        assertEquals(userList.size(), pageSize);
    }

    @Test
    public void testSearchUsers() {
        String keyword = "john doe";
        int pageNumber = 0;
        int pageSize = 4;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = userRepository.findAll(keyword, pageable);

        List<User> userList = page.getContent();
        userList.forEach(user -> System.out.println(user));

        assertTrue(userList.size() > 0);
    }

}
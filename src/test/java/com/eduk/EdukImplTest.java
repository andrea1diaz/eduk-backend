package com.eduk;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;
import com.eduk.model.*;
import com.eduk.repository.*;

public class EdukImplTest {
    @Autowired
    UserRepository userRepository;

    @Test(invocationCount = 100, threadPoolSize = 5)
    public void testTranslateDummy() throws Exception {
      // insert into users(id, email, first_name, last_name, password) values(3,"dlinares@atumplaya.com","Diego","Linares","$2a$10$/KnfV1UjrZxuCDQFMlkOOO924NQgxv6.ntQfdcudwT3QjcgFUvnNO"). Or register ir manually (RECOMMENDED).
      User testUser = new User("Diego", "Linares", "dlinares@atumplaya.com", "diego123@");
      Assert.assertEquals(testUser.getFirstName(), userRepository.findByEmail("dlinares@atumplaya.com").get().getFirstName()); 
    }
}

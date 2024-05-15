package uk.gov.hmcts.ecm.common.idam.models;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDetailsTest {

    @Test
    void userDetails() {
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail("example@gmail.com");
        userDetails.setFirstName("firstName");
        userDetails.setLastName("lastName");
        userDetails.setName("name");
        userDetails.setRoles(Arrays.asList("caseWorker", "judge"));
        userDetails.setUid("1111");
        assertEquals("example@gmail.com", userDetails.getEmail());
        assertEquals("firstName", userDetails.getFirstName());
        assertEquals("lastName", userDetails.getLastName());
        assertEquals("name", userDetails.getName());
        assertEquals("[caseWorker, judge]", userDetails.getRoles().toString());
        assertEquals("1111", userDetails.getUid());
    }
}
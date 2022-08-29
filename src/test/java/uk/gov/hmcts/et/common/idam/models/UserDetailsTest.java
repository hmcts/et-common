package uk.gov.hmcts.et.common.idam.models;

import org.junit.Test;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

public class UserDetailsTest {

    @Test
    public void userDetails() {
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
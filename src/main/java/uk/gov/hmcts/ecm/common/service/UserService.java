package uk.gov.hmcts.ecm.common.service;

import uk.gov.hmcts.ecm.common.idam.models.UserDetails;

public interface UserService {

    UserDetails getUserDetails(String authorisation);

    UserDetails getUserDetailsById(String authToken, String userId);
}

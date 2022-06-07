package uk.gov.hmcts.et.common.service;

import uk.gov.hmcts.et.common.idam.models.UserDetails;

public interface UserService {

    UserDetails getUserDetails(String authorisation);

}
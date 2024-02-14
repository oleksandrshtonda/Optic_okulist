package spring.boot.optic.okulist.service.user.passwordreset;

import spring.boot.optic.okulist.model.user.User;

public interface UserPasswordInitiationService {

    void initiatePasswordChange();

    String generateVerificationCode();
}

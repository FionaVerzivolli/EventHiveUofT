package app.use_case.login;
import app.entity.User.User;

public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserData userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserData userDataAccessObject, LoginOutputBoundary loginPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.loginPresenter = loginPresenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        User user = userDataAccessObject.findUserByUsername(loginInputData.getUsername());
        if (user == null) {
            loginPresenter.prepareFailView("Username does not exist.");
        } else if (!user.getPassword().equals(loginInputData.getPassword())) {
            loginPresenter.prepareFailView("Incorrect password.");
        } else {
            LoginOutputData outputData = new LoginOutputData(user.getUsername(), true);
            loginPresenter.prepareSuccessView(outputData);
        }
    }
}
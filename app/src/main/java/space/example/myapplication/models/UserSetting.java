package space.example.myapplication.models;

public class UserSetting {

    private User user;
    private UserAccountSetting setting;

    public UserSetting(User user, UserAccountSetting setting) {
        this.user = user;
        this.setting = setting;
    }
    public UserSetting() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserAccountSetting getSetting() {
        return setting;
    }

    public void setSetting(UserAccountSetting setting) {
        this.setting = setting;
    }

    @Override
    public String toString() {
        return "userSetting{" +
                "user=" + user +
                ", setting=" + setting +
                '}';
    }
}

public class UserClassBuilser implements UserBuilder{
    User user=new User();
    @Override
    public UserBuilder setLogin(String login) {
        user.login=login;
        return this;
    }

    @Override
    public UserBuilder setPassword(String password) {
        user.password=password;
        return this;
    }

    @Override
    public UserBuilder setName(String name) {
        user.name=name;
        return this;
    }

    @Override
    public UserBuilder setSname(String sname) {
        user.sname=sname;
        return this;
    }

    @Override
    public UserBuilder setMiddlename(String middlename) {
        user.middlename=middlename;
        return this;
    }

    @Override
    public UserBuilder setPost(String post) {
        user.post=post;
        return this;
    }

    @Override
    public User build() {
        return user;
    }
}

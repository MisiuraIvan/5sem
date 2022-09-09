public interface UserBuilder {
    UserBuilder setLogin(String login);
    UserBuilder setPassword(String password);
    UserBuilder setName(String name);
    UserBuilder setSname(String sname);
    UserBuilder setMiddlename(String middlename);
    UserBuilder setPost(String post);
    User build();
}

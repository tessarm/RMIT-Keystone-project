package dmcjj.rmitpp.toiletlocator.security;

/**
 * Created by A on 30/08/2017.
 */

public class Security
{
    public static final String EMAIL_SUPERUSER = "pp1@gmail.com";
    private static Security instance;

    private final LoginAuthorizer loginAuthorizer = new TestAuthorizer();

    private Security(){

    }

    public static Security get(){
        if(instance == null)
            instance = new Security();
        return instance;
    }
    public LoginAuthorizer getLoginAuthorizer(){
        return loginAuthorizer;
    }

    public static boolean isSuperUser(String username, String password) {
        if(username.contentEquals("pp1@gmail.com") && password.contentEquals("password"))
            return true;
        return false;
    }
}

package dmcjj.rmitpp.toiletlocator.security;

/**
 * Created by A on 30/08/2017.
 */

public class Security
{
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
}

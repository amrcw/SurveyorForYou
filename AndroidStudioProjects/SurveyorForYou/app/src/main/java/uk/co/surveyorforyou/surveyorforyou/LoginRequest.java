package uk.co.surveyorforyou.surveyorforyou;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ruwan on 09/02/2017.
 */

public class LoginRequest extends StringRequest {

    private static final String LOGIN_REQUEST_URI = "http://www.surveyor-for-you.co.uk/Android/login.php";
    private Map<String, String> params;

    public LoginRequest( String username, String password, Response.Listener<String> listener) {
        super(Request.Method.POST, LOGIN_REQUEST_URI, listener, null);

        params = new HashMap<>();

        params.put("username",username);
        params.put("password",password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

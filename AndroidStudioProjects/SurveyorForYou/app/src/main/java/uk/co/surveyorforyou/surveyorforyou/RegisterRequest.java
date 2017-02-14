package uk.co.surveyorforyou.surveyorforyou;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ruwan on 08/02/2017.
 */

public class RegisterRequest extends StringRequest {

    private static final String REGISTER_REQUEST_URI = "http://www.surveyor-for-you.co.uk/Android/register.php";
    private Map<String, String> params;

    public RegisterRequest(String firstname, String lastname, String email, String username, String password, String address, String postcode, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URI, listener, null);

        params = new HashMap<>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("email", email);
        params.put("address",address);
        params.put("postcode",postcode);
        params.put("username",username);
        params.put("password",password);

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}

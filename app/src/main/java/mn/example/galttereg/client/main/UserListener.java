package mn.example.galttereg.client.main;

import mn.example.galttereg.client.entity.User;
import mn.example.galttereg.client.response.Response;

public interface UserListener {
    void onSuccess(User user);

    void errorResponse(Response errorResponce);
}

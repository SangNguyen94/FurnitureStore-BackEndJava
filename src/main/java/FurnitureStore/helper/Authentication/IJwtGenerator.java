package FurnitureStore.helper.Authentication;

import FurnitureStore.dto.UserDTO;

public interface IJwtGenerator {
    String createJWT(UserDTO user);

    boolean decodeJWT(String jwt, String email);
}
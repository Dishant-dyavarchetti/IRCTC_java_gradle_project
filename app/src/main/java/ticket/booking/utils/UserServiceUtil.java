package ticket.booking.utils;
import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {
    public static String hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkpassword(String plainPassword, String hashedPassword){
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}

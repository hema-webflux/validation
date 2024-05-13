package hema.web.validation.concerns.verifier;

public interface DatabasePresenceVerifier  extends PresenceVerifier {

    /**
     * Set the connection to be used.
     *
     * @param connection connection name.
     */
    void setConnection(String connection);

}

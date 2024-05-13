package hema.web.validation.concerns.verifier;

final class DatabasePresenceVerifierBean implements DatabasePresenceVerifier {

    private String connection;

    @Override
    public void setConnection(String connection) {
        this.connection = connection;
    }

    @Override
    public int getCount(String collection, String column, String value, Long excludeId, String idColumn, Object[] extra) {
        return 0;
    }

    @Override
    public int getMultiCount(String collection, String column, String[] values, Object[] extra) {
        return 0;
    }
}

package hema.web.validation.concerns.verifier;

interface PresenceVerifier {

    int getCount(String collection, String column, String value, Long excludeId, String idColumn, Object[] extra);

    int getMultiCount(String collection, String column, String[] values, Object[] extra);

}

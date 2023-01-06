package sorting.common;

public enum CliParameterValue {
    WORD("word"),
    LINE("line"),
    LONG("long"),
    NATURAL("natural"),
    BYCOUNT("byCount");

    private final String cliParameterValue;
    CliParameterValue(String cliParameterValue) {
        this.cliParameterValue = cliParameterValue;
    }

    public String getCliParameterValue() {
        return cliParameterValue;
    }
}

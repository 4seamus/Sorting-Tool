package sorting;

public enum CliParameter {
    DATATYPE("-dataType"),
    SORTINTEGERS("-sortIntegers");

    private final String cliParameter;

    // Using enum constructor because enum cannot begin with "-", which is required for cli parameters.
    // This way cli parameters can be defined in a single location as enum
    CliParameter(String cliParameter) {
        this.cliParameter = cliParameter;
    }

    public String getCliParameter() {
        return cliParameter;
    }
}

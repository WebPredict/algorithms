package gen;

import alg.strings.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 12/16/14
 * Time: 4:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class Validation {

    private ValidationType validationType;
    private Object details; // TODO

    public Validation(ValidationType validationType, Object details) {
        this.validationType = validationType;
        this.details = details;
    }

    public Validation(ValidationType validationType) {
        this.validationType = validationType;
    }

    public ValidationType getValidationType() {
        return validationType;
    }

    public void setValidationType(ValidationType validationType) {
        this.validationType = validationType;
    }

    public Object getDetails() {
        return details;
    }

    public Integer getDetailsAsInt () {
        return (Integer)details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }

    public static Validation parseValidation (String text) {
        String trimmed = text.trim().toLowerCase();
        if (trimmed.startsWith("min")) {
            return (new Validation(ValidationType.MIN_LENGTH, StringUtils.extractIntegerFrom(trimmed)));
        }
        else if (trimmed.startsWith("max")) {
            return (new Validation(ValidationType.MAX_LENGTH, StringUtils.extractIntegerFrom(trimmed)));
        }
        else if (trimmed.startsWith("required")) {
            return (new Validation(ValidationType.NOT_NULL));
        }
        else if (trimmed.startsWith("email")) {
            return (new Validation(ValidationType.EMAIL));
        }
        else if (trimmed.startsWith("phone")) {
            return (new Validation(ValidationType.PHONE));
        }
        else if (trimmed.startsWith("date")) {
            return (new Validation(ValidationType.DATE));
        }
        else if (trimmed.startsWith("time")) {
            return (new Validation(ValidationType.TIME));
        }
        else if (trimmed.startsWith("alpha")) {
            return (new Validation(ValidationType.ALPHABETIC));
        }
        else if (trimmed.startsWith("numeric")) {
            return (new Validation(ValidationType.NUMERIC));
        }
        else
            throw new RuntimeException("What validation type is this?" + trimmed);
    }

    public String toString () {
        return (validationType.toString() + " - " + (details == null ? "" : details.toString()));
    }
}

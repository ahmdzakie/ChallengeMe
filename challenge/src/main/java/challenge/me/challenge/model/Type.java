package challenge.me.challenge.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Type {

    Cyclic("cyclic"),
    Onetime("one_time");

    private String jsonValue;

    private Type(final String json) {
        this.jsonValue = json;
    }

    @JsonValue
    public String jsonValue() {
        return this.jsonValue;
    }

}

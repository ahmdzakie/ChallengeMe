package challenge.me.challenge.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ChallengeType {

    cyclic("cyclic"),
    one_time("one_time");

    private String jsonValue;

    private ChallengeType(final String json) {
        this.jsonValue = json;
    }

    @JsonValue
    public String jsonValue() {
        return this.jsonValue;
    }

}

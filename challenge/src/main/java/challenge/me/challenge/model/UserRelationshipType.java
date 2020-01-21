package challenge.me.challenge.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum UserRelationshipType {

    pending_first_second("pending_first_second"),
    pending_second_first("pending_second_first"),
	friends("friends"),
	block_first_second("block_first_second"),
	block_second_first("block_second_first"),
	block_both("block_both"),
	same_user("same_user"),
	not_related("not_related");

    private String jsonValue;

    private UserRelationshipType(final String json) {
        this.jsonValue = json;
    }

    @JsonValue
    public String jsonValue() {
        return this.jsonValue;
    }

}

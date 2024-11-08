package ee.ria.eits.backoffice.api_client.exceptions;

import lombok.Getter;

@Getter
public class ApiIllegalArgumentException extends RuntimeException {
    private final String[] messages;

    public ApiIllegalArgumentException(String[] messages) {
        this.messages = messages;
    }
}

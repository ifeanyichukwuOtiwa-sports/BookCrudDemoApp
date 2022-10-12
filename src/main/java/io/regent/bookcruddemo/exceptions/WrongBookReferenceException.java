package io.regent.bookcruddemo.exceptions;

import org.springframework.core.NestedExceptionUtils;
import org.springframework.core.NestedRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;


/**
 * Created by @author Ifeanyichukwu Otiwa
 * 12/10/2022
 */


public class WrongBookReferenceException extends NestedRuntimeException {
    private final int status;
    @Nullable
    private final String reason;

    public WrongBookReferenceException(HttpStatus status) {
        this(status, (String)null);
    }

    public WrongBookReferenceException(HttpStatus status, @Nullable String reason) {
        super("");
        Assert.notNull(status, "HttpStatus is required");
        this.status = status.value();
        this.reason = reason;
    }

    public WrongBookReferenceException(final int rawStatus, @Nullable final String reason) {
        super("");
        Assert.state(rawStatus != 0, "HttpStatus is required");
        this.status = rawStatus;
        this.reason = reason;
    }

    public HttpStatus getStatus() {
        return HttpStatus.valueOf(this.status);
    }

    public int getRawStatusCode() {
        return this.status;
    }

    @Nullable
    public String getReason() {
        return this.reason;
    }

    @Override
    public String getMessage() {
        HttpStatus code = HttpStatus.resolve(this.status);
        String msg = (code != null ? code : this.status) + (this.reason != null ? " \"" + this.reason + "\"" : "");
        return NestedExceptionUtils.buildMessage(msg, this.getCause());
    }
}

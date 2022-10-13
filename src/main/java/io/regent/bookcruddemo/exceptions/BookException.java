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


public class BookException extends NestedRuntimeException {
    private final int status;
    @Nullable
    private final String reason;

    public BookException(HttpStatus status, @Nullable String reason) {
        super("");
        Assert.notNull(status, "HttpStatus is required");
        this.status = status.value();
        this.reason = reason;
    }

    public BookException(final int rawStatus, @Nullable final String reason) {
        super("");
        Assert.state(rawStatus != 0, "HttpStatus is required");
        this.status = rawStatus;
        this.reason = reason;
    }

    public HttpStatus getStatus() {
        return HttpStatus.valueOf(this.getRawStatusCode());
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

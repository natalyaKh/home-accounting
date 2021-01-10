package smilyk.homeacc.exceptions;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExceptionResponse {

    private final String errorCode;
    private final String errorMessage;
    private final Date timeStamp;
    private final String stackTrace;
    private final List<FieldError> errors;
    private final Object[] params;

    public ExceptionResponse(final String errorCode, final String errorMessage, final Date timeStamp, final String stackTrace,
                             final List<FieldError> errors, final Object[] params) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.timeStamp = timeStamp;
        this.stackTrace = stackTrace;
        this.errors = errors;
        this.params = params;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return the errorMessage
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     * @return the timeStamp
     */
    public Date getTimeStamp() {
        return timeStamp;
    }

    /**
     * @return the stackTrace
     */
    public String getStackTrace() {
        return stackTrace;
    }

    /**
     * @return the errors
     */
    public List<FieldError> getErrors() {
        return errors;
    }

    public static ResponseBuilder code(final String code) {
        return new ResponseBuilder().code(code);
    }

    public static ResponseBuilder msg(final String msg) {
        return new ResponseBuilder().msg(msg);
    }

    public static ResponseBuilder time(final Date timestamp) {
        return new ResponseBuilder().time(timestamp);
    }

    public static ResponseBuilder stackTrace(final String stackTrace) {
        return new ResponseBuilder().stackTrace(stackTrace);
    }

    public static ResponseBuilder errors(final List<FieldError> errors) {
        return new ResponseBuilder().errors(errors);
    }

    public static ResponseBuilder error(final FieldError error) {
        return new ResponseBuilder().error(error);
    }

    public Object[] getParams() {
        return params;
    }

    public static class ResponseBuilder {
        private String errorCode = null;
        private String errorMessage = null;
        private Date timestamp = new Date();
        private String stackTrace = null;
        private Object[] params;
        private final List<FieldError> errors = new ArrayList<>();

        private ResponseBuilder() {
        }

        public ResponseBuilder code(final String code) {
            this.errorCode = code;
            return this;
        }

        public ResponseBuilder msg(final String msg) {
            this.errorMessage = msg;
            return this;
        }

        public ResponseBuilder time(final Date timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ResponseBuilder stackTrace(final String stackTrace) {
            this.stackTrace = stackTrace;
            return this;
        }

        public ResponseBuilder errors(final List<FieldError> errors) {
            if (errors == null) {
                return this;
            }
            this.errors.addAll(errors);
            return this;
        }

        public ResponseBuilder params(final Object[] params) {
            if (params == null) {
                return this;
            }
            this.setParams(params);
            return this;
        }

        public ResponseBuilder error(final FieldError error) {
            this.errors.add(error);
            return this;
        }

        public smilyk.homeacc.exceptions.ExceptionResponse get() {
            return new smilyk.homeacc.exceptions.ExceptionResponse(errorCode, errorMessage, timestamp, stackTrace, errors, params);
        }

        public Object[] getParams() {
            return params;
        }

        public void setParams(final Object[] params) {
            this.params = params;
        }

    }

}

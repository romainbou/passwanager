package org.isen.jee.project.harness;

public class WebRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int code;

    public WebRuntimeException(int code, String msg) {
        super(msg);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}

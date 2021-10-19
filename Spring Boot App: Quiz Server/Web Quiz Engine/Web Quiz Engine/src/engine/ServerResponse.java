package engine;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ServerResponse {
    SUCCES(true,"Congratulations, you're right!"),
    FAIL(false,"Wrong answer! Please, try again.");

    ServerResponse(boolean success,String feedback) {
        this.success = success;
        this.feedback = feedback;
    }


    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    private String feedback;

    private boolean success;



}

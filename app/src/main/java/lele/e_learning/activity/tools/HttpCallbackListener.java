package lele.e_learning.activity.tools;

public interface HttpCallbackListener {

    void onFinish(final String response);

    void onError(Exception e);

}
package comte.example.herve.baseapp.base.presenter;

/**
 * Created by Herve on 2016/10/10.
 */

public interface BasePresenterView {

    void setProgressVisibility(int visibility);

    void success();

    void error();
}

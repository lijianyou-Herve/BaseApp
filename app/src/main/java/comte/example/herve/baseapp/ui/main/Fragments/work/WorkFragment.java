package comte.example.herve.baseapp.ui.main.Fragments.work;


import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.BindView;
import comte.example.herve.baseapp.R;
import comte.example.herve.baseapp.base.view.MvpBaseFragment;
import comte.example.herve.baseapp.ui.main.Fragments.work.presenter.WorkContract;
import comte.example.herve.baseapp.ui.main.Fragments.work.presenter.WorkPresenter;

/**
 * Created           :Herve on 2016/10/10.
 *
 * @ Author          :Herve
 * @ e-mail          :lijianyou.herve@gmail.com
 * @ LastEdit        :2016/10/10
 * @ projectName     :SquareDemo
 * @ version
 */
public class WorkFragment extends MvpBaseFragment<WorkContract.Presenter> implements WorkContract.PresenterView {

    @BindView(R.id.progress)
    ProgressBar progress;

    public static WorkFragment newInstance() {
        WorkFragment fragment = new WorkFragment();
        return fragment;
    }

    public static WorkFragment newInstance(Bundle args) {
        WorkFragment fragment = new WorkFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void showDialog() {
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void dismissDialog() {
        progress.setVisibility(View.GONE);

    }

    @Override
    protected WorkContract.Presenter initPresenter() {
        return new WorkPresenter(this);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.fragment_work_layout;
    }

    @Override
    protected void findViewById() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mPresenter.loading();
    }

    @Override
    protected void initListener() {

    }

}

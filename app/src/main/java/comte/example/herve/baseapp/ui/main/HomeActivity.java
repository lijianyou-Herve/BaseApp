package comte.example.herve.baseapp.ui.main;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.MenuItem;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import comte.example.herve.baseapp.R;
import comte.example.herve.baseapp.base.view.BaseFragment;
import comte.example.herve.baseapp.base.view.MvpBaseActivity;
import comte.example.herve.baseapp.ui.main.Fragments.life.LifeFragment;
import comte.example.herve.baseapp.ui.main.adapter.FragmentsAdapter;
import comte.example.herve.baseapp.ui.main.presenter.MainConstant;
import comte.example.herve.baseapp.ui.main.presenter.MainPresenter;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomeActivity extends MvpBaseActivity<MainConstant.Presenter> implements MainConstant.PresenterView {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.vp_home)
    ViewPager vpHome;
    @BindView(R.id.bottom_view)
    BottomNavigationView bottomView;
    @BindView(R.id.activity_home)
    RelativeLayout activityHome;

    private FragmentsAdapter fragmentsAdapter;
    private String TAG = getClass().getSimpleName();
    private ProgressDialog progressDialog;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_home;
    }

    @Override
    protected void initView() {
        progressDialog = new ProgressDialog(mActivity);

    }

    @Override
    protected void initData() {
        mPresenter.loadData();

        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                if (!emitter.isDisposed()) {
                    Log.i(TAG, "subscribe: 没有取消");
                    emitter.onNext(true);
                    emitter.onComplete();
                } else {
                    Log.i(TAG, "subscribe: 被取消");
                }
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .as(bindLifecycle(Lifecycle.Event.ON_PAUSE))//AutoDispose
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        showDialog();
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                        Log.i(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                        Log.i(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        dismissDialog();
                        Log.i(TAG, "onComplete: ");
                    }
                });

    }

    @Override
    protected void initListener() {

        bottomView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Log.i(TAG, "onNavigationItemSelected:getOrder= " + item.getOrder());
                switch (item.getItemId()) {

                    case R.id.tab_01:
                        vpHome.setCurrentItem(0);
                        break;
                    case R.id.tab_02:
                        vpHome.setCurrentItem(1);
                        break;
                    case R.id.tab_03:
                        vpHome.setCurrentItem(2);
                        break;
                    default:
                        Log.w(TAG, "onNavigationItemSelected: you have not get the MenuItem id");
                        break;
                }
                return false;
            }
        });


    }

    @Override
    protected MainConstant.Presenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    public void getDate() {
        fragmentsAdapter = new FragmentsAdapter(getSupportFragmentManager(), mActivity);

        ArrayList<BaseFragment> data = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            BaseFragment baseFragment;
            if (i == 0) {
                baseFragment = new LifeFragment();
                baseFragment.setTittle("生活");

            } else {
                baseFragment = new LifeFragment();
                baseFragment.setTittle("工作");
            }
            data.add(baseFragment);
        }

        fragmentsAdapter.setData(data);

        vpHome.setOffscreenPageLimit(data.size());

        vpHome.setAdapter(fragmentsAdapter);

        tabLayout.setupWithViewPager(vpHome);

    }

    @Override
    public void showDialog() {
        progressDialog.show();
    }

    @Override
    public void dismissDialog() {
        progressDialog.dismiss();

    }
}

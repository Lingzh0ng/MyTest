package com.wearapay.brotherweather.ui.presenter;

import android.content.Context;
import com.wearapay.brotherweather.common.mvp.BasePresenter;
import com.wearapay.brotherweather.domain.GankioData;
import com.wearapay.brotherweather.domain.GankioType;
import com.wearapay.brotherweather.net.observer.ViewObserver;
import com.wearapay.brotherweather.rep.GankioRepository;
import com.wearapay.brotherweather.ui.view.IGankioView;
import io.reactivex.annotations.NonNull;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by lyz54 on 2017/6/28.
 */

public class GankioAllPresenter extends BasePresenter<IGankioView> {
  GankioRepository gankioRepository;

  @Inject public GankioAllPresenter(Context mContext, GankioRepository gankioRepository) {
    super(mContext);
    this.gankioRepository = gankioRepository;
  }

  public void getGankioData(GankioType type, int count, int page, boolean isProgress) {
    if (isProgress) {
      view.showProgress("");
    }
    wrap(gankioRepository.getAllGankioData(type, count, page)).subscribe(
        new ViewObserver<GankioData>(view) {
          @Override protected void onSuccess(List<GankioData> t) {
            view.display(t);
          }

          @Override public void onError(@NonNull Throwable e) {
            super.onError(e);
            view.displayError();
          }
        });
  }

  public void getGankioData(GankioType type, int count, int page) {
    getGankioData(type, count, page, true);
  }
}

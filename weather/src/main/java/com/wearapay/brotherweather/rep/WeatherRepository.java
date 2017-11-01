package com.wearapay.brotherweather.rep;

import com.wearapay.brotherweather.api.IWeatherRestService;
import com.wearapay.brotherweather.domain.WeatherInfo;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.ResponseBody;

/**
 * Created by lyz54 on 2017/6/27.
 */

public class WeatherRepository implements IWeatherRepository {

    private IWeatherRestService weatherRestService;

    @Inject
    public WeatherRepository(IWeatherRestService weatherRestService) {
        this.weatherRestService = weatherRestService;
    }

    @Override
    public Observable<WeatherInfo> getWeatherInfo(String city) {
        return weatherRestService.getWeatherInfo(city);
    }
}

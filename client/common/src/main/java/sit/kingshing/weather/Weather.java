package sit.kingshing.weather;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

public class Weather {
    private onLoadWeatherSuccess callback;

    public Weather(onLoadWeatherSuccess callback) {
        this.callback = callback;
    }

    static {
        HeConfig.init("HE1904172216481811", "a444952543444c37bfb17f2e903f8da7");
        HeConfig.switchToFreeServerNode();
    }

    /**
     * 实况天气
     * 实况天气即为当前时间点的天气状况以及温湿风压等气象指数，具体包含的数据：体感温度、
     * 实测温度、天气状况、风力、风速、风向、相对湿度、大气压强、降水量、能见度等。
     *
     * @param context  上下文
     * @param location 地址详解
     */
    public  void getWeatherNow(Context context, String location) {
        HeWeather.getWeatherNow(context, location, Lang.CHINESE_SIMPLIFIED, Unit.METRIC, new HeWeather.OnResultWeatherNowBeanListener() {
            @Override
            public void onError(Throwable e) {
                Log.i("Log", "onError: ", e);
            }

            @Override
            public void onSuccess(List<Now> list) {
                Log.e("Log", "onSuccess: " + new Gson().toJson(list));
                callback.onSuccess(list);
            }
        });
    }


    public interface onLoadWeatherSuccess {
        void onSuccess(List<Now> list);
    }

}

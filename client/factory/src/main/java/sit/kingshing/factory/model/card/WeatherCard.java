package sit.kingshing.factory.model.card;

import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import sit.kingshing.factory.Factory;

public class WeatherCard {
    private String adminArea ;
    private String location ;
    private String tmp ;
    private String condCode ;
    private String condTxt ;
    private int icWeatherPicture;

    public WeatherCard(Now now) {
        this.adminArea = now.getBasic().getAdmin_area();
        this.location = now.getBasic().getLocation();
        this.tmp = now.getNow().getTmp();
        this.condCode = now.getNow().getCond_code();
        this.condTxt = now.getNow().getCond_txt();
        this.icWeatherPicture = getIcWeatherPath();
    }

    private int getIcWeatherPath(){
        return  Factory.app().getResources().getIdentifier("ic_weather_" + condCode, "drawable", Factory.app().getPackageName());
    }

    public String getAdminArea() {
        return adminArea;
    }

    public String getLocation() {
        return location;
    }

    public String getTmp() {
        return tmp;
    }

    public String getCondCode() {
        return condCode;
    }

    public String getCondTxt() {
        return condTxt;
    }

    public int getIcWeatherPicture() {
        return icWeatherPicture;
    }
}

package site.pushy.weather.citymanage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import site.pushy.weather.R;
import site.pushy.weather.data.WeatherType;
import site.pushy.weather.data.weather.Forecast;
import site.pushy.weather.data.weather.Weather;
import site.pushy.weather.uitls.ToastUtil;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder>
        implements View.OnClickListener {

    private List<Weather> weatherList;
    private OnItemClickListener mOnItemClickListener;

    public CityListAdapter(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView county;
        TextView city;
        TextView province;
        TextView temp;
        ImageView ic;
        TextView quality; // 空气质量
        TextView humidity;  // 湿度
        TextView wind; // 风级
        TextView tempMin;
        TextView tempMax;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            county = itemView.findViewById(R.id.tv_city_manage_county);
            city = itemView.findViewById(R.id.tv_city_manage_city);
            province = itemView.findViewById(R.id.tv_city_manage_province);
            temp = itemView.findViewById(R.id.tv_city_manage_temp);
            ic = itemView.findViewById(R.id.tv_city_manage_ic);
            quality = itemView.findViewById(R.id.tv_city_manage_quality);
            humidity = itemView.findViewById(R.id.tv_city_manage_humidity);
            wind = itemView.findViewById(R.id.tv_city_manage_wind);
            tempMin = itemView.findViewById(R.id.tv_city_manage_temp_min);
            tempMax = itemView.findViewById(R.id.tv_city_manage_temp_max);
        }
    }

    /* 定义对外暴露的点击事件的接口 */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_city_manage, viewGroup, false);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Weather weather = weatherList.get(i);
        viewHolder.county.setText(weather.basic.location);
        viewHolder.city.setText(weather.basic.city + "，");
        viewHolder.province.setText(weather.basic.province);
        viewHolder.temp.setText(weather.now.temperature);
        viewHolder.ic.setImageResource(WeatherType.getWeatherICResource(weather.now.more.info));
        viewHolder.quality.setText(String.format("空气%s | ", weather.aqi.city.qlty));
        viewHolder.humidity.setText(String.format("湿度%s | ", weather.now.humidity));
        viewHolder.wind.setText(String.format("%s%s级", weather.now.windDir, weather.now.windSc));

        Forecast forecast = weather.forecastList.get(0);
        viewHolder.tempMax.setText(String.format(" / %s℃", forecast.temperature.min));
        viewHolder.tempMin.setText(forecast.temperature.max);

        viewHolder.itemView.setTag(i);  // 为itemView设置tag
    }

    @Override
    public int getItemCount() {
        if (weatherList.isEmpty()) {
            return 0;
        }
        return weatherList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }


}

package com.Jsu.framework.widget.wheel;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.Jsu.framework.R;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 省市区滚轮控件
 * Created by sue on 2015/10/11.
 */
public class CityPickerDialog extends BaseDialog {

    private NumberPicker province, city, area;
    private CitycodeUtil citycodeUtil;
    private String[] str_province;
    private String[] str_city;
    private String[] str_area;
    private List<Cityinfo> mProvince;
    private HashMap<String, List<Cityinfo>> mCity;
    private HashMap<String, List<Cityinfo>> mArea;
    private int pPosition = 0;
    private int cPosition = 0;
    private int aPosition = 0;
    private Context mContext;
    private boolean isThree;
    private OnSelectListener onSelectListener;

    /**
     * @param context 上下文
     */
    public CityPickerDialog(Context context, String currentPosition, final boolean isThree) {
        super(context, R.layout.wheel_picker);
        mContext = context;
        this.isThree = isThree;
        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isThree){
                    Toast.makeText(mContext, str_province[pPosition] + " " + str_city[cPosition] + " " + str_area[aPosition], Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(mContext, str_province[pPosition] + " " + str_city[cPosition], Toast.LENGTH_LONG).show();
                }
            }
        });
        citycodeUtil = CitycodeUtil.getSingleton();
        if (getaddressinfo()) {
            init();
            getData(mProvince, mCity, mArea, currentPosition);
        }
    }

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }

    private void getData(List<Cityinfo> province_list, HashMap<String, List<Cityinfo>> city_map, HashMap<String, List<Cityinfo>> area_map, String currentPosition) {
        String[] getPosition = currentPosition.split(" ");
        if (province_list.size() > 0) {
            List<String> province = new ArrayList<String>();
            province = citycodeUtil.getProvince(province_list);
            this.str_province = new String[province.size()];
            for (int i = 0; i < province.size(); i++) {
                if (getPosition[0] != null && getPosition[0].equals(province.get(i))) {
                    pPosition = i;
                }
                this.str_province[i] = province.get(i);
            }
            setData(this.province, str_province, pPosition);
        }
        if (city_map.size() > 0) {
            if (pPosition == 0) {
                str_city = citycodeUtil.getCity(city_map, citycodeUtil.getProvince_list_code().get(0));
            } else {
                str_city = citycodeUtil.getCity(city_map, citycodeUtil.getProvince_list_code().get(pPosition));
            }
            for (int j = 0; j < str_city.length; j++) {
                if (getPosition[1] != null && getPosition[1].equals(str_city[j])) {
                    cPosition = j;
                }
            }
            setData(this.city, str_city, cPosition);
        }
        if (area_map.size() > 0) {
            if (cPosition == 0) {
                str_area = citycodeUtil.getCouny(area_map, citycodeUtil.getCity_list_code().get(0));
            } else {
                str_area = citycodeUtil.getCouny(area_map, citycodeUtil.getCity_list_code().get(cPosition));
            }
            for (int k = 0; k < str_area.length; k++) {
                if(getPosition.length > 2){
                    if (getPosition[2] != null && getPosition[2].equals(str_area[k])) {
                        aPosition = k;
                    }
                }
            }
            setData(this.area, str_area, aPosition);
        }

    }

    private void init() {
        province = (NumberPicker) findViewById(R.id.left);
        province.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                pPosition = newVal;
                cPosition = 0;
                aPosition = 0;
                str_city = citycodeUtil.getCity(mCity, citycodeUtil.getProvince_list_code().get(newVal));
                str_area = citycodeUtil.getCouny(mArea, citycodeUtil.getCity_list_code().get(0));
                setData(city, str_city, cPosition);
                setData(area, str_area, aPosition);
            }
        });
        city = (NumberPicker) findViewById(R.id.middle);
        city.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                cPosition = newVal;
                aPosition = 0;
                str_area = citycodeUtil.getCouny(mArea, citycodeUtil.getCity_list_code().get(newVal));
                setData(area, str_area, aPosition);
            }
        });
        area = (NumberPicker) findViewById(R.id.right);
        if (!isThree) {
            area.setVisibility(View.GONE);
        }
        area.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                aPosition = newVal;
            }
        });
    }

    private void setData(NumberPicker numberPickers, String[] data, int position) {
        numberPickers.setMinValue(0);
        numberPickers.setDisplayedValues(null);
        numberPickers.setMaxValue(data.length - 1);
        numberPickers.setDisplayedValues(data);
        numberPickers.setValue(position);
        numberPickers.setWrapSelectorWheel(true);
        numberPickers.setFocusableInTouchMode(true);
        numberPickers.setFocusable(true);
        if (str_province != null && str_city != null && str_area != null) {
        }
    }

    @Override
    protected int dialogAnimation() {
        return R.style.AnimationBottomDialog;
    }

    @Override
    protected int dialogGravity() {
        return Gravity.BOTTOM;
    }


    @Override
    protected int dialogWidth() {
        DisplayMetrics metric = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.widthPixels;
    }

    // 获取城市信息
    private boolean getaddressinfo() {
        // TODO Auto-generated method stub
        // 读取城市信息string
        JSONParser parser = new JSONParser();
        String area_str = FileUtil.readAssets(mContext, "area.json");
        this.mProvince = parser.getJSONParserResult(area_str, "area0");
        this.mCity = parser.getJSONParserResultArray(area_str, "area1");
        this.mArea = parser.getJSONParserResultArray(area_str, "area2");
        if (this.mProvince != null && this.mCity != null && this.mArea != null) {
            return true;
        }
        return false;
    }

    public static class JSONParser {
        public ArrayList<String> province_list_code = new ArrayList<String>();
        public ArrayList<String> city_list_code = new ArrayList<String>();

        public List<Cityinfo> getJSONParserResult(String JSONString, String key) {
            List<Cityinfo> list = new ArrayList<Cityinfo>();
            JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);

            Iterator iterator = result.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) iterator.next();
                Cityinfo cityinfo = new Cityinfo();

                cityinfo.setCity_name(entry.getValue().getAsString());
                cityinfo.setId(entry.getKey());
                province_list_code.add(entry.getKey());
                list.add(cityinfo);
            }
            System.out.println(province_list_code.size());
            return list;
        }

        public HashMap<String, List<Cityinfo>> getJSONParserResultArray(String JSONString, String key) {
            HashMap<String, List<Cityinfo>> hashMap = new HashMap<String, List<Cityinfo>>();
            JsonObject result = new JsonParser().parse(JSONString).getAsJsonObject().getAsJsonObject(key);

            Iterator iterator = result.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = (Map.Entry<String, JsonElement>) iterator.next();
                List<Cityinfo> list = new ArrayList<Cityinfo>();
                JsonArray array = entry.getValue().getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    Cityinfo cityinfo = new Cityinfo();
                    cityinfo.setCity_name(array.get(i).getAsJsonArray().get(0).getAsString());
                    cityinfo.setId(array.get(i).getAsJsonArray().get(1).getAsString());
                    city_list_code.add(array.get(i).getAsJsonArray().get(1).getAsString());
                    list.add(cityinfo);
                }
                hashMap.put(entry.getKey(), list);
            }
            return hashMap;
        }
    }
}

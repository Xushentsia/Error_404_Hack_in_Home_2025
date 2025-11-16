package com.zest.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DemoListActivity extends AppCompatActivity {

    private List<AppItem> apps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo_list);

        // Загружаем приложения из JSON
        apps = loadAppsFromJson();

        // Настраиваем шапку
        setupHeader();

        // Показываем демо-приложения
        setupDemoApps();
    }

    private void setupHeader() {
        // Кнопка назад
        ImageView btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());
    }

    private void setupDemoApps() {
        // Здесь будем добавлять демо-приложения динамически
        // Пока просто используем существующие из JSON
        AppItem demoApp = findAppById("vk_video");
        if (demoApp != null) {
            setupDemoApp1(demoApp);
            setupDemoApp2(demoApp); // Дублируем для примера
        }
    }

    private void setupDemoApp1(AppItem app) {
        ImageView ivScreenshot = findViewById(R.id.demo_screenshot_1);
        TextView tvName = findViewById(R.id.tv_demo_name_1);
        TextView tvDesc = findViewById(R.id.tv_demo_desc_1);
        TextView tvRating = findViewById(R.id.tv_demo_rating_1);

        ivScreenshot.setImageResource(getDrawableId(app.icon));
        tvName.setText(app.name);
        tvDesc.setText(app.description);
        tvRating.setText(app.rating);
    }

    private void setupDemoApp2(AppItem app) {
        ImageView ivScreenshot = findViewById(R.id.demo_screenshot_2);
        TextView tvName = findViewById(R.id.tv_demo_name_2);
        TextView tvDesc = findViewById(R.id.tv_demo_desc_2);
        TextView tvRating = findViewById(R.id.tv_demo_rating_2);

        ivScreenshot.setImageResource(getDrawableId(app.icon));
        tvName.setText(app.name + " 2");
        tvDesc.setText(app.description);
        tvRating.setText(app.rating);
    }

    private AppItem findAppById(String id) {
        for (AppItem app : apps) {
            if (app.id != null && app.id.equals(id)) {
                return app;
            }
        }
        return null;
    }

    private int getDrawableId(String drawableName) {
        return getResources().getIdentifier(drawableName, "drawable", getPackageName());
    }

    private List<AppItem> loadAppsFromJson() {
        try {
            InputStreamReader reader = new InputStreamReader(getAssets().open("apps.json"));
            Gson gson = new Gson();
            Type type = new TypeToken<List<AppItem>>(){}.getType();
            return gson.fromJson(reader, type);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
package com.zest.app;

import android.content.Intent;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StoreActivity extends AppCompatActivity {

    private List<AppItem> apps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        // Загружаем приложения из JSON
        apps = loadAppsFromJson();

        // Настраиваем весь интерфейс из JSON
        setupGreeting();
        setupActualApps();
        setupPodborApps();
        setupDemoApp();
        //setupAllApps();
        setupDemoAllButton();
        setupFilterButtons();
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

    private void setupGreeting() {
        TextView tvGreeting = findViewById(R.id.tv_greeting);
        tvGreeting.setText(getGreeting());
    }

    private void setupActualApps() {
        // Находим приложения по ID
        AppItem zaryadkaApp = findAppById("zaryadka");
        AppItem meditaciyaApp = findAppById("meditaciya");

        // Устанавливаем зарядку
        if (zaryadkaApp != null) {
            ImageView ivZaryadka = findViewById(R.id.iv_zaryadka);
            TextView tvZaryadka = findViewById(R.id.tv_zaryadka);
            ivZaryadka.setImageResource(getDrawableId(zaryadkaApp.icon));
            tvZaryadka.setText(zaryadkaApp.name);
        }

        // Устанавливаем медитацию
        if (meditaciyaApp != null) {
            ImageView ivMeditaciya = findViewById(R.id.iv_meditaciya);
            TextView tvMeditaciya = findViewById(R.id.tv_meditaciya);
            ivMeditaciya.setImageResource(getDrawableId(meditaciyaApp.icon));
            tvMeditaciya.setText(meditaciyaApp.name);
        }
    }

    private void setupPodborApps() {
        int[] ivIds = {R.id.iv_podbor_1, R.id.iv_podbor_2, R.id.iv_podbor_3, R.id.iv_podbor_4};
        String[] appIds = {"podbor_1", "podbor_2", "podbor_3", "podbor_4"};

        for (int i = 0; i < appIds.length; i++) {
            AppItem app = findAppById(appIds[i]);
            if (app != null) {
                ImageView iv = findViewById(ivIds[i]);
                iv.setImageResource(getDrawableId(app.icon));

                // Также можно установить другие данные если нужно
                // Например, для accessibility
                iv.setContentDescription(app.name);
            }
        }
    }

    private void setupDemoAllButton() {
        TextView tvAllDemo = findViewById(R.id.tv_all_demo);
        tvAllDemo.setOnClickListener(v -> {
            Intent intent = new Intent(StoreActivity.this, DemoListActivity.class);
            startActivity(intent);
        });
    }

    /*private void setupAllApps() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_apps);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppAdapter adapter = new AppAdapter(apps); // Все приложения из JSON
        recyclerView.setAdapter(adapter);
    }*/

    private void setupDemoApp() {
        AppItem demoApp = findAppById("vk_video");
        if (demoApp != null) {
            // Устанавливаем скриншот
            ImageView ivScreenshot = findViewById(R.id.demo_screenshot);
            ivScreenshot.setImageResource(getDrawableId(demoApp.icon));

            // Устанавливаем название
            TextView tvName = findViewById(R.id.tv_demo_name);
            tvName.setText(demoApp.name);

            // Устанавливаем описание
            TextView tvDesc = findViewById(R.id.tv_demo_desc);
            tvDesc.setText(demoApp.description);

            // Устанавливаем рейтинг
            TextView tvRating = findViewById(R.id.tv_demo_rating);
            if (tvRating != null && demoApp.rating != null) {
                tvRating.setText(demoApp.rating);
            }
        }
    }

    private void setupFilterButtons() {
        // Если нужно, можно также загружать тексты кнопок фильтров из JSON
        // Например, создать отдельный массив в JSON с категориями
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

    private String getGreeting() {
        int h = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        if (h >= 5 && h < 12) return "Доброе утро!";
        if (h < 17) return "Хорошего дня!";
        if (h < 22) return "Приятного вечера!";
        return "Спокойной ночи!";
    }
}
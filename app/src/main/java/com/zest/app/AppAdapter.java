package com.zest.app;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AppAdapter extends RecyclerView.Adapter<AppAdapter.ViewHolder> {
    private List<AppItem> apps;

    public AppAdapter(List<AppItem> apps) {
        this.apps = apps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_app, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        AppItem app = apps.get(position);

        // Устанавливаем данные из JSON
        holder.name.setText(app.name);
        holder.description.setText(app.description);
        holder.rating.setText(app.rating);

        // Загружаем иконку по имени из JSON
        int drawableId = holder.itemView.getContext().getResources()
                .getIdentifier(app.icon, "drawable", holder.itemView.getContext().getPackageName());
        holder.icon.setImageResource(drawableId);

        // Пока скроем теги, т.к. их нет в JSON (можно добавить позже)
        holder.tagNew.setVisibility(View.GONE);
        holder.tagEditor.setVisibility(View.GONE);

        holder.btnInstall.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Установка: " + app.name, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return apps.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView name, description, rating, tagNew, tagEditor;
        ImageView icon;
        Button btnInstall;

        public ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.app_name);
            description = itemView.findViewById(R.id.app_description);
            rating = itemView.findViewById(R.id.app_rating);
            icon = itemView.findViewById(R.id.app_icon);
            tagNew = itemView.findViewById(R.id.tag_new);
            tagEditor = itemView.findViewById(R.id.tag_editor);
            btnInstall = itemView.findViewById(R.id.btn_install);
        }
    }
}
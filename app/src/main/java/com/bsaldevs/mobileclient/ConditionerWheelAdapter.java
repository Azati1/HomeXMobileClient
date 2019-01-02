package com.bsaldevs.mobileclient;

import android.content.Context;
import android.support.v4.app.ActivityCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.List;

import github.hellocsl.cursorwheel.CursorWheelLayout;

public class ConditionerWheelAdapter extends CursorWheelLayout.CycleWheelAdapter {

    private Context context;
    private List<MenuItemData> menuItems;
    private LayoutInflater inflater;
    private int gravity;

    public ConditionerWheelAdapter(Context context, List<MenuItemData> menuItems) {
        this.context = context;
        this.menuItems = menuItems;
        this.gravity = Gravity.CENTER;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return menuItems.size();
    }

    @Override
    public View getView(View parent, int position) {
        MenuItemData itemData = menuItems.get(position);
        View root = inflater.inflate(R.layout.wheel_text_layout, null, false);
        TextView textView = root.findViewById(R.id.wheel_menu_item_text);
        textView.setVisibility(View.VISIBLE);
        textView.setText(itemData.getTitle());
        ((FrameLayout.LayoutParams) textView.getLayoutParams()).gravity = gravity;

        return root;
    }

    @Override
    public Object getItem(int position) {
        return menuItems.get(position);
    }
}


package com.mando.hvacpro;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends Activity {

    private int neonBlue = Color.rgb(0, 220, 255);
    private int backgroundColor = Color.rgb(10, 14, 28);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setStatusBarColor(backgroundColor);
        getWindow().setNavigationBarColor(backgroundColor);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(24, 40, 24, 24);
        root.setBackgroundColor(backgroundColor);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setFillViewport(true);

        LinearLayout content = new LinearLayout(this);
        content.setOrientation(LinearLayout.VERTICAL);
        content.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView logo = new TextView(this);
        logo.setText("MANDO");
        logo.setTextColor(neonBlue);
        logo.setTextSize(34);
        logo.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        logo.setGravity(Gravity.CENTER);
        content.addView(logo);

        TextView title = new TextView(this);
        title.setText("HVAC PRO");
        title.setTextColor(Color.WHITE);
        title.setTextSize(25);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);
        title.setPadding(0, 8, 0, 12);
        content.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("دليلك الفني للتبريد والتكييف");
        subtitle.setTextColor(Color.LTGRAY);
        subtitle.setTextSize(18);
        subtitle.setGravity(Gravity.CENTER);
        subtitle.setPadding(0, 0, 0, 28);
        content.addView(subtitle);

        addSection(content, "📘", "أساسيات التبريد والتكييف",
                "دورة التبريد ومكونات الدائرة");
        addSection(content, "❄️", "أجهزة التكييف",
                "السبليت والشباك والإنفرتر");
        addSection(content, "🔧", "الثلاجات والديب فريزر",
                "المكونات والأعطال الشائعة");
        addSection(content, "⚡", "الكهرباء والأعطال",
                "الريلاي والأوفرلود والمكثفات");
        addSection(content, "📐", "القوانين والحسابات",
                "معلومات أساسية للفني");

        TextView footer = new TextView(this);
        footer.setText("MANDO HVAC PRO  •  الإصدار 1.0");
        footer.setTextColor(neonBlue);
        footer.setTextSize(12);
        footer.setGravity(Gravity.CENTER);
        footer.setPadding(0, 28, 0, 12);
        content.addView(footer);

        scrollView.addView(content);
        root.addView(scrollView,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        0, 1));

        setContentView(root);
    }

    private void addSection(LinearLayout parent, String icon,
                            String heading, String description) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 16, 18, 16);
        card.setBackgroundColor(Color.rgb(22, 30, 49));

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, 14);

        TextView title = new TextView(this);
        title.setText(icon + "  " + heading);
        title.setTextColor(neonBlue);
        title.setTextSize(18);
        title.setTypeface(Typeface.DEFAULT, Typeface.BOLD);

        TextView detail = new TextView(this);
        detail.setText(description);
        detail.setTextColor(Color.WHITE);
        detail.setTextSize(14);
        detail.setPadding(0, 8, 0, 0);

        card.addView(title);
        card.addView(detail);
        parent.addView(card, params);
    }
}
  

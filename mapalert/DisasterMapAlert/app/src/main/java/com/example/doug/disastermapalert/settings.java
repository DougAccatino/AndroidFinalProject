package com.example.doug.disastermapalert;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;

public class settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);

        setContentView(R.layout.activity_settings);

        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.fab));

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();


        //SubActionButton.Builder itemBuilder2 = new SubActionButton.Builder(this);
        int subActionButtonSize = 150;

        SubActionButton.Builder itemBuilder = new SubActionButton.Builder(settings.this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(subActionButtonSize, subActionButtonSize);
        itemBuilder.setLayoutParams(params);


        //button 1
        ImageView itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.warning_float));
        SubActionButton button1 = itemBuilder.setContentView(itemIcon).build();
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(settings.this, "Hazzards", Toast.LENGTH_SHORT).show();
            }
        });

        //button 2
        itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.home_float));
        SubActionButton button2 = itemBuilder.setContentView(itemIcon).build();
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(settings.this, "Home", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(settings.this, HOME.class);
                startActivity(i);
            }
        });

        //button 3
        itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.settings_float));
        SubActionButton button3 = itemBuilder.setContentView(itemIcon).build();
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(settings.this, "You're in settings", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(settings.this, settings.class);
//                startActivity(i);
            }
        });


        //button 4
        itemIcon = new ImageView(this);
        itemIcon.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.phone_float));
        SubActionButton button4 = itemBuilder.setContentView(itemIcon).build();
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(settings.this, "Contacts", Toast.LENGTH_SHORT).show();
            }
        });


        //add everything now
        FloatingActionMenu actionMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(button1)
                .addSubActionView(button2)
                .addSubActionView(button3)
                .addSubActionView(button4)


                // ...
                .attachTo(actionButton)
                .build();

    }

}

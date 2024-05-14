package com.example.android.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import com.example.android.bean.entity.ReaderPage;
import com.example.no_teacher_andorid.R;

public class ReadActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        ReaderPage readerPage = findViewById(R.id.readerPage);

        String content = "Lead poisoning has proved more difficult to deal with. When condors eat dead bodies of other animals containing lead, they absorb large quantities of lead. This affects their nervous systems and ability to produce baby birds, and can lead to kidney(肾) failures and death. So condors with high levels of lead are sent to Los Angeles Zoo, where they are treated with calcium EDTA, a chemical that removes lead from the blood over several days. This work is starting to pay off. The annual death rate for adult condors has dropped from 38% in 2000 to 5.4% in 2011." +
                "Rideout’s team thinks that the California condors’ average survival time in the wild is now just under eight years. “Although these measures are not effective forever, they are vital for now,” he says. “They are truly good birds that are worth every effort we put into recovering them.";
        readerPage.setContent(content);
    }
}
package com.example.android.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.bean.entity.ExampleItem;
import com.example.no_teacher_andorid.R;

import java.util.List;

/**
 * @Auther : Tcy
 * @Date : Create in 2024/5/17 14:47
 * @Decription:
 */
// ExamplesAdapter.java
public class ExamplesAdapter extends ArrayAdapter<ExampleItem> {

    public ExamplesAdapter(Context context, List<ExampleItem> examples) {
        super(context, 0, examples);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ExampleItem exampleItem = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_example, parent, false);
        }

        TextView partOfSpeechTextView = convertView.findViewById(R.id.text_view_part_of_speech);
        TextView definitionTextView = convertView.findViewById(R.id.text_view_definition);
        TextView exampleTextView = convertView.findViewById(R.id.text_view_example);
        TextView translatedExampleTextView = convertView.findViewById(R.id.text_view_translated_example); // 新增显示翻译后的例句的 TextView

        partOfSpeechTextView.setText(exampleItem.getPartOfSpeech());
        definitionTextView.setText(exampleItem.getDefinition());
        exampleTextView.setText(exampleItem.getExample());
        translatedExampleTextView.setText(exampleItem.getTranslatedExample()); // 设置翻译后的例句

        return convertView;
    }
}

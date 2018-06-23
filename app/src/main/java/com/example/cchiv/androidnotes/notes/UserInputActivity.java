package com.example.cchiv.androidnotes.notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.cchiv.androidnotes.R;
import com.example.cchiv.androidnotes.utilities.ComponentRender;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;

public class UserInputActivity extends AppCompatActivity {

    private String component;
    private String demo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_component);

        this.component = getIntent().getStringExtra("className");
        this.demo = getIntent().getStringExtra("snippetName");

        ComponentRender componentRender = new ComponentRender(this, R.id.component_list, this.component, this.demo);
        updateUI();
    }

    public void updateUI() {
        final EditText editText = (EditText) findViewById(R.id.edit_text_input);
        final TextView editTextValue = (TextView) findViewById(R.id.edit_text_value);
        ((Button) findViewById(R.id.edit_text_submit))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        editTextValue.setText(editText.getEditableText());
                    }
                });

        final ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        LinearLayout checkboxContainer = (LinearLayout) findViewById(R.id.checkbox_container);

        for(int g = 0; g < checkboxContainer.getChildCount(); g++) {
            checkBoxes.add((CheckBox) checkboxContainer.getChildAt(g));
        }

        final TextView checkboxValue = (TextView) findViewById(R.id.checkbox_value);
        ((Button) findViewById(R.id.checkbox_submit))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        StringBuilder message = new StringBuilder();

                        for(CheckBox checkbox: checkBoxes) {
                            if(checkbox.isChecked()) {
                                message.append(checkbox.getText().toString() + " + ");
                            }
                        }

                        if(message.length() > 0) {
                            message.replace(message.length()-2, message.length()-1,  "=");
                            message.append("Chocolate");

                            checkboxValue.setText(message.toString());
                        } else {
                            checkboxValue.setText("Void");
                        }
                    }
                });

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group_value);
        final TextView radioValue = (TextView) findViewById(R.id.radio_value);
        ((Button) findViewById(R.id.radio_submit))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String value = ((RadioButton) findViewById(
                                radioGroup.getCheckedRadioButtonId())).getText().toString();

                        radioValue.setText(value);
                    }
                });

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("AutoCompleteTextView");
        arrayList.add("ToggleButton");
        arrayList.add("ProgressBar");
        arrayList.add("Spinner");
        arrayList.add("TimePicker");
        arrayList.add("SeekBar");
        arrayList.add("AlertDialog");
        arrayList.add("Switch");
        arrayList.add("RatingBar");

        FlowLayout componentsContainer = (FlowLayout) findViewById(R.id.components_container);
        for(String missedNote : arrayList) {
            View componentView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.badge_layout, componentsContainer, false);

            ((TextView) componentView.findViewById(R.id.info_text)).setText(missedNote);

            componentsContainer.addView(componentView);
        }
    }
}

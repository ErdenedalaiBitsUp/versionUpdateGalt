package mn.example.galttereg.view.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;

import mn.example.galttereg.R;

public class DataCell extends ConstraintLayout {

    private TextView headerTextView;
    private TextView valueTextView;
    private RelativeLayout layoutItem;
    private boolean hideValueTextWhenEmpty = true;

    public DataCell(Context context) {
        this(context, null);
    }

    public DataCell(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        View inflatedView = inflate(context, R.layout.view_data_cell, this);
        layoutItem = inflatedView.findViewById(R.id.layoutItem);
        headerTextView = inflatedView.findViewById(R.id.textHeader);
        valueTextView = inflatedView.findViewById(R.id.textValue);
        initValues(context, attrs);
    }

    private void initValues(Context context, @Nullable AttributeSet attrs) {

        TypedArray styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.DataCell, 0, 0);
        String headerText = StyledAttributesHelper.getStyleableAttributeString(styledAttributes, R.styleable.DataCell_headerText, "-");
        String valueText = StyledAttributesHelper.getStyleableAttributeString(styledAttributes, R.styleable.DataCell_valueText, "-");
        styledAttributes.recycle();

        setTextViewValue(headerTextView, headerText);
        setTextViewValue(valueTextView, valueText);
    }

    private void setTextViewValue(TextView textView, String value) {
        if ((value == null || value.isEmpty()) && hideValueTextWhenEmpty) {
            textView.setVisibility(GONE);
        } else {
            textView.setVisibility(VISIBLE);
            textView.setText(value);
        }
    }

    private void setTextViewValue(TextView textView, SpannableStringBuilder value) {
        if ((value == null || value.length() == 0) && hideValueTextWhenEmpty) {
            textView.setVisibility(GONE);
        } else {
            textView.setVisibility(VISIBLE);
            textView.setText(value);
        }
    }

    public void setHeaderText(String text) {
        setTextViewValue(headerTextView, text);
    }

    public void setValueTextSize(int size) {
        valueTextView.setTextSize(size);
    }

    public void setTextStyle(Typeface style) {
        valueTextView.setTypeface(style);
    }

    public void setValueText(String text) {
        setTextViewValue(valueTextView, text);
    }

    public void setValueText(SpannableStringBuilder text) {
        setTextViewValue(valueTextView, text);
    }

    public String getHeaderText() {
        return headerTextView.getText().toString();
    }

    public String getValueText() {
        return valueTextView.getText().toString();
    }

    public void setValueTextBold() {
        Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.rubik_medium);
        valueTextView.setTypeface(typeface);
    }


    public void setBackground(int resId) {
        layoutItem.setBackgroundResource(resId);
    }
}
package com.aotuman.studydemo.ellispetextview;

import android.text.Layout;
import android.text.StaticLayout;
import android.widget.TextView;

public class Utils {

    public static StaticLayout createStaticLayout(String text, TextView textView) {
        StaticLayout staticLayout;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            final StaticLayout.Builder layoutBuilder = StaticLayout.Builder.obtain(
                    text, 0, text.length(),  textView.getPaint(), Math.round(textView.getWidth()));
            staticLayout = layoutBuilder.build();
        } else {
            staticLayout = new StaticLayout(text, textView.getPaint(),
                    textView.getWidth() - textView.getPaddingLeft() - textView.getPaddingRight(),
                    Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, false);
        }
        return staticLayout;
    }

    public static String getRealText(StaticLayout layout, TextView textView,
                              String fullText, String endText, int maxLines) {
        int originalLineCount = layout.getLineCount();
        String workingText = fullText;
        if (originalLineCount > maxLines) {
            workingText = fullText.substring(0, layout.getLineEnd(maxLines - 1)).trim();
            while (createStaticLayout(workingText + "... " + endText, textView).getLineCount() > maxLines) {
                int lastSpace = workingText.lastIndexOf(' ');
                if (lastSpace == -1) {
                    workingText = workingText.substring(0, workingText.length() - 1);
                } else {
                    workingText = workingText.substring(0, lastSpace);
                }
            }
            workingText = workingText + "... " + endText;
        }
        return workingText;
    }

}

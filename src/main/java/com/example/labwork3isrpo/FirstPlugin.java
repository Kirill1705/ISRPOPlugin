package com.example.labwork3isrpo;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.ui.Messages;
import com.intellij.ui.EditorTextField;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class FirstPlugin extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Editor editor = e.getRequiredData(CommonDataKeys.EDITOR);
        Document document = editor.getDocument();
        String text = document.getText();
        String result = replaceText(text);
        document.replaceString(0, text.length(), result);
    }
    public static int log(long number) {
        int count = 1;
        long current = 2;
        while (current<number) {
            count++;
            current = current<<1;
        }
        if (current==number) {
            return count;
        }
        else {
            return -1;
        }
    }
    public static String replaceText(String text) {
        StringBuilder builder = new StringBuilder(text);
        HashMap<Character, String> map = new HashMap<>();
        map.put('*', "<<");
        map.put('/', ">>");
        map.put('%', "&");
        for (int i = 0; i < builder.length(); i++) {
            char c = builder.charAt(i);
            if (map.containsKey(c)) {
                int idx = i+1;
                while (idx<builder.length()&&builder.charAt(idx)>='0'&&builder.charAt(idx)<='9') {
                    idx++;
                }
                if (idx-i>1) {
                    long number = Long.parseLong(builder.substring(i+1, idx));
                    int e = log(number);
                    if (e>0) {
                        if (c!='%') {
                            builder.replace(i+1, idx, String.valueOf(e));
                        }
                        else {
                            builder.replace(i+1, idx, String.valueOf(number-1));
                        }
                        builder.replace(i, i+1, map.get(c));
                    }
                }
            }
        }
        return builder.toString();
    }
}
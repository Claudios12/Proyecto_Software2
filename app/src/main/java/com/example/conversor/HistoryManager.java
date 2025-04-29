package com.example.conversor;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HistoryManager {
    private static final String PREFS_NAME = "conversion_prefs";
    private static final String KEY_HISTORY = "key_history";

    private static SharedPreferences prefs;
    private static final List<String> historyList = new ArrayList<>();

    public static void init(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        loadHistory();
    }

    public static void addHistory(String entry) {
        historyList.add(0, entry);
        if (historyList.size() > 20) {
            historyList.remove(historyList.size() - 1);
        }
        saveHistory();
    }

    public static List<String> getHistory() {
        return new ArrayList<>(historyList);
    }

    public static void clearHistory() {
        historyList.clear();
        saveHistory();
    }

    private static void saveHistory() {
        Set<String> set = new HashSet<>(historyList);
        prefs.edit().putStringSet(KEY_HISTORY, set).apply();
    }

    private static void loadHistory() {
        Set<String> set = prefs.getStringSet(KEY_HISTORY, new HashSet<>());
        historyList.clear();
        if (set != null) {
            historyList.addAll(set);
            Collections.sort(historyList, Collections.reverseOrder());
        }
    }
}

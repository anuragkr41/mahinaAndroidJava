package com.esi.mahina.Settings;

public class GeneralSettings implements Notifications, Language{

    @Override
    public boolean isNotificationAllowed() {
        return false;
    }

    @Override
    public String preferredLanguage(String lang) {
        return null;
    }
}

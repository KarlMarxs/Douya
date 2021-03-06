/*
 * Copyright (c) 2015 Zhang Hai <Dreaming.in.Code.ZH@Gmail.com>
 * All Rights Reserved.
 */

package me.zhanghai.android.douya.settings.info;

import android.support.annotation.ArrayRes;
import android.support.annotation.BoolRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.StringRes;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import me.zhanghai.android.douya.DouyaApplication;
import me.zhanghai.android.douya.util.LogUtils;
import me.zhanghai.android.douya.util.SharedPrefsUtils;

public interface SettingsEntries {

    class StringSettingsEntry extends SettingsEntry<String> {

        public StringSettingsEntry(@StringRes int keyResId, @StringRes int defaultValueResId) {
            super(keyResId, defaultValueResId);
        }

        @Override
        public String getDefaultValue() {
            return DouyaApplication.getInstance().getString(getDefaultValueResId());
        }

        @Override
        public String getValue() {
            return SharedPrefsUtils.getString(this);
        }

        @Override
        public void putValue(String value) {
            SharedPrefsUtils.putString(this, value);
        }
    }

    class StringSetSettingsEntry extends SettingsEntry<Set<String>> {

        public StringSetSettingsEntry(@StringRes int keyResId, @ArrayRes int defaultValueResId) {
            super(keyResId, defaultValueResId);
        }

        @Override
        public Set<String> getDefaultValue() {
            Set<String> stringSet = new HashSet<>();
            Collections.addAll(stringSet, DouyaApplication.getInstance().getResources()
                    .getStringArray(getDefaultValueResId()));
            return stringSet;
        }

        @Override
        public Set<String> getValue() {
            return SharedPrefsUtils.getStringSet(this);
        }

        @Override
        public void putValue(Set<String> value) {
            SharedPrefsUtils.putStringSet(this, value);
        }
    }

    class IntegerSettingsEntry extends SettingsEntry<Integer> {

        public IntegerSettingsEntry(@StringRes int keyResId, @IntegerRes int defaultValueResId) {
            super(keyResId, defaultValueResId);
        }

        @Override
        public Integer getDefaultValue() {
            return DouyaApplication.getInstance().getResources().getInteger(getDefaultValueResId());
        }

        @Override
        public Integer getValue() {
            return SharedPrefsUtils.getInt(this);
        }

        @Override
        public void putValue(Integer value) {
            SharedPrefsUtils.putInt(this, value);
        }
    }

    class LongSettingsEntry extends SettingsEntry<Long> {

        public LongSettingsEntry(@StringRes int keyResId, @StringRes int defaultValueResId) {
            super(keyResId, defaultValueResId);
        }

        @Override
        public Long getDefaultValue() {
            return Long.valueOf(DouyaApplication.getInstance().getResources().getString(
                    getDefaultValueResId()));
        }

        @Override
        public Long getValue() {
            return SharedPrefsUtils.getLong(this);
        }

        @Override
        public void putValue(Long value) {
            SharedPrefsUtils.putLong(this, value);
        }
    }

    class FloatSettingsEntry extends SettingsEntry<Float> {

        public FloatSettingsEntry(@StringRes int keyResId, @StringRes int defaultValueResId) {
            super(keyResId, defaultValueResId);
        }

        @Override
        public Float getDefaultValue() {
            return Float.valueOf(DouyaApplication.getInstance().getResources().getString(
                    getDefaultValueResId()));
        }

        @Override
        public Float getValue() {
            return SharedPrefsUtils.getFloat(this);
        }

        @Override
        public void putValue(Float value) {
            SharedPrefsUtils.putFloat(this, value);
        }
    }

    class BooleanSettingsEntry extends SettingsEntry<Boolean> {

        public BooleanSettingsEntry(@StringRes int keyResId, @BoolRes int defaultValueResId) {
            super(keyResId, defaultValueResId);
        }

        @Override
        public Boolean getDefaultValue() {
            return DouyaApplication.getInstance().getResources().getBoolean(getDefaultValueResId());
        }

        @Override
        public Boolean getValue() {
            return SharedPrefsUtils.getBoolean(this);
        }

        @Override
        public void putValue(Boolean value) {
            SharedPrefsUtils.putBoolean(this, value);
        }
    }

    class EnumSettingsEntry<E extends Enum<E>> extends StringSettingsEntry {

        private E[] mEnumValues;

        public EnumSettingsEntry(@StringRes int keyResId, @StringRes int defaultValueResId,
                                 Class<E> enumClass) {
            super(keyResId, defaultValueResId);

            mEnumValues = enumClass.getEnumConstants();
        }

        public E getDefaultEnumValue() {
            return mEnumValues[Integer.parseInt(getDefaultValue())];
        }

        public E getEnumValue() {
            int ordinal = Integer.parseInt(getValue());
            if (ordinal < 0 || ordinal >= mEnumValues.length) {
                LogUtils.w("Invalid ordinal " + ordinal + ", with key=" + getKey()
                        + ", enum values=" + Arrays.toString(mEnumValues)
                        + ", reverting to default value");
                E enumValue = getDefaultEnumValue();
                putEnumValue(enumValue);
                return enumValue;
            }
            return mEnumValues[ordinal];
        }

        public void putEnumValue(E value) {
            putValue(String.valueOf(value.ordinal()));
        }
    }
}

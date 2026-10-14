package com.mikaeel.nickbook.model;

public record NickProfile(String rank, String skin, String name) {
    public static NickProfile empty(String realName) {
        return new NickProfile("DEFAULT", "normal", realName);
    }
}

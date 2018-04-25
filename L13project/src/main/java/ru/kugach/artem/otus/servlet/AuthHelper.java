package ru.kugach.artem.otus.servlet;

class AuthHelper {
    protected static boolean validate(String name, String pass) {
        if (name == null) return false;
        if (pass == null) return false;
        return name.equals("admin") && pass.equals("admin");
    }
}

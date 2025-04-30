package org.libraryapp.util;

public class NotificationMessageBuilder {
    public static String buildBookAddedMessage(String bookTitle) {
        return "Sizə '" + bookTitle + "' adlı kitab əlavə olundu.";
    }

    public static String buildUserRegistrationMessage(String username) {
        return "Hörmətli " + username + ", sistemə uğurla qeydiyyatdan keçdiniz.";
    }

    public static String buildBookDeletedMessage(String bookTitle) {
        return "'" + bookTitle + "' adlı kitab sistemdən silindi.";
    }

    public static String buildBookOverdueReminder(String bookTitle) {
        return "Sizin götürdüyünüz '" + bookTitle + "' kitabı hələ də qaytarılmayıb. Xahiş olunur qaytarasınız.";
    }

    public static String buildWelcomeMessage(String fullName) {
        return "Əziz " + fullName + ", Library App-ə xoş gəlmisiniz!";
    }


}

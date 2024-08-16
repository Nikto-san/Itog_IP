package ITOG;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Pattern;

public class UserDataProcessor {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите данные: Фамилия Имя Отчество Дата рождения(дд.мм.гггг) Номер телефона Пол(m/f), разделенные пробелом:");

        String input = scanner.nextLine();
        String[] data = input.split(" ");

        try {
            // Проверка количества введенных данных
            if (data.length > 6) {
                throw new IllegalArgumentException("Вы ввели больше данных, чем ожидалось. Ожидалось 6 элементов.");
            } else if (data.length <6) {
                throw new IllegalArgumentException("Вы ввели меньше данных, чем ожидалось. Ожидалось 6 элементов.");
            }else {
                String lastName = data[0];
                String firstName = data[1];
                String middleName = data[2];
                String birthDate = validateDate(data[3]);
                long phoneNumber = validatePhoneNumber(data[4]);
                char gender = validateGender(data[5]);
                String outputData = String.format("%s %s %s %s %d %c", lastName, firstName, middleName, birthDate, phoneNumber, gender);

                writeToFile(lastName, outputData);
                System.out.println("Данные успешно записаны.");
            }
        } catch (NumberFormatException e) {
            System.err.println("Неверный формат номера телефона: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Ошибка: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Ошибка в формате даты: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    // Метод для валидации даты
    private static String validateDate(String date) throws ParseException {
        String datePattern = "\\d{2}\\.\\d{2}\\.\\d{4}";

        if (!Pattern.matches(datePattern, date)) {
            throw new IllegalArgumentException("Неверный формат даты. Ожидается формат dd.MM.yyyy");
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateFormat.setLenient(false);
        dateFormat.parse(date);

        return date;
    }

    private static long validatePhoneNumber(String phone) {
        long phoneNumber = Long.parseLong(phone);
        if (phoneNumber <= 0) {
            throw new NumberFormatException("Номер телефона должен быть положительным числом.");
        }
        return phoneNumber;
    }

    private static char validateGender(String gender) {
        if (gender.length() != 1 || (!gender.equals("f") && !gender.equals("m"))) {
            throw new IllegalArgumentException("Пол должен быть обозначен символом 'f' или 'm'.");
        }
        return gender.charAt(0);
    }

    private static void writeToFile(String lastName, String data) throws IOException {
        File file = new File(lastName + ".txt");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(data);
            writer.newLine();
        }
    }
}




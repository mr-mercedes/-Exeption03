package ru.exeption.exep003.homework;

import java.io.*;
/*
Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке,
разделенные пробелом:
Фамилия Имя Отчество датарождения номертелефона пол
Форматы данных:
фамилия, имя, отчество - строки
дата_рождения - строка формата dd.mm.yyyy
номер_телефона - целое беззнаковое число без форматирования
пол - символ латиницей f или m.
Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым, вернуть код ошибки,
обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы.
Можно использовать встроенные типы java и создать свои. Исключение должно быть корректно обработано,
пользователю выведено сообщение с информацией, что именно неверно.
Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии,
в него в одну строку должны записаться полученные данные, вида
<Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
Не забудьте закрыть соединение с файлом.
При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано,
пользователь должен увидеть стектрейс ошибки.
 */
public class ExceptionJavaHW {
    public static void main(String[] args) {
        String[] params = reader();
        params = getParams(params);
        writer(params);
    }

    private static void writer(String[] params) {
        try (FileWriter fw = new FileWriter(params[0] + ".txt", true)) {
            for (String param : params) {
                fw.write(param + " ");
            }
            fw.write("\n");
            fw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static String[] reader() {
        String[] params = new String[6];
        System.out.println("Format of input: FirstName Surname MidName DateOfBirthday PhoneNumber Sex");
        System.out.print("Enter params: ");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            params = reader.readLine().trim().split(" ");
            if (params.length != 6) {
                throw new CountParametersException(params.length, "Error 101: Input parameters fail");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return params;
    }

    private static String[] getParams(String[] params) {
        String firstName = null;
        String surname = null;
        String midName = null;
        String dateOfBirthday = null;
        long phoneNumber = 0;
        String sex = null;
        try {
            int count = 0;
            for (String param : params) {
                if (isWord(param)) {
                    count++;
                } else if (isDate(param)) {
                    dateOfBirthday = param;
                } else if (isDigit(param)) {
                    phoneNumber = Long.parseLong(param);
                } else if (isSex(param)) {
                    sex = param;
                }
            }
            if (count == 3) {
                firstName = params[0];
                surname = params[1];
                midName = params[2];
            } else {
                throw new StringFormatException(params);
            }
        } catch (NullPointerException ex) {
            System.out.println("Error 102: Illegal format params " + ex.getMessage());
        }
        String[] data ={firstName, surname, midName, dateOfBirthday, String.valueOf(phoneNumber), String.valueOf(sex)};
        for (int i = 0; i < data.length; i++) {
            if (data[i] == null){
                throw new ParameterFormatException(data[i]);
            }
        }
        return data;
    }

    private static boolean isWord(String params) throws RuntimeException {
        if (params.length() < 2) return false;
        for (int i = 0; i < params.length(); i++) {
            char ch = params.charAt(i);
            if (!Character.isLetter(ch)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDigit(String params) throws RuntimeException {
        for (int i = 0; i < params.length(); i++) {
            char ch = params.charAt(i);
            if (!Character.isDigit(ch)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDate(String params) throws RuntimeException {
        String[] str = params.split("\\.");
        if (str.length != 3) return false;
        try {
            int day = Integer.parseInt(str[0]);
            int month = Integer.parseInt(str[1]);
            if (day < 0 || day > 31) throw new DataFormatException(str);
            if (month < 0 || month > 12) throw new DataFormatException(str);
            if (str[2].length() != 4) throw new DataFormatException(str);
        } catch (NumberFormatException ex){
            ex.printStackTrace();
        }
        for (String s : str) {
            for (int j = 0; j < s.length(); j++) {
                char ch = s.charAt(j);
                if (!Character.isDigit(ch)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isSex(String params) throws RuntimeException {
        return params.length() != 1 || params.equalsIgnoreCase("m") || params.equalsIgnoreCase("f");
    }
}

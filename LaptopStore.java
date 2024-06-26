// Подумать над структурой класса Ноутбук для магазина техники - выделить поля и
// методы. Реализовать в java.
// Создать множество ноутбуков.
// Написать метод, который будет запрашивать у пользователя критерий (или критерии)
// фильтрации и выведет ноутбуки, отвечающие фильтру. Критерии фильтрации можно
// хранить в Map. Например:
// “Введите цифру, соответствующую необходимому критерию:
// 1 - ОЗУ
// 2 - Объем ЖД
// 3 - Операционная система
// 4 - Цвет …
// Далее нужно запросить минимальные значения для указанных критериев - сохранить
// параметры фильтрации можно также в Map.
// Отфильтровать ноутбуки их первоначального множества и вывести проходящие по
// условиям.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LaptopStore {
    private List<Laptop> laptops; // Поле для хранения списка ноутбуков

    public LaptopStore() { //Конструктор, инициализирующий список ноутбуков и заполняющий его начальными данными.
        this.laptops = new ArrayList<>();
        initializeLaptops();
    }

    private void initializeLaptops() { // Метод для инициализации списка ноутбуков начальными данными.
        laptops.add(new Laptop("Asus", 8, 512, "Windows", "Black"));
        laptops.add(new Laptop("Acer", 16, 1024, "Linux", "Silver"));
        laptops.add(new Laptop("Dell", 4, 256, "Windows", "Red"));
    }

    public void filterLaptops() {
        Map<Integer, String> criteriaMap = new HashMap<>(); // Отображения возможных критериев фильтрации
        criteriaMap.put(1, "ram");
        criteriaMap.put(2, "storage");
        criteriaMap.put(3, "os");
        criteriaMap.put(4, "color");

        Map<String, Object> filterParams = new HashMap<>(); // Для хранения параметров фильтрации
        Scanner scanner = new Scanner(System.in);

        System.out.println("Введите цифру, соответствующую необходимому критерию:");
        for (Map.Entry<Integer, String> entry : criteriaMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }

        int criteriaChoice = scanner.nextInt();
        scanner.nextLine(); // Считываем символ новой строки.

        if (!criteriaMap.containsKey(criteriaChoice)) {
            System.out.println("Неверный выбор критерия.");
            return;
        }

        System.out.println("Введите минимальное значение для выбранного критерия:");
        String minValue = scanner.nextLine();

        try {
            switch (criteriaMap.get(criteriaChoice)) {
                case "ram":
                case "storage":
                    filterParams.put(criteriaMap.get(criteriaChoice), Integer.parseInt(minValue));
                    break;
                case "os":
                case "color":
                    filterParams.put(criteriaMap.get(criteriaChoice), minValue);
                    break;
                default:
                    System.out.println("Неверный выбор критерия.");
                    return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Некорректный формат числа для RAM или хранилища.");
            return;
        }

        List<Laptop> filteredLaptops = new ArrayList<>();
        for (Laptop laptop : laptops) {
            if (meetsCriteria(laptop, filterParams)) {
                filteredLaptops.add(laptop);
            }
        }

        System.out.println("Отфильтрованные ноутбуки:");
        for (Laptop laptop : filteredLaptops) {
            System.out.println(laptop.getModel());
        }
    }

    private boolean meetsCriteria(Laptop laptop, Map<String, Object> filterParams) { // Проверяем все параметры
        for (Map.Entry<String, Object> entry : filterParams.entrySet()) {
            String criteria = entry.getKey();
            Object value = entry.getValue();
            switch (criteria) {
                case "ram":
                    if (laptop.getRam() < (Integer) value)
                        return false;
                    break;
                case "storage":
                    if (laptop.getStorage() < (Integer) value)
                        return false;
                    break;
                case "os":
                    if (!laptop.getOs().equals(value))
                        return false;
                    break;
                case "color":
                    if (!laptop.getColor().equals(value))
                        return false;
                    break;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        LaptopStore store = new LaptopStore();
        store.filterLaptops();
    }
}

class Laptop { //Класс для хранения характеристик ноутбукуа
    private String model;
    private int ram;
    private int storage;
    private String os;
    private String color;

    public Laptop(String model, int ram, int storage, String os, String color) {
        this.model = model;
        this.ram = ram;
        this.storage = storage;
        this.os = os;
        this.color = color;
    }

    public String getModel() {
        return model;
    }

    public int getRam() {
        return ram;
    }

    public int getStorage() {
        return storage;
    }

    public String getOs() {
        return os;
    }

    public String getColor() {
        return color;
    }
}
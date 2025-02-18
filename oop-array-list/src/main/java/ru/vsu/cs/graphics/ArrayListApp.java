package ru.vsu.cs.graphics;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.vsu.cs.logic.MyArrayList;

import java.util.Arrays;

public class ArrayListApp extends Application {
    private MyArrayList<String> list;
    private TextArea outputArea;
    private TextField inputField;

    @Override
    public void start(Stage primaryStage) {
        list = new MyArrayList<>();

        VBox inputPanel = new VBox(10);

        inputField = new TextField();
        inputField.setPromptText("Введите элемент или массив (например: Apple, Banana, Cherry)");

        GridPane buttonPanel = new GridPane();
        buttonPanel.setVgap(10);
        buttonPanel.setHgap(10);
        buttonPanel.setStyle("-fx-padding: 10;");

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(200);

        addButton(buttonPanel, "Добавить элемент (add)", this::addElement, 0, 0);
        addButton(buttonPanel, "Добавить все элементы (разделенные запятой) (addAll)", this::addAll, 0, 1);
        addButton(buttonPanel, "Добавить в начало (addFirst)", this::addFirst, 0, 2);
        addButton(buttonPanel, "Добавить в конец (addLast)", this::addLast, 0, 3);
        addButton(buttonPanel, "Очистить список (clear)", this::clearList, 0, 4);
        addButton(buttonPanel, "Содержит ли (элемент) (contains)", this::containsElement, 0, 5);
        addButton(buttonPanel, "Содержит ли все элементы (разделенные запятой) (containsAll)", this::containsAll, 0, 6);
        addButton(buttonPanel, "Получить (по индексу) (get)", this::getElement, 0, 7);
        addButton(buttonPanel, "Получить первый элемент (getFirst)", this::getFirst, 0, 8);
        addButton(buttonPanel, "Получить последний элемент (getLast)", this::getLast, 0, 9);

        addButton(buttonPanel, "Найти индекс элемента (indexOf)", this::indexOf, 1, 0);
        addButton(buttonPanel, "Найти индекс элемента с конца (lastIndexOf)", this::lastIndexOf, 1, 1);
        addButton(buttonPanel, "Удалить по индексу (remove(index))", this::removeElement, 1, 2);
        addButton(buttonPanel, "Удалить по значению (remove(value))", this::removeObject, 1, 3);
        addButton(buttonPanel, "Удалить первый элемент (removeFirst)", this::removeFirst, 1, 4);
        addButton(buttonPanel, "Удалить последний элемент (removeLast)", this::removeLast, 1, 5);
        addButton(buttonPanel, "Вывести развернутый список (reversed)", this::reversed, 1, 6);
        addButton(buttonPanel, "Установить значение (индекс, значение) (set)", this::setElement, 1, 7);
        addButton(buttonPanel, "Размер списка (size)", this::size, 1, 8);
        addButton(buttonPanel, "Перевести в массив (toArray)", this::toArray, 1, 9);

        VBox root = new VBox(10, inputPanel, inputField, buttonPanel, outputArea);
        root.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("ArrayListApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void addButton(GridPane gridPane, String text, EventHandler<ActionEvent> action, int col, int row) {
        Button button = new Button(text);
        button.setOnAction(action);
        gridPane.add(button, col, row);
    }


    private void addElement(ActionEvent e) {
        String input = inputField.getText();
        list.addLast(input);
        outputArea.appendText("Добавлен элемент '" + input + "' в список\n");
        inputField.clear();
    }

    private void addAll(ActionEvent e) {
        String input = inputField.getText();
        MyArrayList<String> items = MyArrayList.asList(input.split(","));
        list.addAll(0, items);
        outputArea.appendText("Добавлены элементы '" + input + "' множественными элементами\n");
        inputField.clear();
    }

    private void addFirst(ActionEvent e) {
        String input = inputField.getText();
        list.add(0, input);
        list.addFirst(input);
        outputArea.appendText("Добавлен элемент '" + input + "' в начало\n");
        inputField.clear();
    }

    private void addLast(ActionEvent e) {
        String input = inputField.getText();
        list.addLast(input);
        outputArea.appendText("Добавлен элемент '" + input + "' в конец\n");
        inputField.clear();
    }

    private void clearList(ActionEvent e) {
        list.clear();
        outputArea.appendText("Список очищен\n");
    }

    private void containsElement(ActionEvent e) {
        String input = inputField.getText();
        boolean contains = list.contains(input);
        outputArea.appendText("Содержит элемент '" + input + "': " + contains + "\n");
        inputField.clear();
    }

    private void containsAll(ActionEvent e) {
        String input = inputField.getText();
        MyArrayList<String> otherList = MyArrayList.asList(input.split(","));
        boolean containsAll = list.containsAll(otherList);
        outputArea.appendText("Содержит все элементы '" + input + "': " + containsAll + "\n");
        inputField.clear();
    }

    private void getElement(ActionEvent e) {
        try {
            int index = Integer.parseInt(inputField.getText());
            String element = list.get(index);
            outputArea.appendText("Элемент по индексу " + index + ": " + element + "\n");
        } catch (NumberFormatException ex) {
            outputArea.appendText("Неправильный формат ввода индекса\n");
        } catch (IndexOutOfBoundsException ex) {
            outputArea.appendText("Индекс за границами списка\n");
        }
        inputField.clear();
    }

    private void getFirst(ActionEvent e) {
        String first = list.getFirst();
        outputArea.appendText("Первый элемент: " + first + "\n");
    }

    private void getLast(ActionEvent e) {
        String last = list.getLast();
        outputArea.appendText("Последний элемент: " + last + "\n");
    }

    private void indexOf(ActionEvent e) {
        String input = inputField.getText();
        int index = list.indexOf(input);
        outputArea.appendText("Индекс элемента '" + input + "': " + index + "\n");
        inputField.clear();
    }

    private void lastIndexOf(ActionEvent e) {
        String input = inputField.getText();
        int index = list.lastIndexOf(input);
        outputArea.appendText("Последнее вхождение элемента с индексом '" + input + "': " + index + "\n");
        inputField.clear();
    }

    private void removeElement(ActionEvent e) {
        try {
            int index = Integer.parseInt(inputField.getText());
            String removed = list.remove(index);
            outputArea.appendText("Удален элемент по индексу " + index + ": " + removed + "\n");
        } catch (NumberFormatException ex) {
            outputArea.appendText("Неправильный формат ввода индекса\n");
        } catch (IndexOutOfBoundsException ex) {
            outputArea.appendText("Индекс за границами списка\n");
        }
        inputField.clear();
    }

    private void removeObject(ActionEvent e) {
        try {
            String input = inputField.getText();
            boolean removed = list.remove(input);
            outputArea.appendText("Removed '" + input + "': " + removed + "\n");
        } catch (IndexOutOfBoundsException exception) {
            outputArea.appendText("Элемент не найден\n");
        }
        inputField.clear();
    }

    private void removeFirst(ActionEvent e) {
        try {
            String removed = list.removeFirst();
            outputArea.appendText("Removed the first element: " + removed + "\n");
        } catch (IndexOutOfBoundsException exception) {
            outputArea.appendText("Список пуст\n");
        }
    }

    private void removeLast(ActionEvent e) {
        try {
            String removed = list.removeLast();
            outputArea.appendText("Removed the last element: " + removed + "\n");
        } catch (IndexOutOfBoundsException exception) {
            outputArea.appendText("Список пуст");
        }
    }

    private void reversed(ActionEvent e) {
        MyArrayList<String> reversedList = list.reversed();
        outputArea.appendText("Развернутый список: " + reversedList + "\n");
    }

    private void setElement(ActionEvent e) {
        try {
            int index = Integer.parseInt(inputField.getText());
            String element = inputField.getText();
            list.set(index, element);
            outputArea.appendText("Установлено значение '" + element + "' по индексу " + index + "\n");
        } catch (NumberFormatException ex) {
            outputArea.appendText("Неправильный формат ввода индекса\n");
        } catch (IndexOutOfBoundsException ex) {
            outputArea.appendText("Индекс за границами списка\n");
        }
        inputField.clear();
    }

    private void size(ActionEvent e) {
        int size = list.size();
        outputArea.appendText("Размер списка: " + size + "\n");
    }

    private void toArray(ActionEvent e) {
        Object[] array = list.toArray();
        outputArea.appendText("Конвертирован в массив: " + Arrays.toString(array) + "\n");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

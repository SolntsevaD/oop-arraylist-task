package ru.vsu.cs.logic;

import java.util.Arrays;
import java.util.NoSuchElementException;

public class MyArrayList<T> implements List<T> {

    private static final int DEFAULT_ARRAY_CAPACITY = 20;
    private Object[] array;
    private int size;

    public MyArrayList() {
        this.array = new Object[DEFAULT_ARRAY_CAPACITY];
        this.size = 0;
    }

    public MyArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException();
        }
        this.array = new Object[capacity];
        this.size = 0;
    }


    /**
     * Увеличивает размер массива.
     * Копирует содержимое текущего массива в новый, увеличенный в 1.5 раза.
     */
    private void increaseArrayLength() {
        this.array = Arrays.copyOf(array, array.length * 3 / 2);
    }

    /**
     * Добавляет элемент по индексу.
     * @param index индекс, на место которого встанет значение value.
     * @param value элемент, который будет вставлен.
     */
    @Override
    public void add(int index, T value) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == array.length) {
            increaseArrayLength();
        }
        // Сдвигаем элементы вправо для освобождения места
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size++;
    }

    /**
     * Добавляет все элементы из переданной коллекции по индексу.
     * @param index индекс, начиная с которого будут добавляться элементы.
     * @param c коллекция элементов.
     * @return возвращает true, если элементы были добавлены.
     */
    @Override
    public boolean addAll(int index, List<T> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (c.isEmpty()) {
            return false;
        }
        for (int i = 0; i < c.size(); i++) {
            add(index++, c.get(i));
        }
        return true;
    }

    /**
     * Добавляет элемент в начало списка.
     * @param value элемент, который добавляется в начало.
     */
    @Override
    public void addFirst(T value) {
        add(0, value);
    }

    /**
     * Добавляет элемент в конец списка.
     * @param value элемент, который добавляется в конец.
     */
    @Override
    public void addLast(T value) {
        add(size, value);
    }

    /**
     * Очищает список, удаляя все элементы.
     */
    @Override
    public void clear() {
        this.array = new Object[DEFAULT_ARRAY_CAPACITY];
        this.size = 0;
    }

    /**
     * Проверяет, содержится ли элемент в списке.
     * @param o элемент, который ищем в списке.
     * @return возвращает true, если элемент найден.
     */
    @Override
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }

    /**
     * Проверяет, содержатся ли все элементы переданной коллекции в списке.
     * @param c коллекция, элементы которой проверяем.
     * @return возвращает true, если все элементы коллекции содержатся в списке.
     */
    @Override
    public boolean containsAll(List<T> c) {
        for(int i = 0; i < c.size(); i++) {
            if (!contains(c.get(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Проверяет, равен ли текущий список переданному объекту.
     * @param o объект для сравнения.
     * @return возвращает true, если списки одинаковые.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyArrayList<?> that = (MyArrayList<?>) o;
        return size == that.size && Arrays.equals(array, that.array);
    }

    /**
     * Получает элемент по индексу.
     * @param index индекс элемента.
     * @return значение элемента.
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return (T) array[index];
    }

    /**
     * Получает первый элемент списка.
     * @return первый элемент списка.
     */
    @Override
    public T getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return get(0);
    }

    /**
     * Получает последний элемент списка.
     * @return последний элемент списка.
     */
    @Override
    public T getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return get(size - 1);
    }

    /**
     * Вычисляет хэш-код для списка.
     * @return хэш-код списка.
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(array);
    }

    /**
     * Ищет индекс первого вхождения элемента в список.
     * @param o элемент, который ищем.
     * @return индекс элемента или -1, если не найден.
     */
    @Override
    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Проверяет, пуст ли список.
     * @return возвращает true, если список пуст.
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Ищет индекс последнего вхождения элемента в список.
     * @param o элемент, который ищем.
     * @return индекс элемента или -1, если не найден.
     */
    @Override
    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Удаляет элемент по индексу.
     * @param index индекс элемента для удаления.
     * @return удалённый элемент.
     */
    @SuppressWarnings("unchecked")
    @Override
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        T oldValue = (T) array[index];
        // Сдвигаем элементы влево после удаления
        System.arraycopy(array, index + 1, array, index, size - index - 1);
        size--;
        return oldValue;
    }

    /**
     * Удаляет первое вхождение элемента из списка.
     * @param o элемент для удаления.
     * @return true, если элемент был удален, иначе false.
     */
    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index != -1) {
            remove(index);
            return true;
        }
        return false;
    }

    /**
     * Удаляет первый элемент из списка.
     * @return удалённый первый элемент.
     */
    @Override
    public T removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return remove(0);
    }

    /**
     * Удаляет последний элемент из списка.
     * @return удалённый последний элемент.
     */
    @Override
    public T removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return remove(size - 1);
    }

    /**
     * Возвращает новый список, элементы которого идут в обратном порядке.
     * @return новый список в обратном порядке.
     */
    @Override
    public MyArrayList<T> reversed() {
        MyArrayList<T> reversedList = new MyArrayList<>();
        for (int i = size - 1; i >= 0; i--) {
            reversedList.addLast(this.get(i));
        }
        return reversedList;
    }

    /**
     * Меняет значение элемента по индексу.
     * @param index индекс элемента для замены.
     * @param value новое значение элемента.
     */
    @Override
    public void set(int index, T value) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        array[index] = value;
    }

    /**
     * Возвращает количество элементов в списке.
     * @return размер списка.
     */
    @Override
    public int size() {
        return size;
    }

    public static <T> MyArrayList<T> asList(T... array) {
        MyArrayList<T> list = new MyArrayList<>();
        for (T t : array) {
            list.addLast(t);
        }
        return list;
    }

    /**
     * Возвращает массив, содержащий все элементы списка.
     * @return массив элементов списка.
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(array, size);
    }
}

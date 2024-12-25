package ru.vsu.cs.logic;


import java.util.*;
import org.jetbrains.annotations.NotNull;

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
     * @return возвращает длину списка
     */
    public int size() {
        return size;
    }

    /**
     * @return возвращает true, если список пуст
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     *
     * @param o элемент, который будем искать в списке
     * @return возвращает true, если элемент содержится в списке
     */
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    private class Itr implements Iterator<T> {

        int cursor = 0;

        public Itr() {
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            try {
                return (T) array[cursor++];
            } catch (IndexOutOfBoundsException e) {
                throw new IndexOutOfBoundsException();
            }
        }
    }


    @Override
    @NotNull
    public Iterator<T> iterator() {
        return new Itr();
    }

    private class ListItr extends Itr implements ListIterator<T> {

        int cursor;
        boolean calledNextOrPrev = false;
        boolean calledRemoveOrAdd = false;
        boolean calledAdd = false;

        public ListItr(int index) {
            this.cursor = index;
        }

        public ListItr() {
            this(0);
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (!hasNext()) {
                throw new IndexOutOfBoundsException();
            }
            T value = (T) array[cursor++];
            calledNextOrPrev = true;
            calledRemoveOrAdd = false;
            calledAdd = false;
            return value;
        }

        @Override
        public boolean hasPrevious() {
            return cursor != 0;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T previous() {
            if (!hasPrevious()) {
                throw new NoSuchElementException();
            }
            T value = (T) array[--cursor];
            calledNextOrPrev = true;
            calledRemoveOrAdd = false;
            calledAdd = false;
            return value;
        }

        @Override
        public int nextIndex() {
            if (!hasNext()) {
                return size;
            }
            return cursor;
        }

        @Override
        public int previousIndex() {
            if (!hasPrevious()) {
                return -1;
            }
            return cursor - 1;
        }

        @Override
        public void remove() {
            if (calledNextOrPrev && !calledAdd) {
                MyArrayList.this.remove(cursor);
                calledNextOrPrev = false;
            }
        }

        @Override
        public void set(T t) {
            if (calledNextOrPrev && !calledRemoveOrAdd) {
                MyArrayList.this.set(cursor, t);
            }
        }

        @Override
        public void add(T t) {
            MyArrayList.this.add(cursor, t);
            calledRemoveOrAdd = true;
            calledAdd = true;
        }
    }

    private void increaseArrayLength() {
        this.array = Arrays.copyOf(array, array.length * 3 / 2);
    }

    /**
     * Добавляет значение T в список. Если внутренний массив заполнился, то расширяет его
     * @param value значение, которое будет добавлено в список
     * @return возвращает true, если успешно добавил элемент
     */
    public boolean add(T value) {
        if (size >= array.length) {
            increaseArrayLength();
        }
        this.array[size] = value;
        this.size++;
        return true;
    }

    /**
     * Удаляет первый элемент слева из списка со значением элемента "о".
     * @param o элемент, который будет удален
     * @return возвращает true, если элемент был найден и удален.
     */
    @Override
    public boolean remove(Object o) {
        int index = indexOf(o);
        if (index == -1) {
            return false;
        } else {
            remove(index);
            return true;
        }
    }

    /**
     * Проверяет, содержатся ли все элементы коллекции в списке.
     * @param c коллекция, содержимое которой будем искать в нашем списке.
     * @return возвращает true, если все элементы коллекции "с" содержатся в списке.
     */
    @Override
    public boolean containsAll(@NotNull Collection<?> c) {
        for (Object obj : c) {
            if (!contains(obj)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Добавляет элементы из коллекции "с" в список.
     * @param c коллекция, содержимое которого будет добавлено в список.
     * @return возвращает true, если исходный список был изменен.
     */
    @Override
    public boolean addAll(@NotNull Collection<? extends T> c) {
        if (c.isEmpty()) {
            return false;
        }
        for (T value : c) {
            add(value);
        }
        return true;
    }

    /**
     * Добавляет элементы из коллекции "с" в список, начиная с индекса index.
     * @param index индекс, начиная с которого будут добавлены элементы.
     * @param c коллекция, содержимое которого будет добавлено в список.
     * @return возвращает true, если исходный список был изменен.
     */
    @Override
    public boolean addAll(int index, @NotNull Collection<? extends T> c) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        if (c.isEmpty()) {
            return false;
        }
        int i = index;
        for (T value : c) {
            add(i++, value);
        }
        return true;
    }

    /**
     * Удаляет элементы из списка, которые содержатся в коллекции "с".
     * @param c коллекция, содержащая элементы, которые будут удалены из списка.
     * @return возвращает true, если исходный список был изменен.
     */
    @Override
    public boolean removeAll(@NotNull Collection<?> c) {
        if (c.isEmpty()) {
            return false;
        }
        for (Object obj : c) {
            remove(obj);
        }
        return true;
    }

    /**
     * Удаляет все элементы, которых нет в передаваемой коллекции.
     *
     * @param c коллекция, содержащая элементы, которые будут сохранены
     * @return возвращает true, если список был изменен, иначе false
     */
    @Override
    public boolean retainAll(@NotNull Collection<?> c) {
        /*
        2 случая когда не изменяется исходный список
        1. list = c
        2. c.size = 0
        Тк equals может быть слишком затратен для проверки равны ли коллекции,
        выбрал добавить лишнюю переменную
         */
        boolean haveChanged = false;
        for (Object obj : array) {
            if (!c.contains(obj)) {
                remove(obj);
                haveChanged = true;
            }
        }
        return haveChanged;
    }

    /**
     * Удаляет все элементы списка.
     */
    @Override
    public void clear() {
        this.array = new Object[DEFAULT_ARRAY_CAPACITY];
        this.size = 0;
    }

    /**
     * Функция, для получения значения по индексу.
     * @param index индекс элемента, которое нужно вернуть
     * @return значение элемента по индексу
     */
    @Override
    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        return (T) array[index];
    }

    /**
     * Добавляет элемент по индексу.
     * @param index индекс, на место которого встанет значение value.
     * @param value элемент, который будет вставлен.
     */
    public void add(int index, T value) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        if (size + 1 == array.length) {
            increaseArrayLength();
        }
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size++;
    }

    public void addFirst(T value) {
        if (isEmpty()) {
            add(value);
        } else {
            add(0, value);
        }
    }

    public void addLast(T value) {
        add(value);
    }

    public T getFirst() {
        return get(0);
    }

    public T getLast() {
        return get(size() - 1);
    }

    public T removeFirst() {
        return remove(0);
    }

    public T removeLast() {
        return remove(size() - 1);
    }

    public MyArrayList<T> reversed() {
        MyArrayList<T> reversedList = new MyArrayList<>();

        for (int i = size() - 1; i >= 0; i--) {
            reversedList.add(this.get(i));
        }

        return reversedList;
    }

    /**
     * Меняет значение элемента по индексу.
     * @param index индекс элемента, который нужно заменить
     * @param value значение, которое встанет на место предыдущего.
     * @return возвращает прошлое значение элемента.
     */
    @SuppressWarnings("unchecked")
    public T set(int index, T value) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        T oldValue = (T) array[index];
        array[index] = value;
        return oldValue;
    }

    /**
     * Удаляет элемент по индексу.
     * @param index индекс элемента, который будет удален.
     * @return возвращает значение элемента, которое было на месте index.
     */
    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        T oldValue = (T) array[index];
        System.arraycopy(array, index + 1, array, index, size - index);
        size--;
        return oldValue;
    }

    /**
     * Ищет элемент в списке. Возвращает первое вхождение слева.
     * @param o значение элемента, которое мы ищем.
     * @return возвращает index элемента. Либо -1, если элемент не найден.
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
     * Ищет элемент в списке. Возвращает первое вхождение справа.
     * @param o значение элемента, которое мы ищем.
     * @return возвращает index элемента. Либо -1, если элемент не найден.
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

    @Override
    public @NotNull ListIterator<T> listIterator() {
        return new ListItr();
    }

    @Override
    public @NotNull ListIterator<T> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    @SuppressWarnings({"unchecked"})
    public @NotNull List<T> subList(int fromIndex, int toIndex) {
        MyArrayList<T> newList = new MyArrayList<>();
        if (fromIndex >= 0 && toIndex < size && fromIndex < toIndex) {
            for (int i = fromIndex; i < toIndex; i++) {
                newList.add((T) array[i]);
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
        return newList;
    }


    public Object @NotNull [] toArray() {
        Object[] arr = new Object[size];
        System.arraycopy(array, 0, arr, 0, size);
        return arr;
    }

    @Override
    public <T1> T1 @NotNull [] toArray(T1 @NotNull [] a) {
        System.arraycopy(array, 0, a, 0, size);
        return a;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        for (int i = 0; i < size - 1; i++) {
            stringBuilder.append(array[i]).append(", ");
        }
        stringBuilder.append(array[size - 1]).append("}");
        return stringBuilder.toString();
    }
}

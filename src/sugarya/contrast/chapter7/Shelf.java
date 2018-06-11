package sugarya.contrast.chapter7;

import java.beans.*;

public class Shelf {
    private final String name;
    private Book book;
    private int year;

    public Shelf(String name, Book book) {
        this(name, book, 1);

    }

    public Shelf(String name, Book book, int year) {
        this.name = name;
        this.book = book;
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public Book getBook() {
        return book;
    }

    private PropertyChangeSupport mBookProperty = new PropertyChangeSupport(this);

    public void setBook(Book book) {
        Book oldBook = this.book;
        this.book = book;
        mBookProperty.firePropertyChange("book", oldBook, book);
    }

    public void addBookChangeListener(PropertyChangeListener changeListener) {
        mBookProperty.addPropertyChangeListener("book", changeListener);
    }

    public void removeBookChangeListener(PropertyChangeListener changeListener) {
        mBookProperty.removePropertyChangeListener("book", changeListener);
    }


    public int getYear() {
        return year;
    }

    private VetoableChangeSupport mYearVetoable = new VetoableChangeSupport(this);
    public void setYear(int year) throws PropertyVetoException {
        int oldYear = this.year;
        mYearVetoable.fireVetoableChange("year", oldYear, year);
        this.year = year;
    }

    public void addYearVetoableListener(VetoableChangeListener vetoableChangeListener) {
        mYearVetoable.addVetoableChangeListener("year", vetoableChangeListener);
    }

    public void removeYearVetoableListener(VetoableChangeListener vetoableChangeListener){
        mYearVetoable.removeVetoableChangeListener("year", vetoableChangeListener);
    }





    public static class Book {
        private final String name;

        public Book(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "Book{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}



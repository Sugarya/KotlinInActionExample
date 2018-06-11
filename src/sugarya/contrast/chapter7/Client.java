package sugarya.contrast.chapter7;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;

public class Client {

    public static void main(String[] args) {
        testPropertyChangeSupport();
    }

    private static void testPropertyChangeSupport(){
        Shelf.Book book = new Shelf.Book("Think in Java");
        Shelf shelf = new Shelf("书架", book);

        shelf.addBookChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Shelf.Book oldBook = (Shelf.Book)evt.getOldValue();
                Shelf.Book newBook = (Shelf.Book)evt.getNewValue();

                System.out.println("oldBook = " + oldBook + ", newBook = " + newBook);
            }
        });

        shelf.setBook(new Shelf.Book("Kotlin In Action"));
        System.out.println("current book = " + shelf.getBook().toString());


        shelf.addYearVetoableListener(new VetoableChangeListener() {
            @Override
            public void vetoableChange(PropertyChangeEvent evt) throws PropertyVetoException {
                throw new PropertyVetoException("", evt);
            }
        });

        try {
            shelf.setYear(4);
        } catch (PropertyVetoException e) {
            System.out.println("PropertyVetoException");
        }

        System.out.println("year = " + shelf.getYear());
    }
}

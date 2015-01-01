package alg.queues;

public interface Queue<T> {

	boolean isEmpty ();

    T   getFirst( );

    void    insert (T value);

    T   peekFirst ();
}

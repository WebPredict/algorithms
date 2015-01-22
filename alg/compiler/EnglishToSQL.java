package alg.compiler;

/**
 * Created with IntelliJ IDEA.
 * User: jsanchez
 * Date: 1/22/15
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class EnglishToSQL {

    /**
     * Placeholder for an english-to-SQL generator:
     *
     *
     * find all customers with at least one order
     *
     *
     * select * from customer c where exists (select o.customer_id from order o where o.customer_id = c.id);
     *
     * find orders for customer name "Foo"
     *
     * find order and customer data for customer name "Foo"
     *
     * select * from order o, customer c where o.customer_id = c.id;
     *
     * What are some more useful, complex examples?
     *
     */
}

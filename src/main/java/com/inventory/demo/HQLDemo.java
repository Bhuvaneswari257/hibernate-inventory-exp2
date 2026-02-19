package com.inventory.demo;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.inventory.entity.Product;
import com.inventory.util.HibernateUtil;
import com.inventory.loader.ProductDataLoader;

import java.util.List;

public class HQLDemo {

    public static void main(String[] args) {

        SessionFactory factory = HibernateUtil.getSessionFactory();
        Session session = factory.openSession();

        // RUN ONLY ONCE to insert sample data
        // ProductDataLoader.loadData(session);

        // ---------------- SORT ----------------
        Query<Product> q1 =
        session.createQuery("FROM Product ORDER BY price ASC", Product.class);

        System.out.println("Sorted by price:");
        for(Product p : q1.list())
            System.out.println(p.getName()+" "+p.getPrice());

        // ---------------- COUNT ----------------
        Query<Long> q2 =
        session.createQuery("SELECT COUNT(p) FROM Product p", Long.class);

        System.out.println("Total products = "+q2.uniqueResult());

        // ---------------- PRICE RANGE ----------------
        Query<Product> q3 =
        session.createQuery("FROM Product WHERE price BETWEEN 500 AND 5000", Product.class);

        System.out.println("Price range 500-5000:");
        for(Product p : q3.list())
            System.out.println(p.getName());

        // ---------------- LIKE ----------------
        Query<Product> q4 =
        session.createQuery("FROM Product WHERE name LIKE 'D%'", Product.class);

        System.out.println("Names starting with D:");
        for(Product p : q4.list())
            System.out.println(p.getName());

        // ---------------- PAGINATION ----------------
        Query<Product> q5 =
        session.createQuery("FROM Product", Product.class);

        q5.setFirstResult(0);
        q5.setMaxResults(3);

        System.out.println("First 3 products:");
        for(Product p : q5.list())
            System.out.println(p.getName());

        // ---------------- MIN MAX ----------------
        Query<Object[]> q6 =
        session.createQuery("SELECT MIN(price), MAX(price) FROM Product", Object[].class);

        Object[] result = q6.uniqueResult();
        System.out.println("Min price = "+result[0]);
        System.out.println("Max price = "+result[1]);

        session.close();
        factory.close();
    }
}

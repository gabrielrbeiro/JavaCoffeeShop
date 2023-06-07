package nl.brianvermeer.workshop.coffee.repository;

import nl.brianvermeer.workshop.coffee.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;
import java.util.Locale;

@Repository
public class SearchRepository {

    @Autowired
    EntityManager em;

    @Autowired
    DataSource dataSource;

    public List<Product> searchProduct (String input) {
        var lowerInput = input.toLowerCase(Locale.ROOT);
        final var cb = em.getCriteriaBuilder();
        final var cq = cb.createQuery(Product.class);
        final var root = cq.from(Product.class);
        cq.select(root);

        cq.where(
                cb.or(
                        cb.like(cb.lower(root.get("description")), "%" + lowerInput + "%"),
                        cb.like(cb.lower(root.get("productName")), "%" + lowerInput + "%" )
                )
        );

        return em.createQuery(cq).getResultList();

    }

}

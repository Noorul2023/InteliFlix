package com.intelitrac.movie.presenation.converter;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import session.Finder;

/**
 * Allows {@code Converter}s to obtain JPA entities without knowing the entity
 * class at compile-time. This class should not be used by anything other than
 * {@code Converter}s.
 *
 * @author Craig Pell
 */
@SessionScoped
@Named
public class EntityFinder
        implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * @serial
     */
    @EJB
    private Finder finder;

    /**
     * Returns a container-created (and properly injected) instance for the
     * specified context.
     *
     * @throws IllegalArgumentException if context is {@code null}
     */
    static EntityFinder getInstance(FacesContext context) {
        if (context == null) {
            throw new IllegalArgumentException("FacesContext cannot be null");
        }

        return context.getApplication().evaluateExpressionGet(context,
                "#{entityFinder}", EntityFinder.class);
    }

    /**
     * Retrieves a JPA entity by its primary key. This never returns
     * {@code null}.
     *
     * @param key primary key of entity to retrieve
     * @param entityClass class of a JPA entity
     *
     * @return non-{@code null} entity of the specified entity type
     *
     * @throws EJBException if no entity with the specified key exists
     */
    <T> T find(Integer key,
            Class<T> entityClass) {
        return finder.find(key, entityClass);
    }

    /**
     * Retrieves a JPA entity by its primary key and entity class name. This
     * never returns {@code null}.
     *
     * @param key primary key of entity to retrieve
     * @param entityClassName fully qualified name of JPA entity class
     *
     * @return non-{@code null} entity of the specified entity type
     *
     * @throws IllegalArgumentException if {@code entityClassName} is not the
     * name of a known class
     * @throws EJBException if no entity with the specified key exists
     */
    Object find(Integer key,
            String entityClassName) {
        try {
            Class<?> entityClass = Class.forName(entityClassName);
            return find(key, entityClass);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException(e);
        }
    }
}

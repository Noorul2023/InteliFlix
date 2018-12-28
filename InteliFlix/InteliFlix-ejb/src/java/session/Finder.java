package session;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Generic retriever of an entity based on entity type and primary key.
 *
 * @author Craig Pell
 */
@Stateless
public class Finder
        implements Serializable {

    private static final long serialVersionUID = 1;

    /**
     * @serial
     */
    @PersistenceContext(unitName = "InteliFlix-ejbPU")
    private EntityManager entityManager;

    public <T> T find(Object key,
            Class<T> entityClass) {
        return entityManager.find(entityClass, key);
    }
}

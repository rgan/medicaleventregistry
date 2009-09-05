package org.healthapps.medicaleventregistry.dao;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import java.util.Collection;
import java.util.List;

public class AbstractDao {

    protected PersistenceManagerFactory pmf;

    public AbstractDao(PersistenceManagerFactory factory) {
        pmf = factory;
    }

    protected void closeTransactionAndPM(PersistenceManager pm) {
        if (pm.currentTransaction().isActive()) {
            pm.currentTransaction().rollback();
        }
        pm.close();
    }

    protected Object findByName(String name, String className) {
        PersistenceManager pm = pmf.getPersistenceManager();
        String query = "select from " + className + " where name == findName PARAMETERS java.lang.String findName order by name ascending";
        try {
            List objects = (List) pm.newQuery(query).execute(name);
            if (objects.isEmpty()) {
                return null;
            }
            return pm.detachCopy(objects.get(0));
        } finally {
            pm.close();
        }
    }

    protected Collection all(String className) {
        PersistenceManager pm = pmf.getPersistenceManager();
        String query = "select from " + className;
        try {
            List objects = (List) pm.newQuery(query).execute();
            return pm.detachCopyAll(objects);
        } finally {
            pm.close();
        }
    }

    protected PersistenceManager getPM() {
        return pmf.getPersistenceManager();
    }

    public Object findById(Long id, String className) {
        PersistenceManager pm = getPM();
        try {
            final List result = findByIdWithoutDetaching(pm, id, className);
            if (result.isEmpty()) {
                return null;
            }
            return pm.detachCopy(result.get(0));
        } finally {
            pm.close();
        }
    }

    private List findByIdWithoutDetaching(PersistenceManager pm, Long id, String className) {
        String query = "select from " + className + " where id == givenId PARAMETERS long givenId order by name ascending";
        return (List) pm.newQuery(query).execute(id);
    }

    protected void storeObject(Object jdoObject) {
        PersistenceManager pm = getPM();
        try {
            pm.currentTransaction().begin();
            pm.makePersistent(jdoObject);
            pm.currentTransaction().commit();
        } finally {
            closeTransactionAndPM(pm);
        }
    }

    protected void deleteById(Long id, String className) {
        PersistenceManager pm = getPM();
        try {
            pm.currentTransaction().begin();
            List results = findByIdWithoutDetaching(pm, id, className);
            pm.deletePersistent(results.get(0));
            pm.currentTransaction().commit();
        } finally {
            closeTransactionAndPM(pm);
        }
    }
}

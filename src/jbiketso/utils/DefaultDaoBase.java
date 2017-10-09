package cr.co.coopeagri.opentouch.model.classes;

import java.util.function.Supplier;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;

import org.datafx.crud.jpa.CreateOnceSupplier;
import org.datafx.util.EntityWithId;

import cr.co.coopeagri.opentouch.model.interfaces.DaoBase;

public class DefaultDaoBase<E extends EntityWithId<PK>, PK> implements
		DaoBase<E, PK> {

	private CreateOnceSupplier<EntityManager> entityManagerSupplier;
	private EntityManager entityManager;
	private Class<E> entityClass;

	public DefaultDaoBase(EntityManager entityManager, Class<E> entityClass) {
		this(() -> entityManager, entityClass);
	}

	public DefaultDaoBase(Supplier<EntityManager> entityManagerSupplier,
			Class<E> entityClass) {
		this(new CreateOnceSupplier<EntityManager>(entityManagerSupplier),
				entityClass);
	}

	public DefaultDaoBase(
			CreateOnceSupplier<EntityManager> entityManagerSupplier,
			Class<E> entityClass) {
		this.entityManagerSupplier = entityManagerSupplier;
		this.entityClass = entityClass;
	}

	public EntityManager getEntityManager() {
		if (entityManager == null) {
			entityManager = entityManagerSupplier.get();
		}
		return entityManager;
	}

	@Override
	public Resultado<E> save(E entity) {
		Resultado<E> resultado = new Resultado<E>();
		PK id = entity.getId();
		E object;
		if (id == null) {
			getEntityManager().persist(entity);
			object = entity;
		} else {
			object = getEntityManager().merge(entity);
		}
		resultado.set(object);
		return (resultado);
	}

	@Override
	public Resultado<Void> delete(E entity) {
		Resultado<Void> resultado = new Resultado<Void>();
		PK id = entity.getId();
		if (id != null) {
			getEntityManager().remove(entity);
		}
		return (resultado);
	}

	@Override
	public Resultado<E> getById(PK id) {
		return getById(id, false);
	}

	@Override
	public Resultado<E> getById(PK id, boolean refresh) {
		return getById(id, refresh, false);
	}

	@Override
	public Resultado<E> getById(PK id, boolean refresh, boolean lock) {
		Resultado<E> resultado = new Resultado<E>();
		E object;
		object = getEntityManager().find(entityClass, id,
				lock ? LockModeType.PESSIMISTIC_WRITE : LockModeType.NONE);
		if (refresh) {
			return refresh(object);
		}
		resultado.set(object);
		return resultado;
	}

	@Override
	public Resultado<E> refresh(E entity) {
		Resultado<E> resultado = new Resultado<E>();
		getEntityManager().refresh(entity);
		resultado.set(entity);
		return resultado;
	}
}

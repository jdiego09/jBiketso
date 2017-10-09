package cr.co.coopeagri.opentouch.model.interfaces;

import org.datafx.util.EntityWithId;

import cr.co.coopeagri.opentouch.model.classes.Resultado;

public interface DaoBase<E extends EntityWithId<PK>, PK> {
	public Resultado<E> save(E entity);
	public Resultado<Void> delete(E entity);
	public Resultado<E> getById(PK id);
	public Resultado<E> getById(PK id, boolean refresh);
	public Resultado<E> getById(PK id, boolean refresh, boolean lock);
	public Resultado<E> refresh(E entity);
}

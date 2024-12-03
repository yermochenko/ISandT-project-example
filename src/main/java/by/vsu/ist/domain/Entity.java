package by.vsu.ist.domain;

import java.io.Serializable;
import java.util.Objects;

abstract public class Entity implements Serializable {
	private Long id;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(obj == null || getClass() != obj.getClass()) return false;
		Entity entity = (Entity) obj;
		return Objects.equals(id, entity.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
}

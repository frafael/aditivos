package util;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import model.Usuario;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

@Entity
@RevisionEntity(ExampleListener.class)
@Table(name="revinfo")
public class CustomRevisionEntity extends DefaultRevisionEntity {
 
	private static final long serialVersionUID = 1L;

	@OneToOne(fetch=FetchType.LAZY)
	private Usuario usuario;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
 
}

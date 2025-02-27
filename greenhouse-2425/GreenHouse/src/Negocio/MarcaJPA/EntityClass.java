package Negocio.MarcaJPA;

import javax.persistence.Entity;
import java.io.Serializable;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@Entity
@NamedQuery(name = "Negocio.MarcaJPA.EntityClass.findByid", query = "select obj from EntityClass obj where :id = obj.id ")
public class EntityClass implements Serializable {

	private static final long serialVersionUID = 0;

	public EntityClass() {
	}

	@Id
	private Integer id;
}
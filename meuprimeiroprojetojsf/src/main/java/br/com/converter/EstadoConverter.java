package br.com.converter;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidade.Estados;

@FacesConverter(forClass = Estados.class, value="estadoConverter")
public class EstadoConverter implements Converter, Serializable {

	
	private static final long serialVersionUID = -628943317877875062L;

	@Inject
	private EntityManager entityManager;
	
	@Override // Retorna objeto inteiro
	public Object getAsObject(FacesContext context, UIComponent component, String codigoEstado) {
		
		EntityManager entityManager = CDI.current().select(EntityManager.class).get();
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
			Estados estado = (Estados) entityManager.find(Estados.class, Long.parseLong(codigoEstado));
			return estado;
	}

	@Override // Retona apenas o código em String
	public String getAsString(FacesContext context, UIComponent component, Object estado) {
		
		if(estado == null) {
			return null;
		}if(estado instanceof Estados) {
			
			return ((Estados) estado).getId().toString();
		}else {
			return estado.toString();
		}
		
	}

}

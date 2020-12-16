package br.com.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.entidade.Estados;
import br.com.entidade.Pessoa;

@Named
public class IDaoPessoaImpl implements IDaoPessoa, Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private EntityManager entityManager;
	@Override
	public Pessoa consultarUsuario(String login, String senha) {
		
		Pessoa pessoa = null;
		try {
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		
			
		pessoa = (Pessoa) entityManager.createQuery("select p from Pessoa p where p.login = '" +
		login + "' and p.senha = '" + senha + "'").getSingleResult();

		entityTransaction.commit();
		
		return pessoa;
			}catch(Exception e) {
		e.printStackTrace();
		}
		return pessoa;
	}

	@Override
	public List<SelectItem> listaEstados() {
		
		List<SelectItem> selectItems =  new ArrayList<SelectItem>();
		
		
		List<Estados> estados = entityManager.createQuery("from Estados").getResultList();
		
		for (Estados estado : estados) {
			selectItems.add(new SelectItem(estado, estado.getNome()));
		}
		return selectItems;
	}

	@Override
	public List<SelectItem> listaFrameworks() {
		
	List<SelectItem> pessoaLista =  new ArrayList<SelectItem>();
		
		EntityTransaction entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
		List<Pessoa> pessoa = entityManager.createQuery("select frameworks from Pessoa").getResultList();
	
		return pessoaLista;
	
	}
}

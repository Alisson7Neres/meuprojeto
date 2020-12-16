package br.com.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-11-22T13:18:19.108-0200")
@StaticMetamodel(Cidades.class)
public class Cidades_ {
	public static volatile SingularAttribute<Cidades, Long> id;
	public static volatile SingularAttribute<Cidades, String> nome;
	public static volatile SingularAttribute<Cidades, Estados> estados;
}

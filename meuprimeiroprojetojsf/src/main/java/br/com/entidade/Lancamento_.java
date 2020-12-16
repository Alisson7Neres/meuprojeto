package br.com.entidade;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-11-22T13:18:19.144-0200")
@StaticMetamodel(Lancamento.class)
public class Lancamento_ {
	public static volatile SingularAttribute<Lancamento, Long> id;
	public static volatile SingularAttribute<Lancamento, String> numeroNotaFiscal;
	public static volatile SingularAttribute<Lancamento, String> empresaOrigem;
	public static volatile SingularAttribute<Lancamento, String> empresaDestino;
	public static volatile SingularAttribute<Lancamento, Pessoa> usuario;
}

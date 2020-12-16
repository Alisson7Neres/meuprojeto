package br.com.entidade;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2020-12-16T09:26:27.529-0200")
@StaticMetamodel(Pessoa.class)
public class Pessoa_ {
	public static volatile SingularAttribute<Pessoa, Long> id;
	public static volatile SingularAttribute<Pessoa, String> nome;
	public static volatile SingularAttribute<Pessoa, String> sobrenome;
	public static volatile SingularAttribute<Pessoa, Integer> idade;
	public static volatile SingularAttribute<Pessoa, Date> dataNascimento;
	public static volatile SingularAttribute<Pessoa, String> sexo;
	public static volatile SingularAttribute<Pessoa, String> FrameworkExibir;
	public static volatile SingularAttribute<Pessoa, Boolean> ativo;
	public static volatile SingularAttribute<Pessoa, String> login;
	public static volatile SingularAttribute<Pessoa, String> senha;
	public static volatile SingularAttribute<Pessoa, String> perfilUser;
	public static volatile SingularAttribute<Pessoa, String> nivelProgramador;
	public static volatile SingularAttribute<Pessoa, String> LinguagensExibir;
	public static volatile SingularAttribute<Pessoa, String> cep;
	public static volatile SingularAttribute<Pessoa, String> logradouro;
	public static volatile SingularAttribute<Pessoa, String> complemento;
	public static volatile SingularAttribute<Pessoa, String> bairro;
	public static volatile SingularAttribute<Pessoa, String> localidade;
	public static volatile SingularAttribute<Pessoa, String> uf;
	public static volatile SingularAttribute<Pessoa, Cidades> cidades;
}

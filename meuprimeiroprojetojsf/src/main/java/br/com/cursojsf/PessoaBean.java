package br.com.cursojsf;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.html.HtmlSelectOneMenu;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import br.com.dao.DaoGeneric;
import br.com.entidade.Cidades;
import br.com.entidade.Estados;
import br.com.entidade.Pessoa;
import br.com.jpautil.JPAUtil;
import br.com.repository.IDaoPessoa;

@ViewScoped
@Named(value = "pessoaBean")

public class PessoaBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Pessoa pessoa = new Pessoa();
	private List<Pessoa> pessoas = new ArrayList<Pessoa>();
	
	@Inject
	private DaoGeneric<Pessoa> daoGeneric;
	@Inject
	private IDaoPessoa iDaoPessoa;
	
	private List<SelectItem> frameworks;

	private List<SelectItem> estados;
	private List<SelectItem> cidades;
	
	@Inject
	private JPAUtil jpaUtil;
	
	public String salvar() throws IOException {
		if (pessoa.getSenha().trim() == null || pessoa.getSenha().isEmpty()) {
			mostrarMenssagem("PREENCHA A SENHA");
		} else if(pessoa.getEstados() == null && pessoa.getCidades() == null) {
			mostrarMenssagem("SELECIONE A ESTADO / SELECIONE A CIDADE");
		} else if(pessoa.getNivelProgramador() == null) {
			mostrarMenssagem("PREENCHA O NÍVEL DO PROGRAMADOR");
		} else if(pessoa.getFrameworks() == null) {
			mostrarMenssagem("PREENCHA A LINGUAGEM");
		} else if(pessoa.getLocalidade().trim().isEmpty()) {
			mostrarMenssagem("PREENCHA A LOCALIDADE");
		} else if(pessoa.getUf().trim().isEmpty()) {
		mostrarMenssagem("PREENCHA A UF");
		}
		
		else if(pessoa.getEstados() != null && pessoa.getCidades() != null 
				&& pessoa.getLocalidade() != null || pessoa.getUf() != null
				&& pessoa.getNivelProgramador() != null && pessoa.getFrameworks() != null) {
			
		try {
			pessoa = daoGeneric.merge(pessoa);
			mostrarMenssagem("Salvo com sucesso");
		} catch (Exception e) {
			e.printStackTrace();
			}
		}

		return "";
	}

	public String novo() {
		pessoa = new Pessoa();
		return "meuprimeiroprojetojsf.jsf";
	}

	public String remove() {
		daoGeneric.deletePorId(pessoa);
		if (true) {
			pessoa = new Pessoa();
			carregarPessoas();
		}
		return "";
	}

	@PostConstruct
	public void carregarPessoas() {
		pessoas = daoGeneric.getListEntity(Pessoa.class);
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void pesquisaCep(AjaxBehaviorEvent event) {
		try {
			// Monta URL
			URL url = new URL("https://viacep.com.br/ws/" +pessoa.getCep()+"/json/");
			// Abri a conexão
			URLConnection connection = url.openConnection();
			// Executa do lado do servidor
			InputStream is = connection.getInputStream();
			// Leitura de fluxo de dados
			BufferedReader br = new BufferedReader(new InputStreamReader(is,"UTF-8"));
			
			String cep = "";
			StringBuilder jsonCep = new StringBuilder();
			while((cep = br.readLine()) != null) {
				jsonCep.append(cep);
			}
			// Iniciando um novo objeto Gson
			 Pessoa gsonAux = new Gson().fromJson(jsonCep.toString(), Pessoa.class);
			
			pessoa.setCep(gsonAux.getCep());
			pessoa.setLogradouro(gsonAux.getLogradouro());
			pessoa.setComplemento(gsonAux.getComplemento());
			pessoa.setBairro(gsonAux.getBairro());
			pessoa.setLocalidade(gsonAux.getLocalidade());
			pessoa.setUf(gsonAux.getUf());
			
		}catch(Exception e){
			e.printStackTrace();
			mostrarMenssagem("Erro ao consultar CEP");
		}
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public DaoGeneric<Pessoa> getDaoGeneric() {
		return daoGeneric;
	}

	public void setDaoGeneric(DaoGeneric<Pessoa> daoGeneric) {
		this.daoGeneric = daoGeneric;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public String logar() {

		Pessoa pessoaUser = iDaoPessoa.consultarUsuario(pessoa.getLogin(), pessoa.getSenha());

		if (pessoaUser != null) {// achou o usuário
			// adicionar o usuário na sessão usuarioLogado
			FacesContext context = FacesContext.getCurrentInstance();
			ExternalContext externalContext = context.getExternalContext();
			externalContext.getSessionMap().put("usuarioLogado", pessoaUser);

			return "primeirapagina.jsf";
		} else if(pessoaUser == null) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nome ou senha inválidos", null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
		return "index.jsf";
	}
	
	public String deslogar() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		externalContext.getSessionMap().remove("usuarioLogado");
		@SuppressWarnings({"static-access" })
		HttpServletRequest httpServletRequest = (HttpServletRequest)
				context.getCurrentInstance().getExternalContext().getRequest();
		
		httpServletRequest.getSession().invalidate();
		
		return "index.jsf";
	}

	public boolean permiteAcesso(String acesso) {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = context.getExternalContext();
		Pessoa pessoaUser = (Pessoa) externalContext.getSessionMap().get("usuarioLogado");
		return pessoaUser.getPerfilUser().equals(acesso);
	}
	
	public String mostrarMenssagem(String msg) {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = new FacesMessage(msg);
		
		context.addMessage(null, message);
		
		return "";
	}
	
	public List<SelectItem> getEstados(){
		estados = iDaoPessoa.listaEstados();
		return estados;
	}
	
	public void carregaCidades(AjaxBehaviorEvent event) {

		 Estados estado = (Estados) ((HtmlSelectOneMenu)event.getSource()).getValue();
		
		 if(estado != null) {
			pessoa.setEstados(estado);
			@SuppressWarnings("unchecked")
			List<Cidades> cidades = jpaUtil.getEntityManager()
					.createQuery("from Cidades where estados.id = " + estado.getId()).getResultList();
			
			List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
			
			for (Cidades cidade : cidades) {
				selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
				}
			setCidades(selectItemsCidade);
			}
		}
	
	public String editar() {
		if(pessoa.getCidades() != null) {
			Estados estado = pessoa.getCidades().getEstados();
			pessoa.setEstados(estado);
			@SuppressWarnings("unchecked")
			List<Cidades> cidades = jpaUtil.getEntityManager()
					.createQuery("from Cidades where estados.id = " + estado.getId()).getResultList();
			
			List<SelectItem> selectItemsCidade = new ArrayList<SelectItem>();
			
			for (Cidades cidade : cidades) {
				selectItemsCidade.add(new SelectItem(cidade, cidade.getNome()));
				}
			setCidades(selectItemsCidade);
		}
		return "";
	}
	
	public void setCidades(List<SelectItem> cidades) {
		this.cidades = cidades;
	}

	public List<SelectItem> getCidades() {
		return cidades;
	}
	
	
	public void carregaFrameworks(AjaxBehaviorEvent event) {
	String codigoFrameworks = (String)	event.getComponent().getAttributes().get("submittedValue");
	if(codigoFrameworks != null) {
		System.out.println(codigoFrameworks);
		}
	}
	
	
	
	public List<SelectItem> getFrameworks(){
		frameworks = iDaoPessoa.listaFrameworks();
		return frameworks;
		
	}

}

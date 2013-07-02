package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;

import br.com.bronx.accesscontrol.interfaces.Profile;

@Entity
@Audited
@SequenceGenerator(name="sequence", sequenceName="perfil_sequence", allocationSize=1)
public class Perfil implements Profile {

	public int getAccessLevel() {
		return 0;
	}
	
	public List<String> getRoles() {
		List<String> roles = new ArrayList<String>();
		List<Papel> papeis = this.papeis;
		for (Papel papel : papeis) {
			roles.add(papel.getCodigo());
		}
		return roles;
	}
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="sequence")
	private Long id;

	@NotNull @Size( min = 2 , max = 128 )
	private String nome;
	
	@OneToOne(fetch=FetchType.LAZY) @NotNull
	private Empresa empresa;

	@ManyToMany(fetch=FetchType.EAGER) @Fetch(FetchMode.SUBSELECT)
	private List<Papel> papeis;
	
	public Long getId() {
		return id;
	}

	public void setId( Long id ) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
	
	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public List<Papel> getPapeis() {
		return papeis;
	}
	
	public void setPapeis(List<Papel> papeis) {
		this.papeis = papeis;
	}
	
}

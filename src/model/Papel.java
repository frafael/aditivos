package model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.envers.Audited;

@Entity
@Audited
@SequenceGenerator(name="sequence", sequenceName="papel_sequence", allocationSize=1)
public class Papel implements Comparable<Papel> {
	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="sequence")
	private Long id;

	@NotNull @Size( min = 2 , max = 128 )
	private String nome;
	
	@NotNull
	private String link;

	@OneToOne(fetch=FetchType.LAZY)
	private Papel papel;

	private Integer ordem;
	
	@NotNull @Size( min = 2 , max = 128 )
	private String codigo;
	
	@NotNull
	private boolean menu;
	
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
	
	public String getLink() {
		return link;
	}
	
	public void setLink(String link) {
		this.link = link;
	}
	
	public Papel getPapel() {
		return papel;
	}
	
	public void setPapel(Papel papel) {
		this.papel = papel;
	}
	
	public Integer getOrdem() {
		return ordem;
	}
	
	public void setOrdem(Integer ordem) {
		this.ordem = ordem;
	}

	public String getCodigo() {
		return codigo;
	}
	
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public boolean isMenu() {
		return menu;
	}
	
	public void setMenu(boolean menu) {
		this.menu = menu;
	}

	public int compareTo(Papel outro) {
		return this.getOrdem().compareTo(outro.getOrdem());
	}
}

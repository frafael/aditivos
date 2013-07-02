package model; 	

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.io.IOUtils;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.Email;

import util.ThumbGenerator;
import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

@Entity
@Audited
@SequenceGenerator(name="sequence", sequenceName="usuario_sequence", allocationSize=1)
public class Usuario implements Cloneable {

	@Id	@GeneratedValue(strategy=GenerationType.AUTO, generator="sequence")
	private Long id;
	
	@NotNull @Size(max = 255)
	private String nome;
	
	@NotNull @Size(max = 32)
	private String login;
	
	@NotNull @Size(max = 32)
	private String senha;
	
	@Size(max = 64) @Email
	private String email;
	
	@Size(max = 20)
	private String ramal;
	
	@Size(max = 50)
	private String celular;

	@NotNull @Temporal(TemporalType.DATE)
	private Date dataDeNascimento;

	@Transient
	private boolean aniversariante;
	
	@Transient
	private boolean aniversarianteDoMes;
	
	@Transient
	private Long qtdRecados = 0L;
	
	@Size(max = 20)
	private String placa;
	
	@OneToOne(fetch = FetchType.EAGER) @NotNull
	private Empresa empresa;
	
	@OneToOne(fetch = FetchType.EAGER)
	private Perfil perfil;
	
	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	private String fotoFileName;
	
	@NotNull
	private boolean ativo = true;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date ultimoLogin;
	
	private Long qtdDeAcessos = 0L;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRamal() {
		return ramal;
	}

	public void setRamal(String ramal) {
		this.ramal = ramal;
	}
	
	public String getCelular() {
		if( celular != null ) return celular.replaceAll("(\\d{2})(\\d{4})(\\d{4})", "($1) $2-$3");
		return "";
	}

	public void setCelular(String celular) {
//		if( celular.matches(".(\\d{2}).\\s(\\d{4})-(\\d{4})") )
			this.celular = celular.replaceAll("[^0-9]", "");
	}

	public String getPlaca() {
		if( placa != null ) return placa.replaceAll("(\\w{3})(\\d{4})","$1-$2");
		return "";
	}
	
	public void setPlaca(String placa) {
		if( placa.matches("(\\w{3})-(\\d{4})") )
			this.placa = placa.replaceAll("[^A-Za-z0-9]", "");
	}
	
	public Date getDataDeNascimento() {
		return dataDeNascimento;
	}

	public void setDataDeNascimento(Date dataDeNascimento) {
		this.dataDeNascimento = dataDeNascimento;
	}

	public void setFotoFileName(String fotoFileName) {
		this.fotoFileName = fotoFileName;
	}

	public String getFotoFileName() {
		return fotoFileName;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public Date getUltimoLogin() {
		return ultimoLogin;
	}

	public void setUltimoLogin(Date ultimoLogin) {
		this.ultimoLogin = ultimoLogin;
	}

	public Long getQtdDeAcessos() {
		return qtdDeAcessos;
	}

	public void setQtdDeAcessos(Long qtdDeAcessos) {
		this.qtdDeAcessos = qtdDeAcessos;
	}

	public boolean isAniversariante() {
		Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataDeNascimento);
		int mesDeAniversario = calendar.get(Calendar.MONTH);
		int diaDeAniversario = calendar.get(Calendar.DAY_OF_MONTH);
		Calendar diaDeHoje = Calendar.getInstance();
			calendar.setTime(new Date());
		int mesAtual = diaDeHoje.get(Calendar.MONTH);
		int diaHoje = diaDeHoje.get(Calendar.DAY_OF_MONTH);
		return aniversariante = mesDeAniversario == mesAtual && diaDeAniversario == diaHoje;
	}
	
	public boolean isAniversarianteDoMes() {
		Calendar calendar = Calendar.getInstance();
			calendar.setTime(dataDeNascimento);
		int mesDeAniversario = calendar.get(Calendar.MONTH);
		int diaDeAniversario = calendar.get(Calendar.DAY_OF_MONTH);
		Calendar diaDeHoje = Calendar.getInstance();
			calendar.setTime(new Date());
		int mesAtual = diaDeHoje.get(Calendar.MONTH);
		int diaHoje = diaDeHoje.get(Calendar.DAY_OF_MONTH);
		return aniversariante = mesDeAniversario == mesAtual && diaDeAniversario >= diaHoje;
	}

	public void setUserPlaca(String placa) {
		this.placa = placa;
	}

	public void setUserCelular(String celular) {
		this.celular = celular;
	}

	public void setEmpresaId(Long id) {
		if(this.empresa == null)
			this.empresa = new Empresa();
		this.getEmpresa().setId(id);
	}

	public Long getQtdRecados() {
		return qtdRecados;
	}

	public void setQtdRecados(Long qtdRecados) {
		this.qtdRecados = qtdRecados;
	}

	public void uploadFoto(String empresa, UploadedFile foto) throws IOException {
		String path = System.getenv("INTRANET_HOME");
		char separator = java.io.File.separatorChar;
		if( foto != null ) {
			String fileType = foto.getContentType().substring(0, foto.getContentType().indexOf("/"));
			String extensaoDaImagem = foto.getFileName().substring(foto.getFileName().lastIndexOf("."));

			if( fileType.equals("image") ) {
				this.fotoFileName = new Date().getTime() + extensaoDaImagem;
				File destino = new File(path + separator + empresa + separator + "imagens" + separator + "fotos_usuarios" + separator + this.fotoFileName);
				destino.createNewFile();

				InputStream stream = foto.getFile();
				IOUtils.copy(stream, new FileOutputStream(destino));
				
				File destino_thumb = new File(path + separator + empresa + separator + "imagens" + separator + "fotos_usuarios" + separator + "thumb_" + this.fotoFileName);
				ThumbGenerator.generate(destino, destino_thumb, 65, 64);

				System.out.println("Upload Finish >>> "+ destino.getAbsolutePath());
			}
		}
	}

	public void removeFotoAntiga( String empresa, String fotoAntiga ) throws IOException {
		String path = System.getenv("INTRANET_HOME");
		char separator = java.io.File.separatorChar;
		File foto = new File(path + separator + empresa + separator + "imagens" + separator + "fotos_usuarios" + separator + fotoAntiga);
		File foto_thumb = new File(path + separator + empresa + separator + "imagens" + separator + "fotos_usuarios" + separator + "thumb_"+fotoAntiga);
		if( foto.exists() && foto.delete() ) {
			System.out.println("Foto deletada com sucesso.");
		}
		if( foto_thumb.exists() && foto_thumb.delete() ) {
			System.out.println("Foto deletada com sucesso.");
		}
	}

	public Usuario clone() {
		Usuario usuario = null;
		try {
			usuario = (Usuario) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return usuario;
	}
}
package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.apache.commons.io.IOUtils;
import org.hibernate.envers.Audited;

import br.com.caelum.vraptor.interceptor.multipart.UploadedFile;

@Entity
@Audited
@SequenceGenerator(name="sequence", sequenceName="empresa_sequence", allocationSize=1)
public class Empresa {

	@Id @GeneratedValue(strategy=GenerationType.AUTO, generator="sequence")
	private Long id;

	@NotNull @Size(max = 64)
	private String nome;

	private boolean ativa = true;

	@NotNull
	private String endereco;

	@NotNull @Size(max = 8)
	private String numero;

	private String complemento;

	@NotNull @Size(max = 128)
	private String bairro;

	@NotNull @Size(max = 128)
	private String cidade;

	@NotNull @Size(min = 2, max = 2)
	private String uf;
	
	@NotNull @Size(max = 8)
	private String cep;

	@NotNull @Size( max = 10 )
	private String telefone;
	
	@Column(nullable = false,  columnDefinition = "character varying(50) default ''") 
	private String contexto;
	
	private String logoFileName;
	
	public String getContexto() {
		return contexto;
	}

	public void setContexto(String contexto) {
		this.contexto = contexto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome( String nome ) {
		this.nome = nome;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva( boolean ativa ) {
		this.ativa = ativa;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep.replaceAll("(\\d{2})(\\d{3})(\\d{3})", "$1.$2-$3");
	}

	public void setCep(String cep) {
//		if( cep.matches("(\\d{2}).(\\d{3})-(\\d{3})") ) 
			this.cep = cep.replaceAll("[^0-9]", "");
	}

	public String getTelefone() {
		if( telefone != null ) return telefone.replaceAll("(\\d{2,2})(\\d{4,4})(\\d{4,4})", "($1) $2-$3");
		return "";
	}

	public void setTelefone(String telefone) {
//		if( telefone.matches(".(\\d{2}).\\s(\\d{4})-(\\d{4})") )
			this.telefone= telefone.replaceAll("[^0-9]", "");
	}
	
	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}

	public String getLogoFileName() {
		return logoFileName;
	}
	
	public void uploadLogo(UploadedFile foto) throws IOException {
		String path = System.getenv("INTRANET_HOME");
		char separator = java.io.File.separatorChar;
		if( foto != null ) {
			String fileType = foto.getContentType().substring(0, foto.getContentType().indexOf("/"));
			String extensaoDaImagem = foto.getFileName().substring(foto.getFileName().lastIndexOf("."));

			if( fileType.equals("image") ) {
				this.logoFileName = new Date().getTime() + extensaoDaImagem;
				File destino = new File(path + separator + id + separator + "imagens" + separator + "logo" + separator + this.logoFileName);

				InputStream stream = foto.getFile();
				IOUtils.copy(stream, new FileOutputStream(destino));
				
				Thumbnails.of(destino)
			    .crop(Positions.CENTER)
				.size(58, 67)
			    .toFile(path + separator + id + separator + "imagens" + separator + "logo" + separator + this.logoFileName);
				
				System.out.println("Upload Finish >>> "+ destino.getAbsolutePath());
			}
		}
	}

	public void removeLogoAntiga( String empresa, String fotoAntiga ) throws IOException {
		String path = System.getenv("INTRANET_HOME");
		char separator = java.io.File.separatorChar;
		File foto = new File(path + separator + empresa + separator + "imagens" + separator + "logo" + separator + fotoAntiga);
		if( foto.exists() && foto.delete() ) {
			System.out.println("Foto deletada com sucesso.");
		}
	}
	
	public void newDir() {
		String intranet_home = System.getenv("INTRANET_HOME");
		char separator = java.io.File.separatorChar;
		String path = intranet_home + separator + id;
		File dir = new File(path);
	    dir.mkdir();
	    
	    path = intranet_home + separator + id + separator + "imagens" + separator + "conteudos";
	    File dirImgCont = new File(path);  
	    dirImgCont.mkdirs();
	    
	    path = intranet_home + separator + id + separator + "imagens" + separator + "fotos_usuarios";
	    File dirImgUsers = new File(path);  
	    dirImgUsers.mkdirs();
	    
	    path = intranet_home + separator + id + separator + "imagens" + separator + "logo";
	    File dirImgLogo = new File(path);  
	    dirImgLogo.mkdirs();
	    
	    path = intranet_home + separator + id + separator + "videos";
	    File dirVid = new File(path);  
	    dirVid.mkdirs();
	    
	    path = intranet_home + separator + id + separator + "arquivos";
	    File dirArq = new File(path);  
	    dirArq.mkdirs();
	}
}
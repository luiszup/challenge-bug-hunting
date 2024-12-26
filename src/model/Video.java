package model;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Video {
    private String titulo;
    private String descricao;
    private int duracao; // em minutos
    private String categoria;
    private Date dataPublicacao;

    private static List<String> categoriasValidas = Arrays.asList("Filme", "Série", "Documentário");

    public Video(String titulo, String descricao, int duracao, String categoria, Date dataPublicacao) {
        if (!categoriaValida(categoria)) {
            throw new IllegalArgumentException("Categoria inválida! As categorias válidas são " + categoriasValidas);
        }
        this.titulo = titulo;
        this.descricao = descricao;
        this.duracao = duracao;
        this.categoria = categoria;
        this.dataPublicacao = dataPublicacao;
    }

    public static boolean categoriaValida(String categoria) {
        List<String> categoriasValidas = Arrays.asList("filme", "série", "documentário");
        return categoriasValidas.contains(categoria.toLowerCase());
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return titulo + ";" + descricao + ";" + duracao + ";" + categoria + ";" + sdf.format(dataPublicacao);
    }

    public static Video fromString(String linha) {
        try {
            String[] partes = linha.split(";");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            return new Video(partes[0], partes[1], Integer.parseInt(partes[2]), partes[3], sdf.parse(partes[4]));
        } catch (Exception e) {
            return null; // Ignora erros de parsing
        }
    }
    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public int getDuracao() {
        return duracao;
    }

    public Date getDataPublicacao() {
        return dataPublicacao;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setCategoria(String categoria) {
        if (!categoriaValida(categoria)) {
            throw new IllegalArgumentException("Categoria inválida! As categorias válidas são " + categoriasValidas);
        }
        this.categoria = categoria;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setDataPublicacao(Date dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }
}
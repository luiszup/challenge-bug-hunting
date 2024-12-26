package service;

import model.Video;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.text.Normalizer;

public class VideoManager {
    private static final List<String> CATEGORIAS_VALIDAS = Arrays.asList("Filme", "Série", "Documentário");
    private final List<Video> videos;

    public VideoManager() {
        this.videos = new ArrayList<>();
    }

    public boolean isCategoriaValida(String categoria) {
        return CATEGORIAS_VALIDAS.contains(categoria);
    }

    public boolean isDuracaoValida(String duracao) {
        try {
            int valor = Integer.parseInt(duracao);
            return valor > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void addVideo(String titulo, String descricao, int duracao, String categoria, Date dataPublicacao) {
        if (!isCategoriaValida(categoria)) {
            throw new IllegalArgumentException("Categoria inválida! As categorias válidas são: " + CATEGORIAS_VALIDAS);
        }
        if (duracao > 0) {
            throw new IllegalArgumentException("Duração inválida! Digite um número inteiro positivo:");
        }
        Video video = new Video(titulo, descricao, duracao, categoria, dataPublicacao);
        videos.add(video);
    }

    public List<Video> listVideos() {
        return new ArrayList<>(videos);
    }

    public List<Video> searchByTitle(String query) {
        List<Video> resultados = new ArrayList<>();
        for (Video video : videos) {
            if (video.getTitulo().toLowerCase().contains(query.toLowerCase())) {
                resultados.add(video);
            }
        }
        return resultados;
    }
}

package service;

import model.Video;

import java.util.*;
import java.util.stream.Collectors;

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
        if (duracao < 0) {
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

    public void editVideo(String titulo, String novoTitulo, String novaDescricao, int novaDuracao, String novaCategoria, Date novaDataPublicacao) {
        for (Video video : videos) {
            if (video.getTitulo().equalsIgnoreCase(titulo)) {
                if (!isCategoriaValida(novaCategoria)) {
                    throw new IllegalArgumentException("Categoria inválida! As categorias válidas são " + CATEGORIAS_VALIDAS);
                }
            }

            if (novaDuracao < 0) {
                throw new IllegalArgumentException("Duração inválida! Digite um número inteiro positivo.");
            }

            video.setTitulo(novoTitulo);
            video.setDescricao(novaDescricao);
            video.setDuracao(novaDuracao);
            video.setCategoria(novaCategoria);
            video.setDataPublicacao(novaDataPublicacao);
            return;
        }
        throw new IllegalArgumentException("Vídeo com o título " + titulo + " não encontrado!");
    }

    public void deleteVideo(String titulo) {
        for (Video video : videos) {
            if (video.getTitulo().equalsIgnoreCase(titulo)) {
                videos.remove(video);
                System.out.println("O vídeo " + titulo + " foi removido com sucesso!");
                return;
            }
        }
        throw new IllegalArgumentException("Vídeo com título " + titulo + " não foi encontrado!");
    }

    public List<Video> filtroPorCategoria(String categoria) {
        if (!isCategoriaValida(categoria)) {
            throw new IllegalArgumentException("Categoria inválida! As categorias válidas são " + CATEGORIAS_VALIDAS);
        }

        List<Video> resultados = new ArrayList<>();
        for (Video video : videos) {
            if (video.getCategoria().equalsIgnoreCase(categoria)) {
                resultados.add(video);
            }
        }
        return resultados;
    }

    public List<Video> ordenarVideoPorData() {
        return videos.stream()
                .sorted(Comparator.comparing(Video::getDataPublicacao))
                .collect(Collectors.toList());
    }

    public Map<String, Object> gerarRelatorioEstatisticas() {
        Map<String, Object> relatorio = new HashMap<>();
        int totalVideos = videos.size();
        relatorio.put("totalVideos", totalVideos);
        int totalDuracao = videos.stream().mapToInt(Video::getDuracao).sum();
        relatorio.put("totalDuracao", totalDuracao);
        Map<String, Integer> videosPorCategoria = new HashMap<>();
        for (String categoria : CATEGORIAS_VALIDAS) {
            int contador = (int) videos.stream()
                    .filter(video -> video.getCategoria().equalsIgnoreCase(categoria))
                    .count();
            videosPorCategoria.put(categoria, contador);
        }
        relatorio.put("videosPorCategoria", videosPorCategoria);
        return relatorio;
    }
}

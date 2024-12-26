package main;

import model.Video;
import repository.FileVideoRepository;
import service.VideoService;
import service.VideoServiceImpl;
import strategy.SearchStrategy;
import strategy.TitleSearchStrategy;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VideoService videoService = new VideoServiceImpl(new FileVideoRepository("videos.txt"));
        SearchStrategy searchStrategy = new TitleSearchStrategy();
        int opcao;

        do {
            System.out.println("\n=== Sistema de Gerenciamento de Vídeos ===");
            System.out.println("1. Adicionar vídeo");
            System.out.println("2. Listar vídeos");
            System.out.println("3. Pesquisar vídeo por título");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir a quebra de linha

            switch (opcao) {
                case 1:
                    String titulo;
                    while (true) {
                        try {
                            System.out.print("Digite o título do vídeo: ");
                            titulo = scanner.nextLine();

                            if (titulo == null || titulo.trim().isEmpty()) {
                                throw new IllegalArgumentException("O título está vazio. Por favor, digite um título válido");
                            }

                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    String descricao;
                    while (true) {
                        try {
                            System.out.print("Digite a descrição do vídeo: ");
                            descricao = scanner.nextLine();

                            if (descricao == null || descricao.trim().isEmpty()) {
                                throw new IllegalArgumentException("A descrição está vazia. Por favor, digite uma descrição válida");
                            }

                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    int duracao = -1;
                    while (duracao < 0) {
                        try {
                            System.out.print("Digite a duração do vídeo (em minutos): ");
                            String input = scanner.nextLine();
                            duracao = Integer.parseInt(input);
                            if (duracao < 0) {
                                System.out.println("Tempo de duração inválido! Por favor, digite um número inteiro");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Valor inválido! Por favor, digite um número inteiro");
                        }
                    }

                    String categoria;
                    while (true) {
                        try {
                            System.out.print("Digite a categoria do vídeo: ");
                            categoria = scanner.nextLine();

                            if (categoria== null || categoria.trim().isEmpty()) {
                                throw new IllegalArgumentException("A categoria está vazia. Por favor, digite uma categoria válida");
                            }

                            break;
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                    }


                    System.out.print("Digite a data de publicação (dd/MM/yyyy): ");
                    Date dataPublicacao = null;

                    while (dataPublicacao == null) {
                        try {
                            String dataStr = scanner.nextLine();
                            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                            sdf.setLenient(false);
                            dataPublicacao = sdf.parse(dataStr);
                        } catch (java.text.ParseException e) {
                            System.out.println("Formato de data inválido! Utilize o formato dd/mm/yyyy.");
                        }
                    }

                    try {
                        Video video = new Video(titulo, descricao, duracao, categoria, dataPublicacao);
                        videoService.addVideo(video);
                        System.out.println("Vídeo adicionado com sucesso!");
                    } catch (IllegalArgumentException e) {
                        System.out.println("Erro ao adicionar vídeo");
                    }

                    break;

                case 2:
                    List<Video> videos = videoService.listVideos();
                    for (Video video : videos) {
                        System.out.println("Título: " + video.getTitulo());
                        System.out.println("Descrição: " + video.getDescricao());
                        System.out.println("Categoria: " + video.getCategoria());
                        System.out.println("Duração: " + video.getDuracao());
                        System.out.println("Data da publicação: " + video.getDataPublicacao());
                        System.out.println();
                    }
                    break;

                case 3:
                    System.out.print("Digite o título para busca: ");
                    String query = scanner.nextLine();
                    List<Video> resultados = searchStrategy.search(videoService.listVideos(), query);
                    for (Video video : resultados) {
                        System.out.println("Título: " + video.getTitulo());
                        System.out.println("Descrição: " + video.getDescricao());
                        System.out.println("Categoria: " + video.getCategoria());
                        System.out.println("Duração: " + video.getDuracao());
                        System.out.println("Data da publicação: " + video.getDataPublicacao());
                        System.out.println();
                    }
                    break;

                case 4:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 4);

        scanner.close();
    }
}
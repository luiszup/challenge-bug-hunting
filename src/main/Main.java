package main;

import model.Video;
import repository.FileVideoRepository;
import service.VideoService;
import service.VideoServiceImpl;
import strategy.SearchStrategy;
import strategy.TitleSearchStrategy;
import service.VideoManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        VideoService videoService = new VideoServiceImpl(new FileVideoRepository("videos.txt"));
        SearchStrategy searchStrategy = new TitleSearchStrategy();
        VideoManager videoManager = new VideoManager();
        int opcao;

        do {
            System.out.println("\n=== Sistema de Gerenciamento de Vídeos ===");
            System.out.println("1. Adicionar vídeo");
            System.out.println("2. Listar vídeos");
            System.out.println("3. Pesquisar vídeo por título");
            System.out.println("4. Editar vídeo");
            System.out.println("5. Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    try {
                        System.out.print("Digite o título do vídeo: ");
                        String titulo = scanner.nextLine();

                        System.out.print("Digite a descrição do vídeo: ");
                        String descricao = scanner.nextLine();

                        System.out.print("Digite a duração do vídeo (em minutos): ");
                        String duracaoStr = scanner.nextLine();

                        if (!videoManager.isDuracaoValida(duracaoStr)) {
                            System.out.println("Duração inválida! A duração deve ser um número inteiro positivo:");
                            break;
                        }

                        int duracao = Integer.parseInt(duracaoStr);

                        System.out.print("Digite a categoria do vídeo (Filme, Série, Documentário): ");
                        String categoria = scanner.nextLine();

                        System.out.print("Digite a data de publicação (dd/MM/yyyy): ");
                        String dataStr = scanner.nextLine();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date dataPublicacao = sdf.parse(dataStr);

                        videoManager.addVideo(titulo, descricao, duracao, categoria, dataPublicacao);
                        Video video = new Video(titulo, descricao, duracao, categoria, dataPublicacao);
                        videoService.addVideo(video);
                        System.out.println("Vídeo adicionado com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 2:
                    List<Video> videos = videoManager.listVideos();
                    if (videos.isEmpty()) {
                        System.out.println("Nenhum vídeo encontrado");
                    } else {
                        for (Video video: videos) {
                            System.out.println("Título: " + video.getTitulo());
                            System.out.println("Descrição: " + video.getDescricao());
                            System.out.println("Categoria: " + video.getCategoria());
                            System.out.println("Duração: " + video.getDuracao());
                            System.out.println("Data da publicação: " + video.getDataPublicacao());
                            System.out.println();
                        }
                    }
                    break;

                case 3:
                    System.out.print("Digite o título para busca: ");
                    String query = scanner.nextLine();
                    List<Video> resultados = videoManager.searchByTitle(query);
                    
                    if (query == null || query.trim().isEmpty()) {
                        System.out.println("A busca não pode estar vazia! Por favor, digite um título.");
                    } else if (resultados.isEmpty()) {
                        System.out.println("Nenhum vídeo com este título foi encontrado");
                    } else {
                        for (Video video : resultados) {
                            System.out.println("Título: " + video.getTitulo());
                            System.out.println("Descrição: " + video.getDescricao());
                            System.out.println("Categoria: " + video.getCategoria());
                            System.out.println("Duração: " + video.getDuracao());
                            System.out.println("Data da publicação: " + video.getDataPublicacao());
                            System.out.println();
                        }
                    }
                    break;

                case 4:
                    try {
                        System.out.println("Digite o título do vídeo que deseja editar: ");
                        String titulo = scanner.nextLine();
                        System.out.println("Digite o novo título do vídeo: ");
                        String novoTitulo = scanner.nextLine();
                        System.out.println("Digite a nova descrição do vídeo:");
                        String novaDescricao = scanner.nextLine();
                        System.out.println("Digite a nova duração do vídeo (em minutos):");
                        String novaDuracaoStr = scanner.nextLine();

                        if (!videoManager.isDuracaoValida(novaDuracaoStr)) {
                            System.out.println("Duração inválida! A duração deve ser um número inteiro positivo.");
                            break;
                        }

                        int novaDuracao = Integer.parseInt(novaDuracaoStr);

                        System.out.println("Digite a nova categoria do vídeo (Filme, Série, Documentário):");
                        String novaCategoria = scanner.nextLine();
                        System.out.println("Digite a nova data de publicação (dd/mm/yyyy): ");
                        String novaDataStr = scanner.nextLine();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        Date novaDataPublicacao = sdf.parse(novaDataStr);

                        videoManager.editVideo(titulo, novoTitulo, novaDescricao, novaDuracao, novaCategoria, novaDataPublicacao);
                        System.out.println("Vídeo editado com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("Saindo do sistema...");
                    break;

                default:
                    System.out.println("Opção inválida.");
                    break;
            }
        } while (opcao != 5);

        scanner.close();
    }
}
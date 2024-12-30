package repository;

import model.Video;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileVideoRepository implements VideoRepository {
    private FileHandler fileHandler;

    public FileVideoRepository(String filePath) {
        this.fileHandler = new FileHandler(filePath);
        this.fileHandler.createFileIfNotExists();
    }

    @Override
    public void save(Video video) {
        fileHandler.writeLine(video.toString(), true);
    }

    @Override
    public List<Video> findAll() {
        List<Video> videos = new ArrayList<>();
        List<String> lines = fileHandler.readAllLines();
        for (String line : lines) {
            Video video = Video.fromString(line);
            if (video != null) {
                videos.add(video);
            }
        }
        return videos;
    }
}
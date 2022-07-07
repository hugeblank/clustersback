package dev.hugeblank.clustersback.resources;

import com.google.common.collect.Sets;
import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Predicate;

public class CBResourcePack extends AbstractFileResourcePack {

    private final String name;

    private static final Map<String, Path> FILES = new HashMap<>();

    public static void addResourceFile(Path path) {
        FILES.put(path.toString(), path);
    }

    public static void addResourceFile(String pathName, Path path) {
        FILES.put(pathName, path);
    }

    private CBResourcePack(String name) {
        super(null);
        this.name = name;
    }

    public static CBResourcePack create(String name) {
        return new CBResourcePack(name);
    }

    @Override
    protected InputStream openFile(String filename) throws IOException {

        for (Map.Entry<String, Path> file : FILES.entrySet()) {
            if (file.getKey().equals(filename)) {
                return Files.newInputStream(file.getValue());
            }
        }
        throw new FileNotFoundException("\"" + filename + "\" in Clustersback resource pack");
    }

    @Override
    protected boolean containsFile(String filename) {
        for (Map.Entry<String, Path> file : FILES.entrySet()) {
            if (file.getKey().replace(file.getValue().getFileSystem().getSeparator(), "/").equals(filename)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Collection<Identifier> findResources(ResourceType type, String namespace, String path, int depth, Predicate<String> predicate) {
        if (!namespace.equals("minecraft")) {
            return Collections.emptyList();
        }

        List<Identifier> ids = new ArrayList<>();

        for (Map.Entry<String, Path> p : FILES.entrySet()) {
            String separator = p.getValue().getFileSystem().getSeparator();
            if (p.getKey().contains(type.getDirectory() + separator + namespace + separator + path)) {
                ids.add(new Identifier(namespace,
                        p
                                .getKey()
                                .replace(type.getDirectory() + separator + namespace + separator, "")
                                .replace(separator, "/")
                ));
            }
        }

        return ids;
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return Sets.newHashSet("minecraft");
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void close() {

    }
}

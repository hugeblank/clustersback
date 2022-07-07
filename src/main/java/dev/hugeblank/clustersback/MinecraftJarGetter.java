package dev.hugeblank.clustersback;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

// "I can't believe it's not against the EULA!"
// Java 8-compliant logic for downloading an entire game version.
public class MinecraftJarGetter {

    public static void getVersion(String version, Path path) {
        try {
            JsonElement versionManifest = new JsonParser().parse( // Parse the launchermeta...
                    getContentsAsString("http://launchermeta.mojang.com/mc/game/version_manifest_v2.json")
            );
            for (JsonElement jsonElement : versionManifest.getAsJsonObject().getAsJsonArray("versions")) {
                JsonObject e = jsonElement.getAsJsonObject(); // Find the right version...
                if (e.getAsJsonPrimitive("id").getAsString().equals(version)) {
                    Files.createDirectories(path.getParent());
                    Files.createFile(path);
                    URL url = new URL(
                            new JsonParser()
                                    .parse(
                                            getContentsAsString( // Get the url for that version data...
                                                    e
                                                            .getAsJsonPrimitive("url")
                                                            .getAsString()
                                                            .replace("https://", "http://")
                                            )
                                    )
                                    .getAsJsonObject()
                                    .getAsJsonObject("downloads")
                                    .getAsJsonObject("client") // Get the url for the client jar...
                                    .getAsJsonPrimitive("url")
                                    .getAsString()
                                    .replace("https://", "http://")
                    );
                    write(path, url.openStream()); // Cache that bad boy!
                }
            }
        } catch (Throwable e) {
            // TODO: Make this smarter, use custom exception
            throw new RuntimeException("Error finding version " + version, e);
        }
    }

    private static void write(Path p, InputStream istream) throws IOException {
        final byte[] BUF = new byte[8192];
        OutputStream ostream = Files.newOutputStream(p, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        int read;
        while ((read = istream.read(BUF)) != -1) {
            ostream.write(BUF, 0, read);
        }
        ostream.close(); istream.close();

    }

    private static String getContentsAsString(String urlStr) throws IOException {
        URL url = new URL(urlStr);
        Stream<String> wtf = new BufferedReader(new InputStreamReader(url.openStream())).lines();
        StringBuilder out = new StringBuilder();
        wtf.forEach(out::append);
        return out.toString();
    }
}

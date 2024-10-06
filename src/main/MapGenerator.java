package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MapGenerator {

    // Dimensions de la map
    private static final int WIDTH = 60;
    private static final int HEIGHT = 32;

    // Codes des tiles
    private static final int DIRT = 0;  // Terre
    private static final int WATER = 1; // Eau
    private static final int WALL = 2;  // Mur
    private static final int TREE = 4;  // Arbre

    private static int[][] map = new int[WIDTH][HEIGHT];


    // Générer la map avec terre, murs, eau et arbres
    public void generateMap() {
        // Initialiser la map avec de la terre (DIRT) partout
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                map[x][y] = DIRT;
            }
        }

        // Ajouter des murs (WALL) sur les contours de la map
        for (int x = 0; x < WIDTH; x++) {
            map[x][0] = WALL;           // Mur haut
            map[x][HEIGHT - 1] = WALL;  // Mur bas
        }
        for (int y = 0; y < HEIGHT; y++) {
            map[0][y] = WALL;           // Mur gauche
            map[WIDTH - 1][y] = WALL;   // Mur droit
        }

        // Placer l'eau et les arbres de manière aléatoire mais "logique"
        placeWater();
        placeTrees();

    }

    // Placer des zones d'eau de manière aléatoire et groupée
    public static void placeWater() {
        Random random = new Random();
        int waterPatches = 6; // Nombre de groupes d'eau

        for (int i = 0; i < waterPatches; i++) {
            // Déterminer un point de départ pour un groupe d'eau
            int startX = random.nextInt(WIDTH - 4) + 2;  // Limiter pour éviter les bords
            int startY = random.nextInt(HEIGHT - 4) + 2;

            // Étendre l'eau à partir du point de départ
            for (int j = 0; j < 8; j++) { // 5 cases d'eau aléatoires autour du point
                int newX = startX + random.nextInt(3) - 1; // Étendre dans un rayon de 1 case
                int newY = startY + random.nextInt(3) - 1;

                if (newX > 1 && newX < WIDTH - 1 && newY > 1 && newY < HEIGHT - 1) {
                    map[newX][newY] = WATER;
                }
            }
        }
    }

    // Placer des arbres de manière aléatoire
    public static void placeTrees() {
        Random random = new Random();
        int numberOfTrees = 30; // Nombre d'arbres à placer

        for (int i = 0; i < numberOfTrees; i++) {
            int x = random.nextInt(WIDTH - 2) + 1;  // Pour éviter les bords
            int y = random.nextInt(HEIGHT - 2) + 1;

            // S'assurer que l'arbre n'est pas placé dans l'eau ou sur un mur
            if (map[x][y] == DIRT) {
                map[x][y] = TREE;
            }
        }
    }

    // Afficher la map dans la console
    public void displayMap() {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                System.out.print(map[x][y] + " ");
            }
            System.out.println();
        }
    }

    // Sauvegarder la map dans un fichier .txt
    public void saveMapToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int y = 0; y < HEIGHT; y++) {
                for (int x = 0; x < WIDTH; x++) {
                    writer.write(map[x][y] + " ");
                }
                writer.newLine();
            }
            writer.flush(); // Forcer l'écriture des données
            System.out.println("Map sauvegardée dans " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

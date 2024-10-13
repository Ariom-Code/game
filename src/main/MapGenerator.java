package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapGenerator {

    // Dimensions de la map
    private static final int WIDTH = 60;
    private static final int HEIGHT = 32;

    private static final int DIRT = 0;  // Terre
    private static final int WATER = 1; // Eau
    private static final int WALL = 2;  // Mur
    private static final int TREE = 4;  // Arbre

    private static int[][] map = new int[WIDTH][HEIGHT];

    // Liste des coordonnées protégées
    private List<int[]> protectedTiles = new ArrayList<>();

    // Ajouter une tuile protégée (exclure des générations)
    public void addProtectedTile(int x, int y) {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) {
            protectedTiles.add(new int[]{x, y}); //création d'un tableau qui contient les coordonnées de la tile
        }
    }

    // Vérifier si une tuile est protégée
    private boolean isProtected(int x, int y) {
        for (int[] tile : protectedTiles) {
            if (tile[0] == x && tile[1] == y) {//regarder les valeurs 0 et 1 de la liste
                return true; // Coordonnées protégées
            }
        }
        return false;
    }

    // Générer la map avec terre, murs, eau et arbres
    public void generateMap() {
        // Initialiser la map avec de la terre (DIRT) partout
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                if (!isProtected(x, y)) { // Ne pas générer sur les tuiles protégées
                    map[x][y] = DIRT;
                }
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

        placeWater(8);
        expandWater();
        placeTrees(20);
    }

    // Placer des zones d'eau de manière aléatoire et groupée
    public void placeWater(int input) {
        Random random = new Random();
        int waterPatches = input; // Nombre de groupes d'eau

        for (int i = 0; i < waterPatches; i++) {
            // Déterminer un point de départ pour un groupe d'eau
            int startX = random.nextInt(WIDTH - 4) + 2;  // Limiter pour éviter les bords
            int startY = random.nextInt(HEIGHT - 4) + 2;

            // Étendre l'eau à partir du point de départ
            for (int j = 0; j < 15; j++) { // 5 cases d'eau aléatoires autour du point
                int newX = startX + random.nextInt(3) - 1; // Étendre dans un rayon de 1 case
                int newY = startY + random.nextInt(3) - 1;

                if (newX > 1 && newX < WIDTH - 1 && newY > 1 && newY < HEIGHT - 1) {
                    if (!isProtected(newX, newY)) { // Ne pas placer sur les tuiles protégées
                        map[newX][newY] = WATER;
                    }
                }
            }
        }
    }
    // Méthode qui étend l'eau aux cases adjacentes de terre si elles sont entourées par de l'eau
    private void expandWater() {
        boolean[][] newWater = new boolean[WIDTH][HEIGHT];

        for (int y = 1; y < HEIGHT - 1; y++) {
            for (int x = 1; x < WIDTH - 1; x++) {
                if (map[x][y] == DIRT) {
                    // Vérifier si l'eau est présente dans les cases adjacentes
                    if (isWaterAround(x, y)) {
                        newWater[x][y] = true;
                    }
                }
            }
        }

        // Appliquer les changements d'eau
        for (int y = 1; y < HEIGHT - 1; y++) {
            for (int x = 1; x < WIDTH - 1; x++) {
                if (newWater[x][y]) {
                    map[x][y] = WATER;
                }
            }
        }
    }

    // Méthode pour vérifier si une case est entourée par de l'eau
    private boolean isWaterAround(int x, int y) {
        // Vérifier les cases adjacentes : haut, bas, gauche, droite, et diagonales
        return (map[x - 1][y] == WATER || map[x + 1][y] == WATER ||
                map[x][y - 1] == WATER || map[x][y + 1] == WATER ||
                map[x - 1][y - 1] == WATER || map[x + 1][y - 1] == WATER ||
                map[x - 1][y + 1] == WATER || map[x + 1][y + 1] == WATER);
    }

    // Placer des arbres de manière aléatoire
    public void placeTrees(int input) {
        Random random = new Random();
        int numberOfTrees = input; // Nombre d'arbres à placer

        for (int i = 0; i < numberOfTrees; i++) {
            int x = random.nextInt(WIDTH - 2) + 1;  // Pour éviter les bords
            int y = random.nextInt(HEIGHT - 2) + 1;

            // S'assurer que l'arbre n'est pas placé dans l'eau, sur un mur ou une tuile protégée
            if (map[x][y] == DIRT && !isProtected(x, y)) {
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

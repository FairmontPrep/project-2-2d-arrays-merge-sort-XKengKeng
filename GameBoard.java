import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class GameBoard extends JFrame {
    public int Width = 21; // Number of columns (x-coordinate)
    public int Height = 19; // Number of rows (y-coordinate)
    public int SquareSize = 40; // Size of each square in pixels

    private JPanel[][] squares = new JPanel[Width][Height]; // 2D array for board
    private String[][] piecesArray; // 2D array = color hex code::board position

    public GameBoard() {
        setTitle("Miku");
        setSize(Width * SquareSize, Height * SquareSize); // Set window size based on grid dimensions
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(Height, Width)); // Use GridLayout for the board layout

        // Initialize the 2D array of panels
        for (int y = 0; y < Height; y++) {
            for (int x = 0; x < Width; x++) {
                squares[x][y] = new JPanel();
                squares[x][y].setPreferredSize(new Dimension(SquareSize, SquareSize)); // Set square size
                if ((x + y) % 2 == 0) {
                    squares[x][y].setBackground(Color.WHITE); // Color for even squares
                } else {
                    squares[x][y].setBackground(Color.WHITE); // Color for odd squares
                }
                add(squares[x][y]); // Add each square to the board
            }
        }

        // Initialize Pokémon pieces array
        piecesArray = new String[Width * Height][2];  // Your array should be at least 2 columns
        loadPieces();

        // Shuffle the piecesArray initially
        shufflePiecesArray();

        // Initially populate the board with pieces (unsorted)
        populateBoard();

        // Sort the piecesArray using merge sort
        sortImages(squares);

        // Repopulate the board with sorted pieces
        populateBoard();
    }

    // Helper method to map coordinates to numbers
    private int getNumber(int x, int y) {
        return y * Width + x; // Map (x, y) to a unique number
    }

    // Shuffle the piecesArray
    private void shufflePiecesArray() {
        Random rand = new Random();
        for (int i = piecesArray.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            // Swap piecesArray[i] and piecesArray[j]
            String[] temp = piecesArray[i];
            piecesArray[i] = piecesArray[j];
            piecesArray[j] = temp;
        }
    }

    // Merge sort implementation
    private void mergeSort(String[][] arr, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(arr, left, mid); // Sort left half
            mergeSort(arr, mid + 1, right); // Sort right half
            merge(arr, left, mid, right); // Merge the two halves
        }
    }

    // Merge two halves
    private void merge(String[][] arr, int left, int mid, int right) {
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Create temporary arrays
        String[][] leftArr = new String[n1][2];
        String[][] rightArr = new String[n2][2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArr[i] = arr[left + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArr[j] = arr[mid + 1 + j];
        }

        // Merge the temporary arrays
        int i = 0, j = 0, k = left;
        while (i < n1 && j < n2) {
            if (Integer.parseInt(leftArr[i][1]) <= Integer.parseInt(rightArr[j][1])) {
                arr[k] = leftArr[i];
                i++;
            } else {
                arr[k] = rightArr[j];
                j++;
            }
            k++;
        }

        // Copy remaining elements of leftArr (if any)
        while (i < n1) {
            arr[k] = leftArr[i];
            i++;
            k++;
        }

        // Copy remaining elements of rightArr (if any)
        while (j < n2) {
            arr[k] = rightArr[j];
            j++;
            k++;
        }
    }

    public JPanel[][] sortImages(JPanel[][] finalPositions) {
        mergeSort(piecesArray, 0, piecesArray.length - 1);
        return finalPositions;
    }

    private void populateBoard() {
        int pieceRow = 0; // Keeps track of the current piece in piecesArray
        int squareName = 0; // Keeps track of the current square number (0 to 398)

        for (int y = 0; y < Height; y++) { // Iterate over rows (y-coordinate)
            for (int x = 0; x < Width; x++) { // Iterate over columns (x-coordinate)
                int pokePosition = Integer.parseInt(piecesArray[pieceRow][1]);

                if (squareName == pokePosition) {
                    String hexCode = piecesArray[pieceRow][0]; // Hex color code
                    String number = piecesArray[pieceRow][1]; // HP value

                    // Convert hex code to Color
                    Color color = Color.decode(hexCode);

                    // Set the background color of the square
                    squares[x][y].setBackground(color);

                    // Add HP text to the square
                    JLabel textLabel = new JLabel(number, SwingConstants.CENTER);
                    textLabel.setForeground(Color.BLACK); // Make text black and center bottom
                    System.out.println("Adding 1 piece: " + number);
                    squares[x][y].setLayout(new BorderLayout());
                    //squares[x][y].add(textLabel, BorderLayout.CENTER);//display number

                    pieceRow++;
                }
                squareName++;
            }
        }

        revalidate(); // Ensure layout updates
        repaint(); // Refresh view
    }

    private void loadPieces() {
        // Assign colors/images to numbers
        // White
        int[][] whiteCoords = {{1,1}, {2,1}, {3,1}, {4,1}, {18,1}, {19,1}, {20,1}, {21,1}, {1,2}, {2,2}, {20,2}, {21,2}, {1,3}, {2,3}, {20,3}, {21,3}, {1,4}, {21,4}, {1,5}, {21,5}, {1,6}, {21,6}, {6,13}, {16,13}, {1,14}, {6,14}, {7,14}, {8,14}, {14,14}, {15,14}, {16,14}, {21,14}, {1,15}, {6,15}, {7,15}, {15,15}, {16,15}, {21,15}, {1,16}, {2,16}, {6,16}, {16,16}, {20,16}, {21,16}, {1,17}, {2,17}, {3,17}, {6,17}, {16,17}, {19,17}, {20,17}, {21,17}, {1,18}, {2,18}, {3,18}, {4,18}, {5,18}, {6,18}, {7,18}, {15,18}, {16,18}, {17,18}, {18,18}, {19,18}, {20,18}, {21,18}, {1,19}, {2,19}, {3,19}, {4,19}, {5,19}, {6,19}, {7,19}, {8,19}, {9,19}, {11,19}, {13,19}, {14,19}, {15,19}, {16,19}, {17,19}, {18,19}, {19,19}, {20,19}, {21,19}};
        for (int[] coord : whiteCoords) {
            int num = getNumber(coord[0] - 1, coord[1] - 1); // Adjust for 0-based indexing
            piecesArray[num][0] = "#FFFFFF"; // White hex code
            piecesArray[num][1] = String.valueOf(num);
        }

        // Black
        int[][] blackCoords = {{5,1}, {6,1}, {7,1}, {8,1}, {9,1}, {10,1}, {11,1}, {12,1}, {13,1}, {14,1}, {15,1}, {16,1}, {17,1}, {3,2}, {4,2}, {18,2}, {19,2}, {3,3}, {19,3}, {2,4}, {20,4}, {2,5}, {20,5}, {2,6}, {20,6}, {1,7}, {21,7}, {1,8}, {21,8}, {1,9}, {8,9}, {14,9}, {21,9}, {1,10}, {8,10}, {14,10}, {21,10}, {1,11}, {8,11}, {14,11}, {21,11}, {1,12}, {5,12}, {6,12}, {16,12}, {17,12}, {21,12}, {1,13}, {5,13}, {7,13}, {8,13}, {9,13}, {13,13}, {14,13}, {15,13}, {17,13}, {21,13}, {2,14}, {5,14}, {9,14}, {13,14}, {17,14}, {20,14}, {2,15}, {5,15}, {8,15}, {14,15}, {17,15}, {20,15}, {3,16}, {5,16}, {7,16}, {15,16}, {17,16}, {19,16}, {4,17}, {5,17}, {7,17}, {15,17}, {17,17}, {18,17}, {8,18}, {9,18}, {10,18}, {11,18}, {12,18}, {13,18}, {14,18}, {10,19}, {12,19}};
        for (int[] coord : blackCoords) {
            int num = getNumber(coord[0] - 1, coord[1] - 1); // Adjust for 0-based indexing
            piecesArray[num][0] = "#000000"; // Black hex code
            piecesArray[num][1] = String.valueOf(num);
        }

        // Aqua
        int[][] aquaCoords = {{5,2}, {9,2}, {10,2}, {11,2}, {12,2}, {13,2}, {17,2}, {4,3}, {8,3}, {9,3}, {10,3}, {11,3}, {12,3}, {13,3}, {14,3}, {18,3}, {3,4}, {7,4}, {8,4}, {9,4}, {10,4}, {11,4}, {12,4}, {13,4}, {14,4}, {15,4}, {19,4}, {3,5}, {7,5}, {8,5}, {10,5}, {11,5}, {12,5}, {14,5}, {15,5}, {19,5}, {3,6}, {4,6}, {6,6}, {7,6}, {10,6}, {11,6}, {12,6}, {15,6}, {16,6}, {18,6}, {19,6}, {3,7}, {7,7}, {11,7}, {12,7}, {15,7}, {19,7}, {11,8}, {12,8}, {12,9}, {11,15}, {4,16}, {11,16}, {18,16}, {10,17}, {12,17}};
        for (int[] coord : aquaCoords) {
            int num = getNumber(coord[0] - 1, coord[1] - 1); // Adjust for 0-based indexing
            piecesArray[num][0] = "#7DD3D2"; // Aqua hex code
            piecesArray[num][1] = String.valueOf(num);
        }

        // Dark Grey
        int[][] darkGreyCoords = {{6,2}, {8,2}, {14,2}, {16,2}, {5,3}, {7,3}, {15,3}, {17,3}, {4,4}, {6,4}, {16,4}, {18,4}, {4,5}, {5,5}, {17,5}, {18,5}, {5,6}, {17,6}, {5,9}, {17,9}, {5,10}, {17,10}, {6,11}, {16,11}, {11,13}, {10,14}, {12,14}, {9,15}, {13,15}, {8,17}, {9,17}, {11,17}, {13,17}, {14,17}};
        for (int[] coord : darkGreyCoords) {
            int num = getNumber(coord[0] - 1, coord[1] - 1); // Adjust for 0-based indexing
            piecesArray[num][0] = "#383B32"; // Dark Grey hex code
            piecesArray[num][1] = String.valueOf(num);
        }

        // Grey
        int[][] greyCoords = {{10,13}, {12,13}, {10,15}, {12,15}, {10,16}, {12,16}};
        for (int[] coord : greyCoords) {
            int num = getNumber(coord[0] - 1, coord[1] - 1); // Adjust for 0-based indexing
            piecesArray[num][0] = "#9AA3A2"; // Grey hex code
            piecesArray[num][1] = String.valueOf(num);
        }

        // Dark Skin
        int[][] darkSkinCoords = {{9,7}, {13,7}, {8,8}, {9,8}, {14,8}, {10,9}, {11,10}};
        for (int[] coord : darkSkinCoords) {
            int num = getNumber(coord[0] - 1, coord[1] - 1); // Adjust for 0-based indexing
            piecesArray[num][0] = "#FBCFB4"; // Dark Skin hex code
            piecesArray[num][1] = String.valueOf(num);
        }

        // Skin
        int[][] skinCoords = {{13,8}, {9,9}, {13,9}, {9,10}, {10,10}, {13,10}, {9,11}, {10,11}, {11,11}, {12,11}, {13,11}, {9,12}, {10,12}, {11,12}, {12,12}, {13,12}, {8,16}, {14,16}};
        for (int[] coord : skinCoords) {
            int num = getNumber(coord[0] - 1, coord[1] - 1); // Adjust for 0-based indexing
            piecesArray[num][0] = "#F9E9D9"; // Skin hex code
            piecesArray[num][1] = String.valueOf(num);
        }

        // Dark Blue
        int[][] darkBlueCoords = {{6,5}, {9,5}, {13,5}, {16,5}, {9,6}, {13,6}, {5,7}, {8,7}, {14,7}, {17,7}, {5,8}, {7,8}, {10,8}, {15,8}, {17,8}, {7,9}, {11,9}, {11,14}, {9,16}, {13,16}};
        for (int[] coord : darkBlueCoords) {
            int num = getNumber(coord[0] - 1, coord[1] - 1); // Adjust for 0-based indexing
            piecesArray[num][0] = "#5094D1"; // Dark Blue hex code
            piecesArray[num][1] = String.valueOf(num);
        }

        // Light Blue
        int[][] lightBlueCoords = {{8,6}, {14,6}, {2,7}, {4,7}, {6,7}, {10,7}, {16,7}, {18,7}, {20,7}, {2,8}, {3,8}, {4,8}, {6,8}, {16,8}, {18,8}, {19,8}, {20,8}, {2,9}, {3,9}, {4,9}, {6,9}, {15,9}, {16,9}, {18,9}, {19,9}, {20,9}, {2,10}, {3,10}, {4,10}, {6,10}, {7,10}, {12,10}, {15,10}, {16,10}, {18,10}, {19,10}, {20,10}, {2,11}, {3,11}, {4,11}, {7,11}, {15,11}, {18,11}, {19,11}, {20,11}, {2,12}, {3,12}, {4,12}, {7,12}, {15,12}, {18,12}, {19,12}, {20,12}, {2,13}, {3,13}, {4,13}, {18,13}, {19,13}, {20,13}, {3,14}, {4,14}, {18,14}, {19,14}, {3,15}, {4,15}, {18,15}, {19,15}};
        for (int[] coord : lightBlueCoords) {
            int num = getNumber(coord[0] - 1, coord[1] - 1); // Adjust for 0-based indexing
            piecesArray[num][0] = "#5DBCEA"; // Light Blue hex code
            piecesArray[num][1] = String.valueOf(num);
        }

        // Red
        int[][] redCoords = {{7,2}, {15,2}, {6,3}, {16,3}, {5,4}, {17,4}, {5,11}, {17,11}};
        for (int[] coord : redCoords) {
            int num = getNumber(coord[0] - 1, coord[1] - 1); // Adjust for 0-based indexing
            piecesArray[num][0] = "#EB4C37"; // Red hex code
            piecesArray[num][1] = String.valueOf(num);
        }

        // Pink
        int[][] pinkCoords = {{8,12}, {14,12}};
        for (int[] coord : pinkCoords) {
            int num = getNumber(coord[0] - 1, coord[1] - 1); // Adjust for 0-based indexing
            piecesArray[num][0] = "#F5BCC2"; // Pink hex code
            piecesArray[num][1] = String.valueOf(num);
        }

        // Fill remaining slots with "Empty"
        for (int i = 0; i < Width * Height; i++) {
            if (piecesArray[i][0] == null) {
                piecesArray[i][0] = "#FFFFFF"; // Default to white for empty slots
                piecesArray[i][1] = String.valueOf(i);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameBoard board = new GameBoard();
            board.setVisible(true);
        });
    }
}
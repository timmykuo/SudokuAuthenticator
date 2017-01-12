# SudokuAuthenticator

The project uses a client/server to verify a sudoku puzzle after the client is finished playing the game and is submitted to the server.  

# Code Example
```java
while (true) {
  Socket client = sock.accept();
  ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
  ArrayList<ArrayList<Integer>> list = extracted(ois);
    server.new BoxThread(list).start();
    server.new ColThread(list).start();
    server.new RowThread(list).start();
  client.close();
}
 ```
 # Motivation
 My partner, Faraz Ahmed, and I created this project as a final project for our EECS338 class at Case Western Reserve University.  We focused on multithreading the authenticator of the sudoku puzzle through a client/server to speed up the process of verifying the correctness of the puzzle.
 
 # Installation
Download the source code in src. Enter the folder that it is downloaded in and enter the following commands through your terminal:
```
make
java -cp . SudokuClient
```

In a different terminal window, enter the folder and run the following code through your terminal:
```
java -cp . SudokuServer
```

After filling out the puzzle, hit submit in the client GUI to verify the correctness of the sudoku puzzle.

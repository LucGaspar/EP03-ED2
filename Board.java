public class Board{
	// create a board from an n-by-n array of tiles,

	int n;
	int[] tiles;
	int[] goalBoard;

    public Board(int[][] tiles){
		n = tiles.length;
		int max = n * n;
		this.tiles = new int[max];
		for (int i = 0; i < max; i++)
			this.tiles[i] = tiles[i / n][i % n];
	}          

    public void doTheGoalBoard(){
    	int max = n * n;
    	goalBoard = new int[max];
    	for (int i = 0; i < max; i++)
    		goalBoard[i] = i + 1;

    	goalBoard[max-1] = 0;
    }

    // string representation of this board
    public String toString() {
	    StringBuilder s = new StringBuilder();
	    s.append(n + "\n");
	    for (int row = 0; row < n; row++) {
	        for (int col = 0; col < n; col++) {
	            s.append(String.format("%2d ", tileAt(row, col)));
	        }
	        s.append("\n");
	    }
	    return s.toString();
    }              

    public int[] whereIsBlank(){
    	for (int i = 0; i < size(); i++)
    		for (int j = 0; j < size(); j++)
    			if (tileAt(i,j) == 0)
    				return new int[] {i, j};
    	return new int[] {-1, -1};
    }

    // tile at (row, col) or 0 if blank
    public int tileAt(int row, int col){
    	if (row > n - 1 || row < 0 || col > n - 1 || row < 0)
    		throw new java.lang.IllegalArgumentException();

    	return tiles[row * n + col];
    } 

    // board size n
    public int size(){
    	return n;
    }          

    // number of tiles out of place     
    public int hamming(){
    	// Priority: this plus moves made until now
    	int error = 0;
    	for (int i = 0; i < size(); i++)
    		for (int j = 0; j < size(); j++)
    			if (tileAt(i,j) == i * size() + j + 1)
    				error = error;
    			else
    				error = error + 1;
    	return error;
    }              

    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
		// Priority: this plus moves made until now
		int error = 0;
		int distance = 0;
		for (int i = 0; i < size(); i++)
    		for (int j = 0; j < size(); j++){
    			distance = Math.abs(tileAt(i,j) -  (i * size() + j + 1));
    			distance = distance / n + distance % n;
    		}

		return distance;
    }           
    // is this board the goal board?  
    public boolean isGoal(){
    	int index = 0;
    	for (int i = 0; i < size(); i++)
    		for (int j = 0; j < size(); j++)
    			if (tileAt(i,j) != ++index)
    				if (i == size() - 1 && j == size() - 1)
    					return true;
    				else
    					return false;
    	return true;
    }          

    public boolean isEqual(Board x){
    	System.out.printf("" + x.size());
    	for (int i = 0; i < size(); i++)
    		for (int j = 0; j < size(); j++)
    			if (tileAt(i,j) != x.tileAt(i,j))
    				return false;
    	return true;
    }

    public boolean equals(Object x){
    	return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors(){
    	// Retorna os boards resultantes das possíveis jogadas nessa rodada

    	Board items[] = new Board[4];
	    // Find blank
	    int[] blank = whereIsBlank();
       	int[] neighborsDistanceI = {-1, 0, 1,  0};
       	int[] neighborsDistanceJ = {0 , 1, 0, -1};

	    for (int k = 0; k < 4; k++){
	    	int i = blank[0] + neighborsDistanceI[k];
	        int j = blank[1] + neighborsDistanceJ[k];
	        if (i < 0 || i > size() - 1 || j > size() - 1 || j < 0)
	            items[k] = null;
	        else{
	            Board x = new Board(matrixBoard(tiles));
	            x.tiles[blank[0] * size() + blank[1]] = tileAt(i, j);
	            x.tiles[i * n + j] = 0;
	            items[k] = x;
	        }
	    }
	    Iterable<Board> result = java.util.Arrays.asList(items);
	    return result;
    }     

    public int[][] matrixBoard(int[] vector){
    	int length = vector.length;
    	int[][] til = new int[length][length];
    	for (int i = 0; i < length; i++)
    		til[i / length][i % length] = vector[i];
    	return til;    		
    }

    // is this board solvable?
    public boolean isSolvable(){
    	// Contar inversões: para cada um fora de ordem, contar quantos são menores que ele em índices maiores
    	// If n and number of inversions are an odd, thus it's unsolvable
    	// If n is even and the number of inversions plus row of Blank is an even, so it's unsolvable
    	int rowOfBlank = 0;
    	int inversions = 0;
    	int realIndex = 0;
    	for (int i = 0; i < size(); i++)
    		for (int j = 0; j < size(); j++){
    			realIndex = realIndex + 1;
    			if (tileAt(i, j) == 0){
    				realIndex = realIndex - 1;
    				rowOfBlank = i;
    			}
 				if (tileAt(i, j) > realIndex)
 					for (int k = i; k < size(); k++)
 						for (int l = 0; l < size(); l++){
 							if (k == i && l < j)
 								continue;
 							if (tileAt(i,j) > tileAt(k,l) && tileAt(k,l) > 0)
 								inversions = inversions + 1;
 						}

    		}
    	// Odd
    	if (n % 2 == 1){
    		// Odd
    		if (inversions % 2 == 1)
    			return false;

    	}

    	// Even
    	else
    		// Even
    		if ((inversions + rowOfBlank) % 2 == 0)
    			return false;

    	return true;
    }   

}
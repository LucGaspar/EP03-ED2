public class Solver {



	public class SearchNode implements Comparable<SearchNode>{
		Board board;
		SearchNode previous;
		int moves;
		int priority;

		public SearchNode(Board board, SearchNode previous) {
			this.previous = previous;
			this.board = board;
			moves = 0;
			minmoves = 0;
			priority = 0;
		}

		public int getPriority(){
			return priority;
		}

		public void updatePriority(){
			priority = board.manhattan() + moves;
		}

		@Override
		public int compareTo(SearchNode y){
			if (y.getPriority() > this.getPriority())
				return -1;
			else
				if (y.getPriority() == this.getPriority())
					return 0;
				else
					return 1;
		}

	}

	edu.princeton.cs.algs4.MinPQ<SearchNode> nodes = new edu.princeton.cs.algs4.MinPQ<SearchNode>();
	int minmoves;

	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    	if (initial == null)
    		throw new java.lang.IllegalArgumentException();

    	if (!initial.isSolvable())
    		throw new java.lang.IllegalArgumentException();

    	SearchNode min = new SearchNode(initial, null);
    	min.previous = min;
    	nodes.insert(min);

    	while (!min.board.isGoal()){
    		min = nodes.delMin();
    		Iterable<Board> neighbours = min.board.neighbors();
    		for (Board item : neighbours)
    			if (item != null){
    				if (!item.isEqual(min.previous.board)){
	    				SearchNode t = new SearchNode(item, min);
	    				t.moves = min.moves + 1;
	    				t.updatePriority();
	    				nodes.insert(t);
    				}
    			}
    	}
    	setMinMoves(min.moves);
    }         

    // min number of moves to solve initial board
    public int moves(){
    	return minmoves;
    }           

    public void setMinMoves(int n){
    	minmoves = n;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){
    	return null;
    }        
}
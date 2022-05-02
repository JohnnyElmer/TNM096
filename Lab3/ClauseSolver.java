import java.util.*;

public class ClauseSolver {

	public Vector<Clause> clausesVec = new Vector<Clause>();
	
	ClauseSolver(String[] inputString){
		
		//For each bit in the input string
		for(int i = 0; i < inputString.length; i++) {
			//Make a temporary clause
			Clause temp = new Clause(inputString[i]);
			
			//As long as the temporary clause is not empty
			if(!temp.isEmpty()) {
				//Add the clause to the vector of clauses
				clausesVec.addElement(temp);
			}
		}
	}
	
	public Clause Resolution(Clause A, Clause B) {
		
		//Create a variable "snitt" which is used to see where to cut off later
		String snitt = null; 
		
		//And a variable to house the result
		Clause resultClause = new Clause();
		
		Vector<String> An = A.negative;
		Vector<String> Ap = A.positive;
		Vector<String> Bn = A.negative;
		Vector<String> Bp = B.positive;
		
		//For all positive objects in the A clause
		for(int i = 0; i < Ap.size(); i++) {
			
			//Check if it also exists in the B clause
			if(Bn.contains(Ap.get(i))) {
				
				//If yes, set the "snitt" to that object
				snitt = Ap.get(i);
			}
		}
		
		//For all positive objects in the B clause, as long as "snitt" is null
		for(int i = 0; snitt == null && i < Bp.size(); i++) {
			
			//Check if it also exists in the A clause
			if(An.contains(Bp.get(i))) {
				
				//If yes, set the "snitt" to that object
				snitt = Bp.get(i);
			}
		}
		
		//If a "snitt" was ever found
		if(snitt != null) {
			
			//Go through each of the vectors and see whether some element of it is 
			//missing inside of the resultClause vector, if so, add it
			//As long as the element isn't the "snitt"
			
			for(int i = 0; i < Ap.size(); i++) {
				
				if(!Ap.get(i).equals(snitt) && !resultClause.positive.contains(Ap.get(i)))
					resultClause.positive.add(Ap.get(i));
				
			}
			for(int i = 0; i < An.size(); i++) {
				
				if(!An.get(i).equals(snitt) && !resultClause.positive.contains(An.get(i)))
					resultClause.positive.add(An.get(i));
				
			}			
			for(int i = 0; i < Bp.size(); i++) {
				
				if(!Bp.get(i).equals(snitt) && !resultClause.positive.contains(Bp.get(i)))
					resultClause.positive.add(Bp.get(i));
				
			}			
			for(int i = 0; i < Bn.size(); i++) {
				
				if(!Bn.get(i).equals(snitt) && !resultClause.positive.contains(Bn.get(i)))
					resultClause.positive.add(Bn.get(i));
			}	
		}
		else {
			return null;
		}
		
		//Also check whether the resultClause is contradictory to itself
		if(snitt != null && resultClause.isContradictory()) {
			return null;
		}
			
		return resultClause;
	}
	
	//
	public void Solver() {
		
		Clause C;
		
		//Using the notation from the lectures
		Vector<Clause> KB = new Vector<Clause>();
		Vector<Clause> S;
		
		do {
			
			C = new Clause();
			S = new Vector<Clause>();
			KB = clausesVec;
			
			//Look through the clauses
			for(int i = 0; i < clausesVec.size()-1; i++) {
				for(int j = i + 1; j < clausesVec.size(); j++) {
					
					//Use the Resolution function to get the resulting clause
					C = Resolution(clausesVec.get(i), clausesVec.get(j));
					
					//If it did not return a null, C can be added into the vector S
					if(C != null) {
						S.addElement(C);
					}
				}
			}
			
			//If S is empty, the next step can not be performed, so "break"
			if(S.isEmpty())
				break;
			
			//
			clausesVec = Incorporate(S);
			
		}while(clausesVec != KB); //As long as we haven't reached KB
		
		//
		clausesVec = Incorporate(S);
		
		//This just prints out the entire result
		System.out.println("Solved! :)");
		for(int i = 0; i < clausesVec.size(); i++) {
			if(clausesVec.get(i) != null && !clausesVec.get(i).isEmpty()) {
				clausesVec.get(i).printClause();
			}
			
		}
		
	}
	
	//Combine the "clausesVec" with another vector of clauses (KB)
	public Vector<Clause> Incorporate(Vector<Clause> KB){
		
		for(int i = 0; i < clausesVec.size(); i++) {
			
			KB = Incorporate_Clause(clausesVec.get(i), KB);
			
		}
		
		return KB;
		
	}
	
	//Used in Incorporate
	public Vector<Clause> Incorporate_Clause(Clause A, Vector<Clause> KB){
		
		//Look through all of KB and check if it is a subset of A
		for(int i = 0; i < KB.size(); i++) {
			
			if(KB.get(i).testSubset(A)) {
				
				//If yes, return KB
				return KB;
			}
		}
		
		//Do the same, but test if A is a subset of KB
		for(int i = 0; i < KB.size(); i++) {
			
			if(A.testSubset(KB.get(i))) {
				
				//If yes, remove that object from KB
				KB.remove(KB.get(i));
			}
		}
		
		//Add the clause A into the vector of clauses KB
		KB.add(A);
		
		return KB;
		
	}
	
}





















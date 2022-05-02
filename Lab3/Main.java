import java.util.Vector;

public class Main {

	public static void main(String[] args) {
		
		//Given set of clauses (2)
		String[] ourClause = {"-turtle V -hamster V cat", "-hamster V cat V dog", "-dog V hamster", "-dog V -cat", "dog"}; 
		
		ClauseSolver theClauseSolver = new ClauseSolver(ourClause);
		
		theClauseSolver.Solver();
		
		//Our own clauses (1)
		
		//Should return a V b
		String a = "a V b V -c";
		String b = "c V b";
		
		//Should return false
		//String a = "a V b V -c";
		//String b = "d V b V -g";
		
		//Should return false
		//String a =  "-b V c V t";
		//String b = 	"-c V z V b";		
		
		Clause A_Clause = new Clause(a);
		Clause B_Clause = new Clause(b);
		
		Clause result = Resolution(A_Clause, B_Clause);
		
		if(result != null)
			result.printClause();
		
	}
	
	
	//For 1 / A
	public static Clause Resolution(Clause A, Clause B) {
		
		String snitt = null;
		Clause resultClause = new Clause();
		
		Vector<String> An = A.negative;
		Vector<String> Ap = A.positive;
		Vector<String> Bn = B.negative;
		Vector<String> Bp = B.positive;
		
		for(int i = 0; i < Ap.size(); i++) {
			if(Bn.contains(Ap.get(i)))
				snitt = Ap.get(i);
		}
		for(int i = 0; snitt == null && i < Bp.size(); i++) {
			if(An.contains(Bp.get(i)))
				snitt=Bp.get(i);
		}
		
		
		
		if(snitt != null) {
			for(int i = 0; i < Ap.size();i++) {
				if(!Ap.get(i).equals(snitt) && !resultClause.positive.contains(Ap.get(i)))
						resultClause.positive.add(Ap.get(i));
			}
			for (int i = 0; i < An.size(); i++) {
				if (!An.get(i).equals(snitt) && !resultClause.negative.contains(An.get(i)))
					resultClause.negative.add(An.get(i));
			}
			for (int i = 0; i < Bp.size(); i++) {
				if (!Bp.get(i).equals(snitt) && !resultClause.positive.contains(Bp.get(i)))
					resultClause.positive.add(Bp.get(i));
			}
			for (int i = 0; i < Bn.size(); i++) {
				if (!Bn.get(i).equals(snitt) && !resultClause.negative.contains(Bn.get(i)))
					resultClause.negative.add(Bn.get(i));
			}
		}		
		else {
			System.out.print("False");
			return null;
		}
				
		
		if(resultClause.isContradictory() && snitt != null) {
			System.out.print("False");
			return null;
		}
			
		return resultClause;	
	}
}

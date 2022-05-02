import java.util.Vector;

public class Clause {
	
	public Vector<String> positive = new Vector<String>();
	public Vector<String> negative = new Vector<String>();
	
	Clause(){}
	
	Clause(String inputString){
		
		String[] splittedString;
		
		//Change out things that are hard for the code to understand
		inputString = inputString.replaceAll(" ", "");
		inputString = inputString.replaceAll("V", " ");
		
		//Split the string up each time we have a V in the clause
		splittedString = inputString.split(" ");
		
		//Go through each bit in the clause
		for(int i = 0; i < splittedString.length; i++) {
			
			//If it contains a "-", put in the vector containing negative
			if(splittedString[i].contains("-")) {
				String temp = splittedString[i];
				temp = temp.replaceAll("-", "");
				negative.addElement(temp);
			}else { //Else put into positive
				positive.addElement(splittedString[i]);
			}
			
		}
		
	}
	
	//Remove duplicates as they will only take each other out anyways
	public void removeDuplicates() {
		
		for(int i = 0; i < negative.size(); i++) {
			for(int j = 0; j < positive.size(); j++) {
				
				if(negative.get(i).equals(positive.get(j))) {
					negative.removeElement(i);
					positive.removeElement(j);
				}
				
			}
			
		}
	}
	
	//Check if there are contradictory statements in the clause
	public boolean isContradictory() {
		
		for(int i = 0; i < positive.size(); i++) {
			for(int j = 0; j < negative.size(); j++) {
				if(positive.get(i).equals(negative.get(j))) {
					return true;
				}	
			}
		}
		return false;
	}
	
	
	//Check if the clause is empty
	public boolean isEmpty() {
		return (negative.size() + positive.size()) == 0;
	}
	
	public boolean testSubset(Clause inputClause) {
		
		//Go through the positive objects and check if the input clause exists there
		for(int i = 0; i < positive.size(); i++) {
			if(inputClause.positive.contains(positive.get(i)))
				return true;
		}
		//Go through the negative objects and check if the input clause exists there
		for(int i = 0; i < negative.size(); i++) {
			if(inputClause.negative.contains(negative.get(i)))
				return true;
		}
		
		//Else return false, as it is not a subset of the clause
		return false;
	}
	
	//This allows us to print the clause for later
	public void printClause() {
		
		//Start with a bracket
		System.out.print("[");
		
		//If the negative vector contains something
		if(negative.size() > 0) {
			for(int i = 0; i < negative.size(); i++) {
				
				//Print out the negative element with a "-" sign in front of it
				System.out.print("-" + negative.elementAt(i));
				
				//If not at the end, print out the "and" symbol
				if(i != negative.size()-1)
					System.out.print(" V ");
			}
			
		}
		
		//If both of the vectors contain objects
		if(positive.size() > 0 && negative.size() > 0) {
			//Print out the "and" symbol
			System.out.print(" V ");
		}
		
		if(positive.size() > 0) {
			for(int i = 0; i < positive.size(); i++) {
				
				//Print out the positive element
				System.out.print(positive.elementAt(i));
				
				//If not at the end, print out the "and" symbol
				if(i != positive.size()-1)
					System.out.print(" V ");
			}
		}
		//Close the bracket and make a new line
		System.out.print("] \n");
	}
	
	//Making an override (unsure what it does)
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((negative == null) ? 0 : negative.hashCode());
		result = prime * result + ((positive == null) ? 0 : positive.hashCode());
		return result;
	}
	
	
	

}



















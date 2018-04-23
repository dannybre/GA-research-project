package GA.ResearchProject;


public class Population {
	private Individual[] population;
	
	public Population(int size, boolean initialise, boolean decimal){
		population = new Individual[size];
		
		if(initialise){
			for(int i = 0; i<size;i++){
				population[i] = new Individual(decimal);
			}
		}
	}
	
	public void display(){
		for(int i = 0; i<population.length;i++){
			System.out.println(population[i].toString() + " fitness:  " + population[i].getFitness(GA.DECIMAL, GA.DECEPTIVE));
		}
		System.out.println(getFittest()  + " "  + getFittest().getFitness(GA.DECIMAL,GA.DECEPTIVE));
	}
	
	public Individual getFittest(){
		Individual fittest = population[0];
		for(int i = 0; i<population.length;i++){
			if(fittest.getFitness(GA.DECIMAL,GA.DECEPTIVE)<=population[i].getFitness(GA.DECIMAL, GA.DECEPTIVE)){
				fittest = population[i];
			}
		}
		return fittest;
	}
	public Individual getLeastFit(){
		Individual leastFit = population[size()-1];
		
		for(int i =0; i<population.length;i++){
			if(leastFit.getFitness(GA.DECIMAL,GA.DECEPTIVE)>=getIndividual(i).getFitness(GA.DECIMAL, GA.DECEPTIVE)){
				leastFit = population[i];
				
			}
		}
		return leastFit;
	}
	
	public void saveIndividual(int index, Individual individual) {
		population[index] = individual;
		
	}
	
	public float averageFitness(){
		float average = 0.0f;
		for(int i=0;i<population.length;i++){
			average+=population[i].getFitness(GA.DECIMAL, GA.DECEPTIVE);
		}
		average=average/population.length;
		return average;
	}
	
	
	public Individual getIndividual(int index) {
		return population[index];
	}
	
	public int size(){
		return population.length;
	}
	
	
	
}

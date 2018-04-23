package GA.ResearchProject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GA {
	private static final float MUTATION_RATE = 0.025f; 
    private static final int TOURNAMENT_LEN = 6;
    static final boolean DECIMAL = false;
    static final boolean DECEPTIVE = true;
    

    public static Population evolve(Population pop){
    	Population newPopulation = new Population(pop.size(), false, DECIMAL);
    	
    	//loops though the population creates two new individuals based on 
    	//selection and passes them to crossover then adds to new population
    	for (int i = 0; i < pop.size(); i++) {
            Individual indiv1 = selection(pop);
            Individual indiv2 = selection(pop);
            Individual newIndivs = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(i, newIndivs);

        }
    	
    	//loop though the new population and call mutate on each individual
    	for (int i = 0; i < newPopulation.size(); i++) {
    		mutate(newPopulation.getIndividual(i));

        }
    	
    	return newPopulation;
    }

	private static void mutate(Individual individual) {
		for (int i = 0; i < individual.size(); i++) {
			if (Math.random() <= MUTATION_RATE) {
                // Create random gene
            	if(DECIMAL){
            		int gene = (int)(Math.random() *10);
            		individual.setGene(i, gene);// one in ten chance of being the optimal solution 
            	}else{
            		byte gene = (byte) Math.round(Math.random());
	                individual.setGene(i, gene);
            	}
			}
        }
		
	}

	private static Individual crossover(Individual indiv1, Individual indiv2) {
		Individual newIndividual = new Individual(DECIMAL);
		//50/50 chance of first half being from the first individual and second half from second individual and vice versa 
			
		if(Math.random()>=.5){
			for (int i = 0; i < indiv1.size()/2; i++) {
				newIndividual.setGene(i, indiv1.getGene(i));
			}
			for(int j = indiv1.size()/2; j<indiv1.size();j++){
				newIndividual.setGene(j, indiv2.getGene(j));
			}
		}else{
			for (int i = 0; i < indiv1.size()/2; i++) {
				newIndividual.setGene(i, indiv2.getGene(i));
			}
			for(int j = indiv1.size()/2; j<indiv1.size();j++){
				newIndividual.setGene(j, indiv1.getGene(j));
			}
		}
		/*	 other method of crossover not used here 
		for (int i = 0; i < indiv1.size(); i++) {
			// Crossover
            if (Math.random() <= CROSSOVER_RATE) {
                newIndividual.setGene(i, indiv1.getGene(i));
            } else {
                newIndividual.setGene(i, indiv2.getGene(i));
            }
           
		}
		 */
		
  
        return newIndividual;
	}

	private static Individual selection(Population pop) {
		Population tournament = new Population(TOURNAMENT_LEN, false,DECIMAL);
        // For each place in the tournament get a random individual
        for (int i = 0; i < TOURNAMENT_LEN; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.saveIndividual(i, pop.getIndividual(randomId));
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
	}
	
	public static void main(String[] args){
		
		try{
	 		double avergaeGenConverged =0;
	 		int maxGenCount =0;
	 		int minGenCount =100;
	 		int amtNonConverge =0;
	 		double gentime =0;
			//creates file called 
			FileWriter fw = new FileWriter("test_bina_Deceptive.txt");
			BufferedWriter writer = new BufferedWriter(fw);
			//writes to file
			writer.write("\ndecimal Deceptive\n\n");
			long startTimeFullProg = System.nanoTime();
			for(int i = 1; i<=100;i++){
				//avergaeGenConverged =0;
				long startTimeGen = System.nanoTime();
				Population myPop = new Population(100,true,DECIMAL);
				
				writer.write("\nTest: " + i + "\n\n");
				writer.write("********************************************************\n\n");
				int generationCount = 0;
				while (myPop.getFittest().stop(DECIMAL) && generationCount<100) {
	        	//myPop.display();
	        	 
					++generationCount;
		            writer.write("Gen: " + generationCount + " Fittest: " + myPop.getFittest() + "\nMAX:"
		            + myPop.getFittest().getFitness(DECIMAL, DECEPTIVE) 
		            + " MIN: "+ myPop.getLeastFit().getFitness(DECIMAL, DECEPTIVE)
		            +" AVG: " + myPop.averageFitness() + "\n\n");
		            //myPop.display();
		            
		            myPop = evolve(myPop);
		            
		            if(generationCount%10==0){
		            	writer.write("\n\n");
		            }
		           // System.out.println(myPop.getFittest());
		            
		            
		        }
				
	        //prints the solution if it was found within the 100 generations
	        if(myPop.getFittest().stop(DECIMAL)==false){
	        	//++generationCount;
	        	writer.write("Gen: " + generationCount + " Fittest: " + myPop.getFittest() + "\nMAX:"
			            + myPop.getFittest().getFitness(DECIMAL, DECEPTIVE) 
			            + " MIN: "+ myPop.getLeastFit().getFitness(DECIMAL, DECEPTIVE)
			            +" AVG: " + myPop.averageFitness() + "\n\n");
	        	writer.write("\n\nSolution found!\n");
		        writer.write("Generation: " + generationCount+"\n");
		        writer.write("Genes: ");
		        
		        avergaeGenConverged+=generationCount;
		        //myPop.display();
		        writer.write(myPop.getFittest() +" " + myPop.getFittest().getFitness(DECIMAL,DECEPTIVE) + "\n");
		        writer.write("-----------------------------------------------------------------\n\n");
	        
	        }else{
	        	avergaeGenConverged+=100;
	        	++amtNonConverge;
	        }
	        if(maxGenCount<generationCount){
	        	maxGenCount=generationCount;
	        }
	        if(minGenCount>generationCount){
	        	minGenCount=generationCount;
	        }
	        long endTimeGen = System.nanoTime();
	        long totalTimeGen = endTimeGen - startTimeGen;
	        
	        gentime += totalTimeGen;
			}
			long endTimeFullProg = System.nanoTime();
			
			long totalTime = endTimeFullProg - startTimeFullProg;
			double d = totalTime/ 1000000000.0;
			
			writer.write("Average generation of convergence: " + avergaeGenConverged/100);
			writer.write("\nMax gen: "+ maxGenCount);
			writer.write("\nMin gen: "+ minGenCount);
			writer.write("\nnumber of non convergence: "+ amtNonConverge);
			writer.write("\nTotal seconds to compute: " + d);
			writer.write("\nAverage generation time: " + (gentime/100)/1000000000.0);
	        writer.close();	
	}
	catch(IOException e){
		e.printStackTrace();
	}
		
	}
	    
    

}

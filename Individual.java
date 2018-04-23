package GA.ResearchProject;


public class Individual {
	public static int GENE_LEN = 30;
	private int[] genes;
	private float fitness= 0.00f;
	
	
	public Individual(boolean decimal) {
		genes = new int[GENE_LEN];
	     //Set genes randomly for each individual
		if (decimal){//decimal
			for (int i = 0; i < GENE_LEN; i++) {
				genes[i] = (int)(Math.random() *10);
			}
		}
		else{//binary 
			for (int i = 0; i < GENE_LEN; i++) {
				byte gene = (byte) Math.round(Math.random());
				genes[i] = gene;
			}
		}
	}
	
	public int getGene(int index) {
		return genes[index];
	}

    public void setGene(int index, int value) {
        genes[index] = value;
        fitness = 0;
    }
    
    public float getFitness(boolean decimal, boolean deceptive) {
    	fitness= 0.00f;
    	if(decimal){
    		
    		if(deceptive){
    			//if decimal (0-9) and deceptive use this fitness function
				for(int j =0;j<genes.length;j++){
					if(genes[j]==9){
						fitness+=.90001;
					}else{
						fitness+=((1-((genes[j]+1)*.100f)));
					}
				}
				
				
			}else{//if decimal but not deceptive 
				for(int j =0;j<genes.length;j++){
					fitness=(fitness+((genes[j]+1)*.100f));
				}
			}
			
    	}else{//if not decimal
	    	if(deceptive){//if deceptive
	    		int g = 0;
	    		for (int i = 0; i < genes.length; i+=3) {
	    			for(int j = 0+i; j<i+3;j++){
	    				if (genes[j] == 1) {
		                	g+=1;
		                }
	    			}
	    			
	    			//number of 1s 
	    			if(g==0){// one chance 000
	    				fitness+=.9;
	    			}else if(g==1){//3 chances 001 010 100 
	    				fitness+=.3;
	    			}else if(g==2){// 2 chances 110 101 011
	    				fitness+=.6;
	    			}else{// one chance 111
	    				fitness+=.90001;
	    			}
	    			g=0;//set g back to 0
	    			
	    			//str="";
	    		}
	    		fitness = fitness/10;// divide by 10 as doing it in sets of 3
	    		return fitness;
	    	}else{//if not deceptive and binary 
	    		for (int i = 0; i < genes.length; i++) {
	                if (genes[i] == 1) {
	                    fitness++;
	                }
	            }
	    	}
    	}
    	
    	fitness = fitness/GENE_LEN;//get average
    	return fitness;
		
	}
    
    public int size(){
		return genes.length;
	}
    
    public String toString(){
		String s ="";
		for (int i = 0; i < genes.length; i++) {
			s+=genes[i];
		}
		return s;
	}
    
    public boolean stop(boolean decimal){
    	String s ="";
    	for (int i = 0; i < GENE_LEN; i++) {
    		if(decimal)
    			s+=9;
    		else
    			s+=1;
		}
    	if(s.equals(toString())){
    		return false;
    	}
    	return true;
    }
   

}

package com.test.currentprices;

public class Market {
		//market fields
	 	private int market_id;
	    private String market_name;
	     
	    public Market(){
	    	
	    }
	     
	    public Market(int market_id, String market_name){
	        this.market_id = market_id;
	        this.market_name = market_name;
	    }
	     
	    //setters
	    public void setId(int market_id){
	        this.market_id = market_id;
	    }
	     
	    public void setName(String market_name){
	        this.market_name = market_name;
	    }
	     
	    //getters
	    public int getId(){
	        return this.market_id;
	    }
	     
	    public String getName(){
	        return this.market_name;
	    }

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return ""+this.market_id;
		}
	    
	    
	    
}

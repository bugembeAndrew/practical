package com.test.currentprices;

public class Unit {
		//unit fields
	 	private int unit_id;
	    private String unit_name;
	     
	    public Unit(){
	    	
	    }
	     
	    public Unit(int unit_id, String unit_name){
	        this.unit_id = unit_id;
	        this.unit_name = unit_name;
	    }
	     
	    //setters
	    public void setId(int unit_id){
	        this.unit_id = unit_id;
	    }
	     
	    public void setName(String unit_name){
	        this.unit_name = unit_name;
	    }
	     
	    //getters
	    public int getId(){
	        return this.unit_id;
	    }
	     
	    public String getName(){
	        return this.unit_name;
	    }
	    
	    @Override
		public String toString() {
			// TODO Auto-generated method stub
			return ""+this.unit_id;
		}
}

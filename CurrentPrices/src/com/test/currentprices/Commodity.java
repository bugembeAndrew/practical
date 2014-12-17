package com.test.currentprices;

public class Commodity {
	//commodity fields
 	private int commodity_id;
    private String commodity_name;
     
    public Commodity(){
    	
    }
     
    public Commodity(int commodity_id, String commodity_name){
        this.commodity_id = commodity_id;
        this.commodity_name = commodity_name;
    }
     
    //setters
    public void setId(int commodity_id){
        this.commodity_id = commodity_id;
    }
     
    public void setName(String commodity_name){
        this.commodity_name = commodity_name;
    }
     
    //getters
    public int getId(){
        return this.commodity_id;
    }
     
    public String getName(){
        return this.commodity_name;
    }
    
    @Override
	public String toString() {
		// TODO Auto-generated method stub
		return ""+this.commodity_id;
	}
}

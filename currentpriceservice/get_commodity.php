<?php

//load and connect to MySQL database stuff
require("config.inc.php");

$query = "SELECT * FROM commodity";

//execute query
try {
    $stmt   = $db->prepare($query);
    $result = $stmt->execute();//$query_params
}
catch (PDOException $ex) {
    $response["success"] = 0;
    $response["message"] = "Database Error!";
    die(json_encode($response));
}

// Finally, we can retrieve all of the found rows into an array using fetchAll 
$rows = $stmt->fetchAll();


if ($rows) {
    $response["success"] = 1;
    $response["message"] = "Commodities are available!";
    $response["commodities"]   = array();

 foreach ($rows as $row) {
        $commodity = array();
	
        //this line is new:
        $commodity["commodity_id"]  = $row["commodity_id"];
        $commodity["commodity_name"] = $row["commodity_name"];
        
        //update our repsonse JSON data
        array_push($response["commodities"], $commodity);
    }

    #display as options
    #echo '<select name="commodities">';
    #foreach( (array) $response["commodities"] as $cmds ){
        #echo '<option value = "'.$cmds['commodity_id'].'" selected = "selected">'.$cmds['commodity_name'].'</option>';
    #}
    #echo '</select><br/>';
    
    // echoing JSON response
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No commodities available!";
    die(json_encode($response));
}

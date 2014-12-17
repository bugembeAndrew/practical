<?php

//load and connect to MySQL database stuff
require("config.inc.php");

$query = "SELECT * FROM market";

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
    $response["message"] = "Market (s) are available!";
    $response["markets"]   = array();

 foreach ($rows as $row) {
        $market = array();
	
        //this line is new:
        $market["market_id"]  = $row["market_id"];
        $market["market_name"] = $row["market_name"];
        
        //update our repsonse JSON data
        array_push($response["markets"], $market);
    }

    #display as options
    #echo '<select name="markets">';
    #foreach( (array) $response["markets"] as $mkts ){
        #echo '<option value = "'.$mkts['market_id'].'" selected = "selected">'.$mkts['market_name'].'</option>';
    #}
    #echo '</select><br/>';
    
    // echoing JSON response
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No markets available!";
    die(json_encode($response));
}

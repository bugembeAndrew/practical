<?php

//load and connect to MySQL database stuff
require("config.inc.php");

$query = "SELECT * FROM unit";

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
    $response["message"] = "Units are available!";
    $response["units"]   = array();

 foreach ($rows as $row) {
        $unit = array();
	
        //this line is new:
        $unit["unit_id"]  = $row["unit_id"];
        $unit["unit_name"] = $row["unit_name"];
        
        //update our repsonse JSON data
        array_push($response["units"], $unit);
    }

    #display as options
    #echo '<select name="units">';
    #foreach( (array) $response["units"] as $units ){
        #echo '<option value = "'.$units['unit_id'].'" selected = "selected">'.$units['unit_name'].'</option>';
    #}
    #echo '</select><br/>';
    
    // echoing JSON response
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No units available!";
    die(json_encode($response));
}

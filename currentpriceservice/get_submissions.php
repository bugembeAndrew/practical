<?php

//load and connect to MySQL database stuff
require("config.inc.php");
#include functions
require_once 'functions.php';

//initial query
$query = "SELECT * FROM submissions";

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
    $response["message"] = "Submissions are available!";
    $response["submissions"]   = array();

 foreach ($rows as $row) {
        $submission = array();
	
        //this line is new:
        $submission["sub_id"]  = $row["sub_id"];
        $submission["vendor_name"] = $row["vendor_name"];
        $submission["market_name"]    = get_market($row["market_id"]);
        $submission["commodity_name"]  = get_commodity($row["commodity_id"]);
        $submission["price"]  = $row["price"];
        $submission["unit_name"]  = get_unit($row["unit_id"]);
        
        
        //update our repsonse JSON data
        array_push($response["submissions"], $submission);
    }
    
    // echoing JSON response
    echo json_encode($response);
    
    
} else {
    $response["success"] = 0;
    $response["message"] = "No submissions available!";
    die(json_encode($response));
}

?>

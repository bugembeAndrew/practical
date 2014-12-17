<?php

function get_unit($unit_id){
    require("config.inc.php");
    ob_clean();

    $query = "SELECT unit_name FROM unit WHERE unit_id='$unit_id'";
    #execute query
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute();
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Error!";
        die(json_encode($response));
    }
    #Finally, we can retrieve all of the found rows into an array using fetchAll 
    $rows = $stmt->fetchAll();

    if ($rows) { 
        foreach ((array)$rows as $row)
            $unit = $row["unit_name"];
        return $unit;
    }
}

function get_market($market_id){
    require("config.inc.php");
    ob_clean();

    $query = "SELECT market_name FROM market WHERE market_id='$market_id'";
    #execute query
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute();
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Error!";
        die(json_encode($response));
    }
    #Finally, we can retrieve all of the found rows into an array using fetchAll 
    $rows = $stmt->fetchAll();

    if ($rows) { 
        foreach ((array)$rows as $row)
            $market = $row["market_name"];
        return $market;
    }
}

function get_commodity($commodity_id){
    require("config.inc.php");
    ob_clean();

    $query = "SELECT commodity_name FROM commodity WHERE commodity_id='$commodity_id'";
    #execute query
    try {
        $stmt   = $db->prepare($query);
        $result = $stmt->execute();
    }
    catch (PDOException $ex) {
        $response["success"] = 0;
        $response["message"] = "Database Error!";
        die(json_encode($response));
    }
    #Finally, we can retrieve all of the found rows into an array using fetchAll 
    $rows = $stmt->fetchAll();

    if ($rows) { 
        foreach ((array)$rows as $row)
            $commodity = $row["commodity_name"];
        return $commodity;
    }
}

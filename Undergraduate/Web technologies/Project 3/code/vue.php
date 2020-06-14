<?php
session_start();
require_once("NepremicnineDB.php");

$result = array();
if (isset($_GET["query"]) && !empty($_GET["query"])) {
    $hits = NepremicnineDB::search($_GET["query"]);
    foreach ($hits as $hit):
        $result[] = array(
            "nepremicnina_id" => $hit["nepremicnina_id"],
            "ime_nepremicnine" => $hit["ime_nepremicnine"],
            "slika" => base64_encode($hit["slika"]),
            "cena" => $hit["cena"],
            "ime_kraja" => $hit["ime_kraja"],
            "ime_tipa" => $hit["ime_tipa"]
        );   
    endforeach;
} else {    
    $result = [];
}

header('Content-type: application/json; charset=utf-8');
echo json_encode($result);
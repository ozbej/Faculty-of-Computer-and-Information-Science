<?php
session_start();
require_once ("NepremicnineDB.php");

if (isset($_GET["id"]) && !empty($_GET["id"])) {
    $hits = NepremicnineDB::getComments($_GET["id"]);
}
else {
    $hits = [];
}

header('Content-type: application/json; charset=utf-8');
echo json_encode($hits);
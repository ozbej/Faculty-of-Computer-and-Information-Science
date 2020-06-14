<?php
session_start();
require_once ("NepremicnineDB.php");

if (!isset($_SESSION['loggedin'])) {
    throw new Exception("Prijava potrebna.");
}

$add = isset($_POST["nepremicnina_id"]) && !empty($_POST["nepremicnina_id"]) && 
        isset($_POST["uporabnik_id"]) && !empty($_POST["uporabnik_id"]) &&
        isset($_POST["komentar"]) && !empty($_POST["komentar"]);

if ($add) {
    try {
        NepremicnineDB::addComment($_POST["nepremicnina_id"], $_POST["uporabnik_id"], $_POST["komentar"]);
    } 
    catch (Exception $e) {
        $errorMessage = "Database error occured: $e";
        header("add.php");
    }
}


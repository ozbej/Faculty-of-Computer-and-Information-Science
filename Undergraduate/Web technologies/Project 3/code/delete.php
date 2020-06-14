<?php
session_start();
require_once "NepremicnineDB.php";

if (!isset($_SESSION['loggedin'])) {
    throw new Exception("Prijava potrebna.");
}

$delete = isset($_POST["id"]) && !empty($_POST["id"]);

$nepremicnina = NepremicnineDB::get($_POST["id"]);
if ($nepremicnina["uporabnik_id"] != $_SESSION["id"]) {
    throw new Exception("Niste lastnik nepremicnine.");
}

if ($delete) {
    try {
        NepremicnineDB::delete($_POST["id"]);
        header("Location: index.php");
    } catch (Exception $e) {
        $errorMessage = "A database error occured: $e";
        echo $errorMessage;
    }
}
?>
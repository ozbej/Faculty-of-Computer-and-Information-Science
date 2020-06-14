<?php
require_once "NepremicnineDB.php";
session_start();

if (!isset($_SESSION['loggedin'])) {
    throw new Exception("Prijava potrebna.");
}

$delete = isset($_POST["id"]) && !empty($_POST["id"]);

if ($delete) {
    try {
        NepremicnineDB::deleteComment($_POST["id"]);
    } catch (Exception $e) {
        $errorMessage = "A database error occured: $e";
    }
}
?>
<?php

require_once "DBInit.php";

class UporabnikiDB {

    public static function login($email, $password) {
        $dbh = DBInit::getInstance();

        $query = "SELECT uporabnik_id, ime, priimek, email, geslo FROM uporabniki WHERE email = :email";
        $stmt = $dbh->prepare($query);
        $stmt->bindParam(":email", $email);
        $stmt->execute();

        if ($stmt->rowCount() > 0) return $stmt->fetch();
        else return false;
    }

    public static function getAll($id) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT DISTINCT n.nepremicnina_id, n.ime_nepremicnine, n.cena, k.ime_kraja, t.ime_tipa, 
        (SELECT s.slika FROM slike s WHERE s.nepremicnina_id = n.nepremicnina_id LIMIT 1) as slika
        FROM kraji k INNER JOIN nepremicnine n ON k.kraj_id = n.kraj_id INNER JOIN tip_oglasa t ON n.tip_id = t.tip_id WHERE n.uporabnik_id = :id;");
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();

        return $statement->fetchAll();
    }

    public static function register($ime, $priimek, $email, $geslo) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("INSERT INTO uporabniki (ime, priimek, email, geslo)
            VALUES (:ime, :priimek, :email, :geslo)");
        $ime = htmlspecialchars($ime);
        $statement->bindParam(":ime", $ime);
        $priimek = htmlspecialchars($priimek);
        $statement->bindParam(":priimek", $priimek);
        $email = htmlspecialchars($email);
        $statement->bindParam(":email", $email);
        $geslo = htmlspecialchars($geslo);
        $statement->bindParam(":geslo", $geslo);
            
        $statement->execute();      
    }

    public static function emailZaseden($email) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT email FROM uporabniki WHERE email = :email");
        $statement->bindParam(":email", $email);
        $statement->execute();

        if ($statement->rowCount() > 0) return true;
        else return false;
    }
}

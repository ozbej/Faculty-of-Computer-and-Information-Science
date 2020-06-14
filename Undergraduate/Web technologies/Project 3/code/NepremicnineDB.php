<?php

require_once "DBInit.php";

class NepremicnineDB {

    public static function getAll() {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT DISTINCT n.nepremicnina_id, n.ime_nepremicnine, n.cena, k.ime_kraja, t.ime_tipa, 
        (SELECT s.slika FROM slike s WHERE s.nepremicnina_id = n.nepremicnina_id LIMIT 1) as slika
        FROM kraji k INNER JOIN nepremicnine n ON k.kraj_id = n.kraj_id INNER JOIN tip_oglasa t ON n.tip_id = t.tip_id ORDER BY n.nepremicnina_id;");
        $statement->execute();

        return $statement->fetchAll();
    }

    public static function search($searchString) {
        $db = DBInit::getInstance();

        $statement = $db->prepare('SELECT DISTINCT n.nepremicnina_id, n.ime_nepremicnine, n.cena, k.ime_kraja, t.ime_tipa, 
        (SELECT s.slika FROM slike s WHERE s.nepremicnina_id = n.nepremicnina_id LIMIT 1) as slika
        FROM kraji k INNER JOIN nepremicnine n ON k.kraj_id = n.kraj_id INNER JOIN tip_oglasa t ON n.tip_id = t.tip_id
        WHERE n.ime_nepremicnine LIKE :string OR k.ime_kraja LIKE :string ORDER BY n.nepremicnina_id;');
        $statement->bindValue(":string", "%$searchString%", PDO::PARAM_STR);
        $statement->execute();

        return $statement->fetchAll();
    }

    public static function get($id) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT n.nepremicnina_id, n.ime_nepremicnine, n.opis, n.naslov, n.cena, k.ime_kraja, t.ime_tipa, n.uporabnik_id, n.tel_st
        FROM kraji k INNER JOIN nepremicnine n ON k.kraj_id = n.kraj_id INNER JOIN tip_oglasa t ON n.tip_id = t.tip_id
        WHERE n.nepremicnina_id = :id");
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();

        return $statement->fetch();
    }

    public static function getSlike($id) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT slika_id, nepremicnina_id, ime_slike, slika FROM slike WHERE nepremicnina_id = :id;");
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();

        return $statement->fetchAll();
    }

    public static function getType() {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT tip_id, ime_tipa FROM tip_oglasa");
        $statement->execute();

        return $statement->fetchAll();
    }

    public static function getKraji() {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT kraj_id, ime_kraja FROM kraji ORDER BY ime_kraja");
        $statement->execute();

        return $statement->fetchAll();
    }

    public static function insert($ime, $naslov, $kraj, $opis, $cena, $tip, $userId, $telSt) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("INSERT INTO nepremicnine (kraj_id, tip_id, uporabnik_id, ime_nepremicnine, opis, naslov, cena, tel_st)
            VALUES ((SELECT kraj_id FROM kraji WHERE ime_kraja = :kraj), (SELECT tip_id FROM tip_oglasa WHERE ime_tipa = :tip), :uporabnik_id, :ime_nepremicnine, :opis, :naslov, :cena, :tel_st)");
        $statement->bindParam(":kraj", htmlspecialchars($kraj));
        $statement->bindParam(":tip", htmlspecialchars($tip));
        $statement->bindParam(":uporabnik_id", htmlspecialchars($userId));
        $statement->bindParam(":ime_nepremicnine", htmlspecialchars($ime));
        $statement->bindParam(":opis", htmlspecialchars($opis));
        $statement->bindParam(":naslov", htmlspecialchars($naslov));
        $statement->bindParam(":cena", htmlspecialchars($cena));
        $statement->bindParam(":tel_st", htmlspecialchars($telSt));
            
        $statement->execute();      

        if ($_FILES['slike']['size'][0] != 0) {
            $lastId = $db->lastInsertId();
            $myFile = $_FILES['slike'];
            $fileCount = count($myFile["name"]);
        
            for ($i = 0; $i < $fileCount; $i++) {
                $name = $myFile["name"][$i];
                $tmpFile = $myFile["tmp_name"][$i];
                $data = file_get_contents($tmpFile);

                $stmt = $db->prepare("INSERT INTO slike (nepremicnina_id, ime_slike, slika) VALUES (?, ?, ?)");
                $stmt->execute([$lastId, htmlspecialchars($name), $data]);
            }
        }
    }

    public static function update($id, $ime, $naslov, $kraj, $opis, $cena, $tip, $userId, $telSt) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("UPDATE nepremicnine SET kraj_id = (SELECT kraj_id FROM kraji WHERE ime_kraja = :kraj), tip_id = (SELECT tip_id FROM tip_oglasa WHERE ime_tipa = :tip), 
        uporabnik_id = :uporabnik_id, ime_nepremicnine = :ime_nepremicnine, opis = :opis, naslov = :naslov, cena = :cena, tel_st = :tel_st WHERE nepremicnina_id = :id");
        $statement->bindParam(":kraj", htmlspecialchars($kraj));
        $statement->bindParam(":tip", htmlspecialchars($tip));
        $statement->bindParam(":uporabnik_id", htmlspecialchars($userId));
        $statement->bindParam(":ime_nepremicnine", htmlspecialchars($ime));
        $statement->bindParam(":opis", htmlspecialchars($opis));
        $statement->bindParam(":naslov", htmlspecialchars($naslov));
        $statement->bindParam(":cena", htmlspecialchars($cena));
        $statement->bindParam(":tel_st", htmlspecialchars($telSt));
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
            
        $statement->execute();

        if ($_FILES['slike']['size'][0] != 0) {
            $myFile = $_FILES['slike'];
            $fileCount = count($myFile["name"]);
        
            for ($i = 0; $i < $fileCount; $i++) {
                $name = $myFile["name"][$i];
                $tmpFile = $myFile["tmp_name"][$i];
                $data = file_get_contents($tmpFile);

                $stmt = $db->prepare("INSERT INTO slike (nepremicnina_id, ime_slike, slika) VALUES (?, ?, ?)");
                $stmt->execute([$id, htmlspecialchars($name), $data]);
            }
        }
    }

    public static function delete($id) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("DELETE FROM nepremicnine WHERE nepremicnina_id = :id");
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();
    }

    public static function getComments($nepremicnina_id) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("SELECT komentar_id, nepremicnina_id, uporabnik_id as uid, (SELECT ime FROM uporabniki where uporabnik_id=uid) as ime, 
        (SELECT priimek FROM uporabniki where uporabnik_id=uid) as priimek, komentar, datum FROM komentarji WHERE nepremicnina_id=:nepremicnina_id;");
        $statement->bindParam(":nepremicnina_id", $nepremicnina_id, PDO::PARAM_INT);
        $statement->execute();

        return $statement->fetchAll();
    }

    public static function addComment($nepremicnina_id, $uporabnik_id, $comment) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("INSERT INTO komentarji (nepremicnina_id, uporabnik_id, komentar)
            VALUES (:nepremicnina_id, :uporabnik_id, :comment);");
        $statement->bindParam(":nepremicnina_id", htmlspecialchars($nepremicnina_id));
        $statement->bindParam(":uporabnik_id", htmlspecialchars($uporabnik_id));
        $statement->bindParam(":comment", htmlspecialchars($comment));
            
        $statement->execute();      
    }

    public static function deleteComment($id) {
        $db = DBInit::getInstance();

        $statement = $db->prepare("DELETE FROM komentarji WHERE komentar_id = :id");
        $statement->bindParam(":id", $id, PDO::PARAM_INT);
        $statement->execute();
    }
}

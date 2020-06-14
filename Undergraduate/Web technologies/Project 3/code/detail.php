<?php
session_start();
require_once ("NepremicnineDB.php");
?><!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>Nepremičnina</title>
    <link href="./styles/detail.css" rel="stylesheet" type="text/css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
</head>
<body>
    <?php include "./components/navigation.php" ?>
    <div class="detail">
        <?php $nepremicnina = NepremicnineDB::get($_GET["id"]); ?>
        <h1><?= $nepremicnina["ime_nepremicnine"] ?></h1>
        <p><?= $nepremicnina["naslov"] ?>, <?= $nepremicnina["ime_kraja"] ?></p>
        <p class="bold"><?= $nepremicnina["cena"] ?> EUR</p>
        <p>Tip oglasa: <?= $nepremicnina["ime_tipa"] ?></p>
        <div id="carouselExampleIndicators" class="carousel slide" data-ride="carousel">
            <ol class="carousel-indicators">
            <?php 
                $count = 0;
                foreach (NepremicnineDB::getSlike($_GET["id"]) as $slika): 
                
                if ($count == 0) {
                ?>
                <li data-target="#carouselExampleIndicators" data-slide-to="0" class="active"></li>
                <?php $count++; } else { ?>
                <li data-target="#carouselExampleIndicators" data-slide-to="<?= $count ?>"></li>
                <?php } endforeach; ?>
            </ol>
            <div class="carousel-inner">
                <?php 
                $count = 0;
                foreach (NepremicnineDB::getSlike($_GET["id"]) as $slika): 
                $count++;
                if ($count == 1) {
                ?>
                <div class="carousel-item active">
                    <img class="d-block w-100" src="data:image/jpeg;base64,<?= base64_encode( $slika['slika']) ?>" alt="Slide">
                </div>
                <?php } else { ?>
                <div class="carousel-item">
                    <img class="d-block w-100" src="data:image/jpeg;base64,<?= base64_encode( $slika['slika']) ?>" alt="Slide">
                </div>
                <?php } endforeach; ?>
            </div>
            <a class="carousel-control-prev" href="#carouselExampleIndicators" role="button" data-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="sr-only">Previous</span>
            </a>
            <a class="carousel-control-next" href="#carouselExampleIndicators" role="button" data-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="sr-only">Next</span>
            </a>
        </div>
        <div class="info">
            <p><?= $nepremicnina["opis"] ?></p>
            <p class="bold">Kontakt:<br/><?= $nepremicnina["tel_st"] ?></p>
        </div>
        <?php if (isset($_SESSION["loggedin"]) && $nepremicnina["uporabnik_id"] == $_SESSION["id"]) { ?>
            <div class="buttons">
                <a class="btn btn-primary" href="edit.php?id=<?= $_GET["id"]?>" role="button">Uredi</a>
                <form action="delete.php" method="post" id="delete-form">
                    <input type="hidden" name="id" value="<?= $_GET["id"] ?>"  />
                    <input type="submit" onclick="return myConfirm();" class="btn btn-danger" value="Izbriši" />
                </form>
            </div>
        <?php } ?>
    </div>
    <div class="comments">
        <h2>Komentarji</h2>
        <div id="comments-container">
        </div>
        <?php if(isset($_SESSION["loggedin"])) { ?>
            <div id="comment-form">
                <input type="hidden" name="nepremicnina_id" id="nepremicnina_id" value="<?= $_GET["id"] ?>"  />
                <input type="hidden" name="uporabnik_id" id="uporabnik_id" value="<?= $_SESSION["id"] ?>"  />
                <label for="comment">Dodaj komentar</label>
                <textarea name="comment" id="comment" class="form-control" rows="2" required></textarea>
                <input type="button" onclick="addComment()" id="add-comment" class="btn btn-primary" value="Dodaj" />
            </div>
        <?php } else { ?>
            <input type="hidden" name="nepremicnina_id" id="nepremicnina_id" value="<?= $_GET["id"] ?>"  />
        <?php } ?>
    </div>
    <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
    <script>
        $(document).ready(function(){
            loadComments();
        });

        function addComment() {
            $.post("add_comment.php",
            {
                nepremicnina_id: $("#nepremicnina_id").val(),
                uporabnik_id: $("#uporabnik_id").val(),
                komentar: $("#comment").val(),
            },
            function(data) {
                $("#comment").val("");
                loadComments();
            });
        }

        function loadComments() {
            $.get("load_comments.php",
            {id: $("#nepremicnina_id").val()},
            function(data) {
                console.log(data);
                $("#comments-container").empty();
                data.map(item => {
                    if (item.uid == <?php if (isset($_SESSION["id"])) echo $_SESSION["id"]; else echo "-1"; ?>) {
                        $("#comments-container").append("<div class='comment-box'><span class='ime'>" + item.ime  + " " + 
                            item.priimek + "</span> <span class='datum'>" + item.datum + "</span><p>" + item.komentar + "</p>" + 
                            "<input type='submit' onclick='deleteComment(" + item.komentar_id + ");' class='btn btn-danger' value='Izbriši' /></div>")
                    }
                    else {
                        $("#comments-container").append("<div class='comment-box'><span class='ime'>" + item.ime  + " " + 
                            item.priimek + "</span> <span class='datum'>" + item.datum + "</span><p>" + item.komentar + "</p></div>")
                    }                    
                })
            });
        }

        function myConfirm() {
            var result = confirm("Ali želite izbrisati nepremičnino?");
            if (result) {
                return true;
            } 
            else {
                return false;
            }
        }

        function deleteComment(id) {
            var result = confirm("Ali želite izbrisati komentar?");
            if (result) {
                $.post("delete_comment.php",
                {
                    id: id
                },
                function(data) {
                    $("#comment").val("");
                    loadComments();
                });
            } 
            else {
                return false;
            }
        }
    </script>
</body>
</html>
<?php
session_start();
require_once("NepremicnineDB.php");

if (!isset($_SESSION['loggedin'])) {
    throw new Exception("Prijava potrebna.");
}

$rules = [
    "ime" => [
        "filter" => FILTER_VALIDATE_REGEXP,
        "options" => ["regexp" => "/^[ a-zA-ZšđčćžŠĐČĆŽ\.\-]+$/"]
    ],
    "telst" => [
        "filter" => FILTER_VALIDATE_REGEXP,
        "options" => ["regexp" => "/^[0-9]{3}-[0-9]{3}-[0-9]{3}$/"]
    ],
    "naslov" => FILTER_SANITIZE_SPECIAL_CHARS,
    "kraj" => FILTER_SANITIZE_SPECIAL_CHARS,
    "opis" => FILTER_SANITIZE_SPECIAL_CHARS,
    "cena" => [
        "filter" => FILTER_VALIDATE_INT,
        "options" => ["min_range" => 0]
    ],
    "tip" => FILTER_SANITIZE_SPECIAL_CHARS,
];

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $data = filter_input_array(INPUT_POST, $rules);

    if (empty($data["ime"])) $errors["ime"] = "Vnesite ime.";
    $errors["ime"] = $data["ime"] === false ? "Dovoljene so samo črke, pike, pomišljaji in presledki." : "";
    $errors["telst"] = $data["telst"] === false ? "Format mora biti XXX-XXX-XXX, kjer je X števka." : "";
    if (empty($data["naslov"])) $errors["naslov"] = "Vnesite naslov.";
    if (empty($data["kraj"])) $errors["kraj"] = "Vnesite kraj.";
    $errors["cena"] = $data["cena"] === false ? "Cena mora biti večja od 1." : "";
    if (empty($data["tip"])) $errors["tip"] = "Vnesite tip.";

    $isDataValid = true;
    foreach ($errors as $error) {
        $isDataValid = $isDataValid && empty($error);
    }

    if ($isDataValid) {
        try {
            NepremicnineDB::update($_POST["id"], $_POST["ime"], $_POST["naslov"], $_POST["kraj"], $_POST["opis"], $_POST["cena"], $_POST["tip"], $_SESSION['id'], $_POST["telst"]);
            header(sprintf("Location: detail.php?id=%d", $_POST["id"]));
        } catch (Exception $e) {
            $errorMessage = "Database error occured: $e";
        }
    }
    else {
        try {
            $nepremicnina = NepremicnineDB::get($_REQUEST["id"]);
        } catch (Exception $e) {
            $errorMessage = "A database error occured: $e";
        }
    } 
}
else {
    try {
        $nepremicnina = NepremicnineDB::get($_REQUEST["id"]);
    } catch (Exception $e) {
        $errorMessage = "A database error occured: $e";
    }
} 
?>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Uredi nepremičnino</title>
        <link href="./styles/add.css" rel="stylesheet" type="text/css">
        <link href="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/css/select2.min.css" rel="stylesheet" />
        <script src="https://cdn.jsdelivr.net/npm/select2@4.0.13/dist/js/select2.min.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
        <?php include "./components/navigation.php" ?>
        <div class="container">
            <div class="add">
                <h1>Uredi nepremičnino</h1>
                <?php if (isset($errorMessage)): ?>
                    <p class="important"><?= $errorMessage ?></p>
                <?php endif; ?>
                <form action="<?= $_SERVER["PHP_SELF"] ?>" method="post" id="form" enctype="multipart/form-data">
                <input type="hidden" name="id" value="<?= $nepremicnina["nepremicnina_id"] ?>"  />
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="ime">Ime nepremičnine</label>
                        <input type="text" class="form-control" name="ime" id="ime" value="<?= $nepremicnina["ime_nepremicnine"] ?>" required>
                        <div style="color: #dc3545">
                            <?php if (isset($errors["ime"]) && !empty($errors["ime"])) echo $errors["ime"]; ?>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="telst">Kontaktna telefonska številka</label>
                        <input type="tel" class="form-control" id="telst" name="telst" value="<?= $nepremicnina["tel_st"] ?>" pattern="[0-9]{3}-[0-9]{3}-[0-9]{3}" required>
                        <div style="color: #dc3545">
                            <?php if (isset($errors["telst"]) && !empty($errors["telst"])) echo $errors["telst"]; ?>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="naslov">Naslov nepremičnine</label>
                        <input type="text" class="form-control" name="naslov" id="naslov" value="<?= $nepremicnina["naslov"] ?>" required>
                        <div style="color: #dc3545">
                            <?php if (isset($errors["naslov"]) && !empty($errors["naslov"])) echo $errors["naslov"]; ?>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="kraj">Kraj nepremičnine</label>
                        <select class="js-example-basic-single form-control" id="kraj" name="kraj">
                        <?php foreach (NepremicnineDB::getKraji() as $kraj): 
                            if ($kraj["ime_kraja"] == $nepremicnina["ime_kraja"]) {
                            ?>
                            <option value="<?= $kraj["ime_kraja"] ?>" selected><?= $kraj["ime_kraja"] ?></option>
                            <?php } else { ?>
                            <option value="<?= $kraj["ime_kraja"] ?>"><?= $kraj["ime_kraja"] ?></option>
                            <?php } endforeach; ?>
                        </select>
                        <div style="color: #dc3545">
                            <?php if (isset($errors["kraj"]) && !empty($errors["kraj"])) echo $errors["kraj"]; ?>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="opis">Opis nepremičnine</label>
                    <textarea rows="3" cols="50" class="form-control" name="opis" id="opis" form="form"><?= $nepremicnina["opis"] ?></textarea>
                    <div style="color: #dc3545">
                        <?php if (isset($errors["opis"]) && !empty($errors["opis"])) echo $errors["opis"]; ?>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-4">
                        <label for="cena">Cena (v EUR)</label>
                        <input type="number" class="form-control" id="cena" name="cena" min="1" value=<?= $nepremicnina["cena"] ?> required>
                        <div style="color: #dc3545">
                            <?php if (isset($errors["cena"]) && !empty($errors["cena"])) echo $errors["cena"]; ?>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="tip">Tip oglasa</label>
                        <select id="tip" name="tip" class="form-control">                            
                            <?php foreach (NepremicnineDB::getType() as $type): 
                                if ($type["ime_tipa"] == $nepremicnina["ime_tipa"]) {?>
                                <option value="<?= $type["ime_tipa"] ?>" selected><?= $type["ime_tipa"] ?></option>
                                <?php } else { ?>
                                    <option value="<?= $type["ime_tipa"] ?>"><?= $type["ime_tipa"] ?></option>
                            <?php } endforeach; ?>
                        </select>
                        <div style="color: #dc3545">
                            <?php if (isset($errors["tip"]) && !empty($errors["tip"])) echo $errors["tip"]; ?>
                        </div>
                    </div>
                    <div class="form-group col-md-4">
                        <label for="slike">Slike</label>
                        <input type="file" class="form-control" name="slike[]" id="slike" accept="image/*" multiple/>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Uredi nepremičnino</button>
                </form>
            </div>
        </div>
        <script>
            $(document).ready(function() {
                $('.js-example-basic-single').select2();
            });
        </script>
	</body>
</html>
<?php
require_once("UporabnikiDB.php");

unset($regError);
unset($success);

$register = isset($_POST["ime"]) && !empty($_POST["ime"]) && 
        isset($_POST["priimek"]) && !empty($_POST["priimek"]) &&
        isset($_POST["email"]) && !empty($_POST["email"]) &&
        isset($_POST["password"]) && !empty($_POST["password"]) &&
        isset($_POST["password2"]) && !empty($_POST["password2"]);

$rules = [
    "ime" => [
        "filter" => FILTER_VALIDATE_REGEXP,
        "options" => ["regexp" => "/^[ a-zA-ZšđčćžŠĐČĆŽ\.\-]+$/"]
    ],
    "priimek" => [
        "filter" => FILTER_VALIDATE_REGEXP,
        "options" => ["regexp" => "/^[ a-zA-ZšđčćžŠĐČĆŽ\.\-]+$/"]
    ],
    "email" => [
		"filter" => FILTER_VALIDATE_REGEXP,
		"options" => ["regexp" => "/^.+@.+$/"]
	],
    "password" => FILTER_SANITIZE_SPECIAL_CHARS,
    "password2" => FILTER_SANITIZE_SPECIAL_CHARS,
];

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    $data = filter_input_array(INPUT_POST, $rules);

    $errors["ime"] = $data["ime"] === false ? "Dovoljene so samo črke, pike, pomišljaji in presledki." : "";
    $errors["priimek"] = $data["priimek"] === false ? "Dovoljene so samo črke, pike, pomišljaji in presledki." : "";
    $errors["email"] = $data["email"] === false ? "Mail mora vsebovati znake pred afno, afno in znake po afni." : "";
	if (empty($data["password"])) $errors["password"] = "Vnesite geslo.";
	if (empty($data["password2"])) $errors["password2"] = "Vnesite ponovitev gesla.";

    $isDataValid = true;
    foreach ($errors as $error) {
        $isDataValid = $isDataValid && empty($error);
    }

    if ($isDataValid) {
        try {
            if ($_POST["password"] != $_POST["password2"]) {
                $regError = "Gesli se ne ujemata.";
            }
            else {
                if(UporabnikiDB::emailZaseden($_POST["email"])) {
                    $regError = "E-mail je že v uporabi.";
                }
                else {
                    $hashed = password_hash($_POST["password"], PASSWORD_DEFAULT);
                    UporabnikiDB::register($_POST["ime"], $_POST["priimek"], $_POST["email"], $hashed);
                    $success = 'Registracija uspešna. <a href="login.php">Prijavite se</a>.';
                }
            }
        } catch (Exception $e) {
            $errorMessage = "Database error occured: $e";
        }
    }
    else {
    } 
}
?>


<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Registracija</title>
        <link href="./styles/register.css" rel="stylesheet" type="text/css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
        <?php include "./components/navigation.php" ?>
        <div class="container">
            <div class="register">
                <h1>Vpišite svoje podatke</h1>
                <?php if (isset($errorMessage)): ?>
                    <p class="important"><?= $errorMessage ?></p>
                <?php endif; ?>
                <form action="<?= $_SERVER["PHP_SELF"] ?>" method="post" id="form" enctype="multipart/form-data">
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="ime">Ime</label>
                        <input type="text" class="form-control" name="ime" id="ime" placeholder="Janez" required>
                        <div style="color: #dc3545">
                            <?php if (isset($errors["ime"]) && !empty($errors["ime"])) echo $errors["ime"]; ?>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="priimek">Priimek</label>
                        <input type="text" class="form-control" name="priimek" id="priimek" placeholder="Novak" required>
                        <div style="color: #dc3545">
                            <?php if (isset($errors["priimek"]) && !empty($errors["priimek"])) echo $errors["priimek"]; ?>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label for="email">E-mail</label>
                    <input type="email" class="form-control" name="email" placeholder="janez.novak@gmail.com" id="email" required>
                    <div style="color: #dc3545">
                            <?php if (isset($errors["email"]) && !empty($errors["email"])) echo $errors["email"]; ?>
                        </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="ime">Geslo</label>
                        <input type="password" class="form-control" name="password" placeholder="Geslo" id="password" required>
                        <div style="color: #dc3545">
                            <?php if (isset($errors["password"]) && !empty($errors["password"])) echo $errors["password"]; ?>
                        </div>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="ime">Ponovno vpišite geslo</label>
                        <input type="password" class="form-control" name="password2" placeholder="Geslo" id="password2" required>
                        <div style="color: #dc3545">
                            <?php if (isset($errors["password2"]) && !empty($errors["password2"])) echo $errors["password2"]; ?>
                        </div>
                    </div>
                </div>
                <div style="font-size:15px; color:#cc0000; margin:3px 0 10px 0"><?php if (isset($regError)) echo $regError; ?></div>
                <button type="submit" class="btn btn-primary">Registracija</button>
                </form>
                <div style="font-size:15px; color:green; margin:10px 0 10px 0"><?php if (isset($success)) echo $success; ?></div>
                <p style="margin-top: 5%;">Že imate račun? <a href="login.php">Prijavite se</a>.</p>
            </div>
        </div>
        <script>
            $(document).ready(function() {
                $('.js-example-basic-single').select2();
            });
        </script>
	</body>
</html>
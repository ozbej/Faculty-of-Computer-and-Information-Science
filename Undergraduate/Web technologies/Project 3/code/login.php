<?php
unset($error);
require_once("UporabnikiDB.php");
session_start();

$login = isset($_POST["email"]) && !empty($_POST["email"]) &&
		isset($_POST["password"]) && !empty($_POST["password"]);

$rules = [
	"email" => [
		"filter" => FILTER_VALIDATE_REGEXP,
		"options" => ["regexp" => "/^.+@.+$/"]
	],
	"password" => FILTER_SANITIZE_SPECIAL_CHARS
];

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
	$data = filter_input_array(INPUT_POST, $rules);
	$errors["email"] = $data["email"] === false ? "Mail mora vsebovati znake pred afno, afno in znake po afni." : "";
	if (empty($data["password"])) $errors["password"] = "Vnesite geslo.";

	$isDataValid = true;
    foreach ($errors as $error) {
        $isDataValid = $isDataValid && empty($error);
    }

    if ($isDataValid) {
		$uporabnik = UporabnikiDB::login($_POST["email"], $_POST["password"]);
		if (!$uporabnik) {
			$error = "Napačen e-mail ali geslo.";
		}
		else {
			if (password_verify($_POST['password'], $uporabnik["geslo"])) {
				session_regenerate_id();
				$_SESSION['loggedin'] = TRUE;
				$_SESSION['name'] = $uporabnik["ime"];
				$_SESSION['lastname'] = $uporabnik["priimek"];
				$_SESSION['email'] = $uporabnik["email"];
				$_SESSION['id'] = $uporabnik["uporabnik_id"];
				header("Location: index.php");
			} else {
				$error = "Napačen e-mail ali geslo.";
			}
		}
	}
}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Prijava</title>
		<link href="styles/login.css" rel="stylesheet" type="text/css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<?php include "./components/navigation.php" ?>
		<div class="container">
			<div class="login">
				<h1>Prijava</h1>
				<form action="<?= $_SERVER["PHP_SELF"] ?>" method="post" id="form" enctype="multipart/form-data">
					<div class="form-group">
						<label for="email">E-mail</label>
						<input type="email" class="form-control" name="email" placeholder="janez.novak@gmail.com" id="email" required>
						<div style="color: #dc3545">
                            <?php if (isset($errors["email"]) && !empty($errors["email"])) echo $errors["email"]; ?>
                        </div>
					</div>
					<div class="form-group">
						<label for="geslo">Geslo</label>
						<input type="password" class="form-control" name="password" placeholder="Geslo" id="password" required>
						<div style="color: #dc3545;"><?php 
							if (isset($errors["password"]) && !empty($errors["password"])) echo $errors["password"];
							else if (isset($error)) echo $error; 
						?></div>
					</div>
					<button type="submit" class="btn btn-primary">Prijava</button>
				</form>
				<p style="margin-top: 5%;">Še nimate računa? <a href="register.php">Registrirajte se</a>.</p>
			</div>
		</div>
		<script>
			
		</script>
	</body>
</html>
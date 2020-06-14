<?php
session_start();
require_once("UporabnikiDB.php");

if (!isset($_SESSION['loggedin'])) {
    throw new Exception("Prijava potrebna.");
}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Vaše nepremičnine</title>
		<link href="./styles/index.css" rel="stylesheet" type="text/css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<?php include "./components/navigation.php" ?>
		<div class="nepremicnine">
			<h1>Vaši oglasi</h1>
			<div class="nepremicnine-container">
				<?php foreach (UporabnikiDB::getAll($_SESSION["id"]) as $nepremicnina): ?>
					<a href="detail.php?id=<?= $nepremicnina["nepremicnina_id"] ?>">
						<div class="nepremicnina-box">
							<img class="nepremicnina-thumbnail" src="data:image/jpeg;base64,<?= base64_encode( $nepremicnina['slika']) ?>"/>
							<p class="bold"><?= $nepremicnina["ime_nepremicnine"] ?></p>
							<p><?= $nepremicnina["ime_kraja"] ?></p>
						</div>
					</a>
    			<?php endforeach; ?>
			</div>
		</div>
	</body>
</html>
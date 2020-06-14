<?php
session_start();
require_once("NepremicnineDB.php");
?>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Nepremičnine app</title>
		<link href="./styles/index.css" rel="stylesheet" type="text/css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<?php include "./components/navigation.php" ?>
		<div class="parallax">
			<h1>Dobrodošli na Nepremičnine app</h1>
			<?php if (!isset($_SESSION['loggedin'])) { ?>
			<button type="button" class="btn btn-light btn-lg btn-objavi" onclick="objaviButtonClicked()">Objavi oglas</button>
			<?php } else { ?>
			<a class="btn btn-light btn-lg btn-objavi" href="add.php" role="button">Objavi oglas</a>
			<?php } ?>
			<form action="search-vue.php" class="form-inline search" method="get">
				<input class="form-control" type="search" name="query" placeholder="Poišči nepremičnine" aria-label="Search" required>
				<button class="btn btn-light" type="submit"><i class="fa fa-search"></i></button>
			</form>
		</div>
		<div class="nepremicnine">
			<h1>Vse nepremičnine</h1>
			<div class="nepremicnine-container">
				<?php foreach (NepremicnineDB::getAll() as $nepremicnina): ?>
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
		<script>
		function objaviButtonClicked() {
			alert("Za objavo oglasa se prijavite.");
		}
		</script>
	</body>
</html>
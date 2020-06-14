<?php
session_start();

if (!isset($_SESSION['loggedin'])) {
    throw new Exception("Prijava potrebna.");
}
?>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<title>Profil</title>
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.1/css/all.css">
		<link href="styles/profile.css" rel="stylesheet" type="text/css">
        <meta name="viewport" content="width=device-width, initial-scale=1">
	</head>
	<body>
		<?php include "./components/navigation.php" ?>
		<div class="profile-container">
                <h1>Vaši podatki</h1>
                <img src="sources/avatar.png" alt="Avatar" class="avatar-profile">
            <div class="profile-data">
                <div class="profile-data-item">
                    <b>Ime:</b>
                    <?= $_SESSION["name"] ?>
                </div>
                <div class="profile-data-item">
                    <b>Priimek:</b>
                    <?= $_SESSION["lastname"] ?>
                </div>
                <div class="profile-data-item">
                    <b>E-mail:</b>
                    <?= $_SESSION["email"] ?>
                </div>
                <p class="link">Oglejte si <a href="user_nepremicnine.php">vaše oglase</a>.</p>
            </div>
		</div>
	</body>
</html>
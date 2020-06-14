<?php
session_start();
require_once("NepremicnineDB.php");
?>
<!DOCTYPE html>

<meta charset="UTF-8" />
<link href="./styles/index.css" rel="stylesheet" type="text/css">
<title>AJAX Book search</title>

<?php include "./components/navigation.php" ?>

<div id="app" class="nepremicnine">
    <h1 style="padding-bottom: 0">Iskanje nepremičnin</h1>
    <input id="search-vue" class="form-control" type="text" name="query" value="<?= $_GET["query"] ?>" aria-label="Search" autocomplete="off" v-on:keyup="search">
    <div class="nepremicnine-container">
            <a v-for="nepremicnina in nepremicnine" :href="'detail.php?id=' + nepremicnina.nepremicnina_id">
                <div class="nepremicnina-box">
                    <img class="nepremicnina-thumbnail" :src="'data:image/jpeg;base64,' + nepremicnina.slika"/>
                    <p class="bold">{{ nepremicnina.ime_nepremicnine }}</p>
                    <p>{{ nepremicnina.ime_kraja }}</p>
                </div>
            </a>
    </div>
</div>

<script src="https://unpkg.com/vue"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script>
const app = new Vue({
    el: '#app',
    data: {
        nepremicnine: []
    },
    methods: {
        search(event) {
            console.log(event);
            const query = event.target.value
            if (query == "") {
                app.nepremicnine = []
                return
            }

            axios.get(
                "vue.php",
                { params: { query } }
            ).then(response => {
                app.nepremicnine = response.data;
            }
            ).catch(error => console.log(error))
        }
    },
    beforeMount() {
        const query = "<?= $_GET["query"] ?>";
            if (query == "") {
                app.nepremicnine = []
                return
            }

            axios.get(
                "vue.php",
                { params: { query } }
            ).then(response => {
                app.nepremicnine = response.data;
            }
            ).catch(error => console.log(error))
    }
});
</script>
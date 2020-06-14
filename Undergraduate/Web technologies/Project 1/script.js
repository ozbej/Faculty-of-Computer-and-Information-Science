let income = parseFloat(0);
let expenses = parseFloat(0);
let incomeArray = new Array();
let expensesArray = new Array();
let itemId = 0;
filterString = ""
let dataIncome = [
    {category: "Salary", number: 0, color: "#47c4c1"},
    {category: "Passive income", number: 0, color: "#00d9d2"},
    {category: "Other", number: 0, color: "#039994"}
]
let dataExpenses = [
    {category: "Entertainment", number: 0, color: "#FF5049"},
    {category: "Food and drink", number: 0, color: "#ff7873"},
    {category: "Home", number: 0, color: "#f2221b"},
    {category: "Life", number: 0, color: "#bf0c06"},
    {category: "Other", number: 0, color: "#991e1a"}
]

function changeCashAmount() {
    $("#income-amount").text(`+ ${income.toFixed(2)} €`);
    $("#expenses-amount").text(`- ${expenses.toFixed(2)} €`);

    if (income - expenses > 0)
        $("#available-budget-amount").text(`+ ${(income - expenses).toFixed(2)} €`);
    else if (income - expenses < 0)
        $("#available-budget-amount").text(`- ${(expenses - income).toFixed(2)} €`);
    else
        $("#available-budget-amount").text("0.00 €");
}

function updateBudget() {
    dataIncome = [
        {category: "Salary", number: 0, color: "#55faf5"},
        {category: "Passive income", number: 0, color: "#00d9d2"},
        {category: "Other", number: 0, color: "#039994"}
    ]
    dataExpenses = [
        {category: "Entertainment", number: 0, color: "#FF5049"},
        {category: "Food and drink", number: 0, color: "#ff7873"},
        {category: "Home", number: 0, color: "#f2221b"},
        {category: "Life", number: 0, color: "#bf0c06"},
        {category: "Other", number: 0, color: "#991e1a"}
    ]

    incomeArray.map(item => {
        dataIncome.map(category => {
            if (item.category == category.category) {
                category.number += parseFloat(item.quantity);
            }
        })
    })
    expensesArray.map(item => {
        dataExpenses.map(category => {
            if (item.category == category.category) {
                category.number += parseFloat(item.quantity);
            }
        })
    })
    drawIncomeChart();
    drawExpensesChart();

    $(".item").remove();

    console.log(incomeArray)

    incomeArray.filter(item => item.desc.toLowerCase().includes(filterString.toLowerCase())).map(item =>
        $("#income").append(`<div id="${item.id}" class="item" draggable="true" ondragstart="drag(event)" ondragend="dragLeave()"><span>${item.desc}</span><span class="category">${item.category}</span><p class="item-income">+ ${parseFloat(item.quantity)} €<button id="delete-income" class="icon"><i class="fa fa-times-circle"></i></button></p></div>`)
    )
    expensesArray.filter(item => item.desc.toLowerCase().includes(filterString.toLowerCase())).map(item => 
        $("#expenses").append(`<div id="${item.id}" class="item" draggable="true" ondragstart="drag(event)" ondragend="dragLeave()"><span>${item.desc}</span><span class="category">${item.category}</span><p class="item-expense">- ${parseFloat(item.quantity)} €<button id="delete-expense" class="icon"><i class="fa fa-times-circle"></i></button></p></div>`)
    )

    changeCashAmount();
}

function searchPress() {
    filterString = document.querySelector("#search").value;
    updateBudget();
}

function cancelSearch() {
    filterString = "";
    document.querySelector("#search").value = "";
    updateBudget();
}

function deleteIncome(id) {
    incomeArray = incomeArray.filter(item => {
        if (item.id == id) income -= item.quantity;
        return item.id !== id
    })
    updateBudget();
}

function deleteExpense(id) {
    expensesArray = expensesArray.filter(item => {
        if (item.id == id) expenses -= item.quantity;
        return item.id !== id
    })
    updateBudget();
}

$("#expenseType" ).change(function() {
    let selectedOption = $('#expenseType').find(":selected").text();
    if (selectedOption === "+") {
        $("#add-button").css("background-color", "#28B9B5");
        $("#expensesCategory").css("display", "none");
        $("#incomeCategory").css("display", "block");
    }
    else {
        $("#add-button").css("background-color", "#FF5049")
        $("#incomeCategory").css("display", "none")
        $("#expensesCategory").css("display", "block")
    }
});

$("#add-button").click(function() {
    const type = $('#expenseType').find(":selected").text();
    const desc = document.querySelector("#description").value;
    const quantity = document.querySelector("#quantity").value;
    let category;
    let typeCategory;

    if (type === "+") {
        category = $('#incomeCategory').find(":selected").text();
        typeCategory = document.getElementById("incomeCategory");
    }
    else {
        category = $('#expensesCategory').find(":selected").text();
        typeCategory = document.getElementById("expensesCategory");
    }

    const item = {
        id: "item" + itemId,
        category: category,
        desc: desc,
        quantity: parseFloat(quantity)
    }
    itemId++;

    let typeObject = document.getElementById("expenseType");
    let descObject = document.getElementById("description");
    let quantityObject = document.getElementById("quantity");

    // Check input validity
    if (!typeObject.checkValidity() || !typeCategory.checkValidity() || !descObject.checkValidity() || !quantityObject.checkValidity()) {
        document.querySelector("#description").value = "";
        document.querySelector("#quantity").value = 0;
        alert("Invalid entry! Try again.");
        return;
    }

    if (desc === "" || quantity == 0) {
        alert("You must enter description and value!");
        return;
    }

    if (type === "+"){
        income += parseFloat(quantity);
        incomeArray.push(item);
    }
    else {
        expenses += parseFloat(quantity);
        expensesArray.push(item);
    }

    updateBudget();
    document.querySelector("#description").value = "";
    document.querySelector("#quantity").value = 0;
    $('#sortType option[value=default]').prop('selected', true);
})

$(document).on("mouseenter", ".item", function() {
    $(this.lastChild.lastChild).css("display", "inline");
    $(this).css("background-color", "lightgray");
})

$(document).on("mouseleave", ".item", function() {
    $(this.lastChild.lastChild).css("display", "none");
    $(this).css("background-color", "white");
})

$(document).on("click", "#delete-income", function() {
    let del = confirm("Are you sure you want to delete this entry?");
    if (del) {
        deleteIncome($(this).parent().parent().attr("id"));
    }
})

$(document).on("click", "#delete-expense", function() {
    let del = confirm("Are you sure you want to delete this entry?");
    if (del) {
        deleteExpense($(this).parent().parent().attr("id"));
    }
})

$("#sortType" ).change(function() {
    let selectedOption = $('#sortType').find(":selected").val();
    if (selectedOption === "valueAsc") {
        incomeArray.sort((a, b) => (a.quantity > b.quantity) ? 1: -1);
        expensesArray.sort((a, b) => (a.quantity > b.quantity) ? 1: -1);
    }
    else if (selectedOption === "valueDesc") {
        incomeArray.sort((a, b) => (a.quantity < b.quantity) ? 1: -1);
        expensesArray.sort((a, b) => (a.quantity < b.quantity) ? 1: -1);
    }
    else if (selectedOption === "category") {
        incomeArray.sort((a, b) => (a.category > b.category) ? 1: -1);
        expensesArray.sort((a, b) => (a.category > b.category) ? 1: -1);
    }
    updateBudget();
});

// Drag and drop
function allowDrop(ev) {
    ev.preventDefault();
    $("#trash-closed").css("display", "none")
    $("#trash-open").css("display", "block")
}
function drag(ev) {
    ev.dataTransfer.setData("Text",ev.target.id);
}
function drop(ev) {
    ev.preventDefault();
    var data=ev.dataTransfer.getData("Text");
    var el = document.getElementById(data);
    let del = confirm("Are you sure you want to delete this entry?");
    if (del) {
        deleteIncome($(el).attr("id"));
        deleteExpense($(el).attr("id"));
    }
}

function dragLeave() {
    $("#trash-closed").css("display", "block")
    $("#trash-open").css("display", "none")
}

// Charts
function drawIncomeChart() {
    let canvas = document.getElementById("canvasIncome");
    let ctx = canvas.getContext("2d");
    canvas.width = 450;
    canvas.height = 400;
    let total = dataIncome.reduce((ttl, category) => { return ttl + category.number }, 0);
    let startAngle = 0;
    let radius = 100;
    let cx = canvas.width/2;
    let cy = canvas.height/2;

    dataIncome.forEach(category => {
        ctx.fillStyle = category.color;
        ctx.lineWidth = 1;
        ctx.strokeStyle = '#333';
        ctx.beginPath();
        let endAngle = ((category.number / total) * Math.PI * 2) + startAngle;
        ctx.moveTo(cx, cy);
        ctx.arc(cx, cy, radius, startAngle, endAngle, false);
        ctx.lineTo(cx, cy);
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
        ctx.beginPath();
        ctx.font = '20px Helvetica, Calibri';
        ctx.textAlign = 'center';
        ctx.fillStyle = '#555';
        let theta = (startAngle + endAngle) / 2;
        let deltaY = Math.sin(theta) * 1.5 * radius;
        let deltaX = Math.cos(theta) * 1.5 * radius;
        if (category.number > 0) ctx.fillText(category.category, deltaX+cx, deltaY+cy);
        ctx.closePath();
        
        startAngle = endAngle;
    })
}

function drawExpensesChart() {
    let canvas = document.getElementById("canvasExpenses");
    let ctx = canvas.getContext("2d");
    canvas.width = 450;
    canvas.height = 400;
    let total = dataExpenses.reduce((ttl, category) => { return ttl + category.number }, 0);
    let startAngle = 0;
    let radius = 100;
    let cx = canvas.width/2;
    let cy = canvas.height/2;

    dataExpenses.forEach(category => {
        ctx.fillStyle = category.color;
        ctx.lineWidth = 1;
        ctx.strokeStyle = '#333';
        ctx.beginPath();
        let endAngle = ((category.number / total) * Math.PI * 2) + startAngle;
        ctx.moveTo(cx, cy);
        ctx.arc(cx, cy, radius, startAngle, endAngle, false);
        ctx.lineTo(cx, cy);
        ctx.fill();
        ctx.stroke();
        ctx.closePath();
        ctx.beginPath();
        ctx.font = '20px Helvetica, Calibri';
        ctx.textAlign = 'center';
        ctx.fillStyle = '#555';
        let theta = (startAngle + endAngle) / 2;
        let deltaY = Math.sin(theta) * 1.5 * radius;
        let deltaX = Math.cos(theta) * 1.5 * radius;
        if (category.number > 0) ctx.fillText(category.category, deltaX+cx, deltaY+cy);
        ctx.closePath();
        
        startAngle = endAngle;
    })
}

document.addEventListener("DOMContentLoaded", () => {
    let selectedOption = $('#expenseType').find(":selected").text();
    if (selectedOption === "+") {
        $("#add-button").css("background-color", "#28B9B5");
        $("#expensesCategory").css("display", "none");
        $("#incomeCategory").css("display", "block");
    }
    else {
        $("#add-button").css("background-color", "#FF5049")
        $("#incomeCategory").css("display", "none")
        $("#expensesCategory").css("display", "block")
    }
})
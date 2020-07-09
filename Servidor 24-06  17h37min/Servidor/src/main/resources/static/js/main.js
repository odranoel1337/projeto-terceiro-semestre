
//----------------------------------------------------------------------//
//-------------------------- ANIMAÇÕES DO CSS --------------------------//
//----------------------------------------------------------------------//

var inputs = document.querySelectorAll(".input");

function addcl(){
	let parent = this.parentNode.parentNode;
	parent.classList.add("focus");
}

function remcl(){
	let parent = this.parentNode.parentNode;
	if(this.value == ""){
		parent.classList.remove("focus");
	}
}


inputs.forEach(input => {
	input.addEventListener("focus", addcl);
	input.addEventListener("blur", remcl);
});

//----------------------------------------------------------------------//
//-------------------- VERIFICA LOGIN E SENHA PARA LOGAR ---------------//
//----------------------------------------------------------------------//

function verificaAdmin(){
	var username = document.getElementById("username").value;
	var password = document.getElementById("password").value;

	if(username == "adm" && password == "adm"){
		window.location.href = "paginaEscolha.html"
		alert("Logado com Sucesso!")

	}else{
		alert("Acesso Negado.")
	}
}

//----------------------------------------------------------------------//
//---------------- FUNÇÕES PARA MANDAR PARA OUTRA PÁGINA ---------------//
//----------------------------------------------------------------------//

function pagEscolha() {
 	window.location.href = "paginaEscolha.html"
}

function addCampanhas(){
	window.location.href = "paginaAddCampanhas.html"
}

function addProdutos(){
	window.location.href = "paginaAddProdutos.html"
}

function verCampanha(){
	window.location.href = "paginaVerCampanhas.html"
}

function verProduto(){
	window.location.href = "paginaVerProdutos.html"
}

//----------------------------------------------------------------------//
//-------------------- CADASTRA A CAMPANHA NO BANCO --------------------//
//----------------------------------------------------------------------//

function cadastrarCampanha() {
	var nome_campanhav = document.getElementById("nomecampanha").value;
	var descricaov = document.getElementById("descricaocampanha").value;
	var metav = document.getElementById("metacampanha").value;
	var data_iniciov = document.getElementById("datainicio").value;
	var data_fimv = document.getElementById("datafim").value;

	var inst = {};
	inst.nome_campanha = document.getElementById("nomecampanha").value;
	inst.descricao = document.getElementById("descricaocampanha").value;
	inst.meta = document.getElementById("metacampanha").value;
	inst.arrecadado = 0;
	inst.data_inicio = document.getElementById("datainicio").value;
	inst.data_fim = document.getElementById("datafim").value;
	inst.tipo_doacao = document.getElementById("appearance-select").value;
	if(nome_campanhav == "" || descricaov == "" || metav == "" || data_iniciov == "" || data_fimv == ""){
		alert("Preencha todos os campos!")
	}else {

		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function () {
			if (this.readyState == 4 && this.status == 200) {
				window.location.replace("paginaEscolha.html");
				alert("Campanha Adicionada com Sucesso!")
			}
		};
		xhttp.open("POST", "/campanhaCadastro", true);
		xhttp.setRequestHeader("Content-Type", "application/json");
		xhttp.send(JSON.stringify(inst));
	}
}

//----------------------------------------------------------------------//
//-------------------- CADASTRA O PRODUTO NO BANCO ---------------------//
//----------------------------------------------------------------------//

function cadastrarProduto() {
	var tipo_produtov = document.getElementById("tipoproduto").value;
	var nome_produtov = document.getElementById("nomeproduto").value;
	var unidadev = document.getElementById("unidadeproduto").value;

	var inst = {};
	inst.tipo_produto = document.getElementById("tipoproduto").value;
	inst.nome_produto = document.getElementById("nomeproduto").value;
	inst.unidade = document.getElementById("unidadeproduto").value;

	if(tipo_produtov == "" || nome_produtov == "" || unidadev == ""){
		alert("Preencha todos os campos!")
	}else {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function () {
			if (this.readyState == 4 && this.status == 200) {
				window.location.replace("paginaEscolha.html");
				alert("Produto Adicionado com Sucesso!")
			}
		};
		xhttp.open("POST", "/produtoCadastro", true);
		xhttp.setRequestHeader("Content-Type", "application/json");
		xhttp.send(JSON.stringify(inst));
	}
}

//----------------------------------------------------------------------//
//------------ CRIA UMA TABELA PARA EXIBIR AS CAMPANHAS ----------------//
//----------------------------------------------------------------------//

function exibirCampanhas(instList) {
	var tabela = "<table width='70%' class=\"bordaSimples\">";
	for (var i = 0; i < instList.length; i++) {
		var inst = instList[i];
		var linha = "<tr>" +
			"<td>" + inst.nome_campanha + "</td>" +
			"<td>" + inst.meta + "</td>" +
			"<td>" + inst.data_inicio + "</td>" +
			"<td>" + inst.data_fim + "</td>" +
			"<td>" + inst.tipo_doacao + "</td>" +
			'<td><a href="paginaCampanhaEspecifica.html?id='+ inst.id + '">Detalhes</a></td>' +
			'<td> <center> <button class="smallbtn" onclick="excluirCampanha(' + inst.id + ')">Excluir</button></center></td>' +
			"<tr>";

		tabela += linha;
	}
    tabela +="</table>";
    document.getElementById("divPrincipal").innerHTML = tabela;
}

//----------------------------------------------------------------------//
//------------ CRIA UMA TABELA PARA EXIBIR OS PRODUTOS -----------------//
//----------------------------------------------------------------------//

function exibirProdutos(instList) {
	var tabela = "<table width='50%' class=\"bordaSimples\">";
	for (var i = 0; i < instList.length; i++) {
		var inst = instList[i];
		var linha = "<tr>" +
			"<td>" + inst.nome_produto + "</td>" +
			"<td>" + inst.tipo_produto + "</td>" +
			"<td>" + inst.unidade + "</td>" +
			'<td> <center> <button class="smallbtn" onclick="excluirProduto(' + inst.id + ')">Excluir</button></center></td>' +
			"<tr>";

		tabela += linha;
	}
	tabela +="</table>";
	document.getElementById("divPrincipal").innerHTML = tabela;
}

//----------------------------------------------------------------------//
//-------------------- BUSCA AS CAMPANHAS EXISTENTES -------------------//
//----------------------------------------------------------------------//

function buscarCampanhas() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resp = JSON.parse(this.responseText);
			exibirCampanhas(resp);
		}
	};
	xhttp.open("GET", "/listarCampanhas/", true);
	xhttp.send();
}

//----------------------------------------------------------------------//
//-------------------- BUSCA OS PRODUTOS EXISTENTES -------------------//
//----------------------------------------------------------------------//

function buscarProdutos() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resp = JSON.parse(this.responseText);
			exibirProdutos(resp);
		}
	};
	xhttp.open("GET", "/listarProdutos/", true);
	xhttp.send();
}

//----------------------------------------------------------------------//
//---------- BUSCA TODOS OS DADOS DE UMA CAMPANHA ESPECIFICA -----------//
//----------------------------------------------------------------------//

function buscarCampanha() {
	var url = new URL(window.location.href);
	var id = url.searchParams.get("id");
	if (id == null) {
		alert("ID não encontrado.");
		return;
	}
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			var resp = JSON.parse(this.responseText);
			exibirCampanha(resp);
		}
	};
	xhttp.open("GET", "/listarCampanhas/" + id, true);
	xhttp.send();
}

//----------------------------------------------------------------------//
//-------------------------- EXCLUI A CAMPANHA -------------------------//
//----------------------------------------------------------------------//

function excluirCampanha(id) {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            window.location.replace("paginaVerCampanhas.html");
        }
    };
    if(confirm('Você tem certeza que deseja excluir essa Campanha?')) {
		xhttp.open("DELETE", "/campanhaDeleteID/" + id, true);
		xhttp.send();
	}else {}
}

//----------------------------------------------------------------------//
//-------------------------- EXCLUI O PRODUTO --------------------------//
//----------------------------------------------------------------------//

function excluirProduto(id) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			window.location.replace("paginaVerProdutos.html");
		}
	};
	if(confirm('Você tem certeza que deseja excluir esse Produto?')) {
		xhttp.open("DELETE", "/produtoDeleteID/" + id, true);
		xhttp.send();
	}else {}
}

//----------------------------------------------------------------------//
//------------------- EXIBE A CAMPANHA EM ESPECIFICO -------------------//
//----------------------------------------------------------------------//

function exibirCampanha(inst) {
	document.getElementById("nome_campanha").value = inst.nome_campanha;
	document.getElementById("meta_campanha").value = inst.meta;
	document.getElementById("arrecadado_campanha").value = inst.arrecadado;
	document.getElementById("descricao_campanha").value = inst.descricao;
	document.getElementById("data_inicio").value = inst.data_inicio;
	document.getElementById("data_fim").value = inst.data_fim;
	document.getElementById("tipo_produto").value = inst.tipo_doacao;
}


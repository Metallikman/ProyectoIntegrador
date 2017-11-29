<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	
	if($_SERVER['REQUEST_METHOD']=="POST"){

		$conn=new conectionSQL();
		$conn->startConection();

		if(isset($_POST['idTicket']) && isset($_POST['idUser'])){
			$idTicket=$_POST['idTicket'];
			$idUser=$_POST['idUser'];
			$sql="UPDATE id3295737_rebuc.tickets SET ti_bibliotecario=$idUser WHERE ti_folio=$idTicket";
			$conn->update($sql);
			echo '[{"success":"Bibliotecario cambiado."}]';
		}else{
			echo '[{"error":"No ha ingresado los datos."}]';
		}
					
	}
	
?>
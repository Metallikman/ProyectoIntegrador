<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	
	if($_SERVER['REQUEST_METHOD']=="POST"){

		$conn=new conectionSQL();
		$conn->startConection();

		if(isset($_POST['idTicket']) && isset($_POST['status'])){
			$idTicket=$_POST['idTicket'];
			$status=$_POST['status'];
			$sql="UPDATE id3295737_rebuc.ticket SET ti_status=$status WHERE ti_folio=$idTicket";
			$conn->update($sql);
			echo '[{"success":"Status cambiado."}]';
		}else{
			echo '[{"error":"No ha ingresado los datos."}]';
		}
					
	}
	
?>
<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	
	if($_SERVER['REQUEST_METHOD']=="POST"){

		date_default_timezone_set('America/Mexico_City');
		$timedate=date('Y-m-d H:i:s');

		$conn=new conectionSQL();
		$conn->startConection();		
		
		if(isset($_POST['idTicket'])&&isset($_POST['calificacion'])){
			$idTicket=$_POST['idTicket'];
			$calificacion=$_POST['calificacion'];

			$sql="UPDATE id3295737_rebuc.tickets SET ti_status=6 , ti_calificacion=".$calificacion." , ti_fechaCierre='".$timedate."' WHERE ti_folio=".$idTicket;
			$conn->update($sql);

			echo '[{"success":"Ticket cerrado correctamente."}]';

		}else{
			echo '[{"error":"No hay parametro de ticket."}]';
		}			
					
	}
	
?>
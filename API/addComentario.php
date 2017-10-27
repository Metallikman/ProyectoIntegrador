<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	//header('Content-Type: text/html; charset=UTF-8');
	
	if($_POST){

		$conn=new conectionSQL();
		$conn->startConection();

		date_default_timezone_set('America/Mexico_City');
		$timedate=date('Y-m-d H:i:s');

		$comentario=$_POST['comentario'];
		$idUsuario=$_POST['idUsuario'];
		$idTicket=$_POST['idTicket'];

		
		if($comentario!=""){
			
			$sql="INSERT INTO rebuc.movimientos (mo_idUsuario, mo_idTicket, mo_fechaMovimiento, mo_comentario) VALUES (".$idUsuario.",".$idTicket.",'".$timedate."','".$comentario."')";
			$conn->insert($sql);

			$sql="UPDATE rebuc.tickets SET ti_status=7 WHERE ti_folio=".$idTicket;
			$conn->update($sql);

			echo '[{"success":"Comentario agregado."}]';

		}else{
			echo '[{"error":"No ha ingresado un comentario"}]';
		}			
					
	}
	
?>
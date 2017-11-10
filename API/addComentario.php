<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	
	if($_POST){

		$conn=new conectionSQL();
		$conn->startConection();

		date_default_timezone_set('America/Mexico_City');
		$timedate=date('Y-m-d H:i:s');

		$comentario=$_POST['comentario'];
		$idUsuario=$_POST['idUsuario'];
		$idTicket=$_POST['idTicket'];
		$rol=$_POST['rol'];

		if($rol==3){
			$sql="INSERT INTO rebuc.movimientos (mo_idUsuario, mo_idTicket, mo_fechaMovimiento, mo_comentario, mo_etiquetaMovimiento) VALUES (".$idUsuario.",".$idTicket.",'".$timedate."','".$comentario."',2)";
		}else {
			$sql="INSERT INTO rebuc.movimientos (mo_idUsuario, mo_idTicket, mo_fechaMovimiento, mo_comentario, mo_etiquetaMovimiento) VALUES (".$idUsuario.",".$idTicket.",'".$timedate."','".$comentario."',1)";
		}
		if($comentario!=""){
			
			$conn->insert($sql);

			$sql="UPDATE rebuc.tickets SET ti_status=4 WHERE ti_folio=".$idTicket;
			$conn->update($sql);

			echo '[{"success":"Comentario agregado."}]';

		}else{
			echo '[{"error":"No ha ingresado un comentario"}]';
		}			
					
	}
	
?>
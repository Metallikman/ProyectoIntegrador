<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	
	if($_SERVER['REQUEST_METHOD']=="POST"){

		$conn=new conectionSQL();
		$conn->startConection();

		if(isset($_POST['status']) && isset($_POST['idUsuario'])){
			$sql="UPDATE id3295737_rebuc.usuarios SET us_status=".$_POST['status']." WHERE us_id=".$_POST['idUsuario'];
			$conn->update($sql);
			echo '[{"success":"Status cambiado."}]';
		}else{
			echo '[{"error":"No ha ingresado el status."}]';
		}
					
	}
	
?>
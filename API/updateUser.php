<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	
	if($_POST){
		$conn=new conectionSQL();
		$conn->startConection();

		if(isset($_POST['nombre']) && isset($_POST['apellido']) && isset($_POST['email']) && isset($_POST['idUsuario'])){
			$sql="UPDATE rebuc.usuarios SET us_nombre='".$_POST['nombre']."', us_apellido='".$_POST['apellido']."', us_correo='".$_POST['email']."'  WHERE us_id=".$_POST['idUsuario'];
			$conn->update($sql);
			echo '[{"success":"Usuario modificado correctamente."}]';
		}else{
			echo '[{"error":"No ha ingresado el status."}]';
		}					
	}	
?>
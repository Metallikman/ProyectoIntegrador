<?php
	header("Content-Type: application/json;charset=utf-8");
	include ("controladorConexionMySQL.php");
	
	if($_SERVER['REQUEST_METHOD']=="POST"){
		$conn=new conectionSQL();
		$conn->startConection();

		if(isset($_POST['nombre'])&& isset($_POST['apellido']) && isset($_POST['email']) && isset($_POST['idUsuario'])&& isset($_POST['rol'])&& isset($_POST['dependencia'])){
			$sql="UPDATE id3295737_rebuc.usuarios SET us_nombre='".$_POST['nombre']."', us_apellido='".$_POST['apellido']."', us_correo='".$_POST['email']."', us_rol=".$_POST['rol'].", us_dependencia=".$_POST['dependencia']." WHERE us_id=".$_POST['idUsuario'];
			$conn->update($sql);
			echo '[{"success":"Usuario modificado correctamente."}]';
		}
		else if(isset($_POST['nombre'])&& isset($_POST['apellido']) && isset($_POST['email']) && isset($_POST['idUsuario'])&& isset($_POST['rol'])){
			$sql="UPDATE id3295737_rebuc.usuarios SET us_nombre='".$_POST['nombre']."', us_apellido='".$_POST['apellido']."', us_correo='".$_POST['email']."', us_rol=".$_POST['rol']." WHERE us_id=".$_POST['idUsuario'];
			$conn->update($sql);
			echo '[{"success":"Usuario modificado correctamente."}]';
		}else{
			echo '[{"error":"No ha ingresado todos los datos."}]';
		}					
	}	
?>